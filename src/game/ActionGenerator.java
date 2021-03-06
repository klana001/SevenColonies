package game;

import interfaces.ExchangableItem;
import interfaces.GameElement;
import interfaces.UpgradableFrom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import common.Utilities;
import cards.IManufacturedGood;
import cards.IRawMaterial;
import cards.manufacturedgoods.ManufacturedGood;
import cards.rawmaterials.RawMaterial;
import classes.Card;
import classes.Coin;
import classes.CostedOwnedExchangableItem;
import effects.Effect;
import effects.Effect.ActivationPoint;
import effects.Effecting;
import effects.commercial.LeftTradeRawMaterialsForOneCoin;
import effects.commercial.RightTradeRawMaterialsForOneCoin;
import effects.commercial.TradeManufacturedGoodsForOneCoin;
import actions.Build;
import actions.BuildWonderStage;
import actions.DiscardForThreeCoins;
import actions.Upgrade;
import player.Player;
import wonders.wonderstage.WonderStage;

public class ActionGenerator
{
	static private class TradeCostGenerator
	{
		int leftRawMaterialsTradeCost;
		int rightRawMaterialsTradeCost;
		int manufacturedGoodsTradeCost;
		private int leftPlayerId;
		private int RightPlayerId;
		
		public TradeCostGenerator(Player player,List<Effect> tradeEffects)
		{
			leftRawMaterialsTradeCost = Utilities.filterElements(tradeEffects,LeftTradeRawMaterialsForOneCoin.class).isEmpty()?2:1;
			rightRawMaterialsTradeCost = Utilities.filterElements(tradeEffects,RightTradeRawMaterialsForOneCoin.class).isEmpty()?2:1;
			manufacturedGoodsTradeCost = Utilities.filterElements(tradeEffects,TradeManufacturedGoodsForOneCoin.class).isEmpty()?2:1;
			this.leftPlayerId=player.getLeftNeighbourId();
			this.RightPlayerId=player.getRightNeighbourId();
		}
		
		public int getTradeCost(ExchangableItem exchangableItem,int playerId)
		{
			int cost = 2;
			if (playerId == leftPlayerId && exchangableItem instanceof IRawMaterial)
			{
				cost = leftRawMaterialsTradeCost;
			}
			else if (playerId == RightPlayerId && exchangableItem instanceof IRawMaterial)
			{
				cost = rightRawMaterialsTradeCost;
			}
			else if (exchangableItem instanceof IManufacturedGood)
			{
				cost = manufacturedGoodsTradeCost;
			}
			return cost;

		}
	}
	
	static private class ActionMap extends HashMap<Integer, Action>
	{
		private int id=0;
		public void put(Action action)
		{
			put(id,action);
			action.setId(id++);
		}
	}
	
	static public class Costings 
	{
		List<CostedOwnedExchangableItem> costings = new ArrayList<CostedOwnedExchangableItem>();

		public void add(CostedOwnedExchangableItem costedOwnedExchangableItem)
		{
			costings.add(costedOwnedExchangableItem);
		}
		
		public List<CostedOwnedExchangableItem> getCostings()
		{
			return costings;
		}
		
		public Costings()
		{
			
		}
		
		@Override 
		public int hashCode()
		{
			int hash=0;
			for (CostedOwnedExchangableItem costedOwnedExchangableItem : costings)
			{
				hash+=costedOwnedExchangableItem.hashCode();
			}
			return hash;
		}
		@Override
		public boolean equals(Object other)
		{
			if (!(other instanceof Costings)) return false;
			Costings otherCostings = (Costings) other;

			if (otherCostings.getCostings().size()!=costings.size()) return false;
			
			for (CostedOwnedExchangableItem costedOwnedExchangableItem : costings)
			{
				if (!(otherCostings.getCostings().contains(costedOwnedExchangableItem))) return false;
			}
			return true;
		}
	}
	
	/**
	 * generates a map of all possible actions for the given player for the current turn
	 * @param player
	 * @param gameState
	 * @return
	 */
	public static HashMap<Integer,Action> generateActions(final Player player, GameState gameState)
	{
		ActionMap actions = new ActionMap();

		
		// add always available discard for 3 coin action
		actions.put(new DiscardForThreeCoins(player));
		
		populateBuildActions(actions,player,gameState);
		
		populateUpgradeActions(actions,player,gameState);
		
		populateBuildWonderStageActions(actions,player,gameState); 

		
		
				
		return actions;
	}
	
	private static void populateUpgradeActions(ActionMap actions,final Player player, GameState gameState)
	{
		Collection<UpgradableFrom> upgradableFromCards = Utilities.filterElements(player.getHand(),UpgradableFrom.class);
		
		Collection<Card> playerPlayedCards = Utilities.filterElements(player.getGameElements(), Card.class);
		for (UpgradableFrom upgradableToCard : upgradableFromCards)
		{
			for (Card upgradableFromCard : upgradableToCard.upgradableFrom())
			{
				playerPlayedCards.stream().filter(c->c.equals(upgradableFromCard)).forEach(prereq->actions.put(new Upgrade(player,prereq,(Card) upgradableToCard)));
			}
		}
	}
	
	private static void populateBuildActions(ActionMap actions,final Player player, GameState gameState)
	{
		final List<Effect> tradeEffectsMaster = new ArrayList<Effect>();
		Utilities.filterElements(player.getGameElements(),Effecting.class).stream().forEach(effecting->
				tradeEffectsMaster.addAll(
						effecting.getEffect().stream().filter(e->
							e.getActivationPoint(gameState, player)==ActivationPoint.EVERY_TRADE).collect(Collectors.toList())));
		
		final TradeCostGenerator tradeCostGenerator = new TradeCostGenerator(player,tradeEffectsMaster); 
		
		// iterate over all cards in hand and see what can be constructed and how (combinations of requirements for each card).
		final List<CostedOwnedExchangableItem> availableExchangableItems = new ArrayList<CostedOwnedExchangableItem>();

		// add default action to discard a card for 3 coins
		availableExchangableItems.add(new CostedOwnedExchangableItem(new Coin(player.getCoins()), 0,player));
		
		// add any player built cards that that yield a raw material or manufactured good.
		Utilities.filterElements(player.getGameElements(),ExchangableItem.class).forEach(e->availableExchangableItems.add(new CostedOwnedExchangableItem(e, 0,player)));
		
		// add any wonder stages effects that yield a raw material or manufactured good.
		Stream<WonderStage> playerWonderStages = gameState.getPlayer(player.getRightNeighbourId()).getWonder().getWonderStages().stream().filter(stage-> stage.isBuilt());
		Utilities.filterElements(playerWonderStages,ExchangableItem.class).forEach(e->availableExchangableItems.add(new CostedOwnedExchangableItem(e, 0,player)));

		// add trading coins for use of neighbour's played cards 
		Utilities.filterElements(gameState.getPlayer(player.getLeftNeighbourId()).getGameElements().stream(),ExchangableItem.class).filter(e->!Coin.class.isInstance(e)).forEach(e->availableExchangableItems.add(new CostedOwnedExchangableItem(e, tradeCostGenerator.getTradeCost(e,player.getLeftNeighbourId()),gameState.getPlayer(player.getLeftNeighbourId()))));
		Utilities.filterElements(gameState.getPlayer(player.getRightNeighbourId()).getGameElements().stream(),ExchangableItem.class).filter(e->!Coin.class.isInstance(e)).forEach(e->availableExchangableItems.add(new CostedOwnedExchangableItem(e, tradeCostGenerator.getTradeCost(e,player.getRightNeighbourId()),gameState.getPlayer(player.getRightNeighbourId()))));


		
		for (Card card : player.getHand())
		{
			List<CostedOwnedExchangableItem> availableExchangableItemsCopy = Utilities.cloneObject(availableExchangableItems);
			List<ExchangableItem> cardRequirements = new ArrayList<ExchangableItem>(Utilities.cloneObject(card.getCost()));
			Player localPlayerCopy = Utilities.cloneObject(player);
	
			if (cardRequirements.isEmpty())
			{
				actions.put(new Build(player,card,new ArrayList<CostedOwnedExchangableItem>()));
			}
			else
			{
				
				List<Effect> tradeEffects = Utilities.cloneObject(tradeEffectsMaster);
				
				Set<Costings> uniqueCostings = resolveUniqueCostingCombinations(gameState, localPlayerCopy, cardRequirements, availableExchangableItemsCopy,tradeEffects );
				
				
				// create and add build card actions for each unique costing...
				for (Costings costing : uniqueCostings)
				{
					actions.put(new Build(player,card,costing.getCostings()));
				}
			}
		}
	}
	
	private static void populateBuildWonderStageActions(ActionMap actions,final Player player, GameState gameState)
	{
		final List<Effect> tradeEffectsMaster = new ArrayList<Effect>();
		Utilities.filterElements(player.getGameElements(),Effecting.class).stream().forEach(effecting->
				tradeEffectsMaster.addAll(
						effecting.getEffect().stream().filter(e->
							e.getActivationPoint(gameState, player)==ActivationPoint.EVERY_TRADE).collect(Collectors.toList())));
		
		final TradeCostGenerator tradeCostGenerator = new TradeCostGenerator(player,tradeEffectsMaster); 
		
		// iterate over all cards in hand and see what can be constructed and how (combinations of requirements for each card).
		final List<CostedOwnedExchangableItem> availableExchangableItems = new ArrayList<CostedOwnedExchangableItem>();

		// add default action to discard a card for 3 coins
		availableExchangableItems.add(new CostedOwnedExchangableItem(new Coin(player.getCoins()), 0,player));
		
		// add any player built cards that that yield a raw material or manufactured good.
		Utilities.filterElements(player.getGameElements(),ExchangableItem.class).forEach(e->availableExchangableItems.add(new CostedOwnedExchangableItem(e, 0,player)));
		
		// add any wonder stages effects that yield a raw material or manufactured good.
		Stream<WonderStage> playerWonderStages = gameState.getPlayer(player.getRightNeighbourId()).getWonder().getWonderStages().stream().filter(stage-> stage.isBuilt());
		Utilities.filterElements(playerWonderStages,ExchangableItem.class).forEach(e->availableExchangableItems.add(new CostedOwnedExchangableItem(e, 0,player)));

		// add trading coins for use of neighbour's played cards 
		Utilities.filterElements(gameState.getPlayer(player.getLeftNeighbourId()).getGameElements().stream(),ExchangableItem.class).filter(e->!Coin.class.isInstance(e)).forEach(e->availableExchangableItems.add(new CostedOwnedExchangableItem(e, tradeCostGenerator.getTradeCost(e,player.getLeftNeighbourId()),gameState.getPlayer(player.getLeftNeighbourId()))));
		Utilities.filterElements(gameState.getPlayer(player.getRightNeighbourId()).getGameElements().stream(),ExchangableItem.class).filter(e->!Coin.class.isInstance(e)).forEach(e->availableExchangableItems.add(new CostedOwnedExchangableItem(e, tradeCostGenerator.getTradeCost(e,player.getRightNeighbourId()),gameState.getPlayer(player.getRightNeighbourId()))));

	
		for (WonderStage wonderStage : player.getWonder().getNextWonderStage())
		{
			List<CostedOwnedExchangableItem> availableExchangableItemsCopy = Utilities.cloneObject(availableExchangableItems);
			List<ExchangableItem> wonderStageRequirements = new ArrayList<ExchangableItem>(Utilities.cloneObject(wonderStage.getCost()));
			Player localPlayerCopy = Utilities.cloneObject(player);
	
			if (wonderStageRequirements.isEmpty())
			{
				actions.put(new BuildWonderStage(player,wonderStage,new ArrayList<CostedOwnedExchangableItem>()));
			}
			else
			{
				
				List<Effect> tradeEffects = Utilities.cloneObject(tradeEffectsMaster);
				
				Set<Costings> uniqueCostings = resolveUniqueCostingCombinations(gameState, localPlayerCopy, wonderStageRequirements, availableExchangableItemsCopy,tradeEffects );
				
				
				// create and add build card actions for each unique costing...
				for (Costings costing : uniqueCostings)
				{
					actions.put(new BuildWonderStage(player,wonderStage,costing.getCostings()));
				}
			}
		}
	}
	
	
	/**
	 * For the given requirement, creates combinations of available exchangeable items that meet the requirement.
	 * This is an iterative method terminating when either no more requirements are left or a requirement cannot be met.
	 * The resultant combinations (if any) are added to the uniqueCostings set.
	 * @param gameState
	 * @param player
	 * @param requirement
	 * @param remainingCardRequirements
	 * @param availableExchangableItems
	 * @param tradeEffects
	 * @param uniqueCostings
	 * @param costings
	 */
	private static void resolveUniqueCostingCombinations(GameState gameState, Player player, ExchangableItem requirement, List<ExchangableItem> remainingCardRequirements, List<CostedOwnedExchangableItem> availableExchangableItems, List<Effect> tradeEffects,Set<Costings> uniqueCostings,Costings costings)
	{
		// no more requirements needed
		if (requirement == null)
		{
			uniqueCostings.add(costings);
		}
		else
		{
			List<CostedOwnedExchangableItem> exchangeableItemsThatMeetRequirment = availableExchangableItems.stream().filter(c->c.getItem().equivilent(requirement, player)).collect(Collectors.toList());
			
			List<ExchangableItem> newRemainingCardRequirements = Utilities.cloneObject(remainingCardRequirements);
			ExchangableItem nextRequirement = newRemainingCardRequirements.isEmpty()?null:newRemainingCardRequirements.remove(0);
			
			while (exchangeableItemsThatMeetRequirment.size()>0)
			{
				CostedOwnedExchangableItem originalItem = exchangeableItemsThatMeetRequirment.remove(0);
				if (originalItem.getCost()>player.getCoins())
				{
					break;
				}
				
				CostedOwnedExchangableItem item; 
				
				// hack! we need the requirement coin otherwise the player coins will be used!
				if (requirement instanceof Coin)
				{
					item = new CostedOwnedExchangableItem(requirement, originalItem.getCost(), originalItem.getOwner()); 
				}
				else
				{
					item = new CostedOwnedExchangableItem(originalItem.getItem(), originalItem.getCost(), originalItem.getOwner());
				}
				Player newPlayer = Utilities.cloneObject(player);
				newPlayer.setCoins(newPlayer.getCoins()-originalItem.getCost());
				
//				for (Effect effect : tradeEffects)
//				{
//					effect.performEffect(gameState, item, newPlayer);
//				}
				
				Costings newCostings= Utilities.cloneObject(costings);
				newCostings.add(item);
					
				List<CostedOwnedExchangableItem> newAvailableExchangableItems = Utilities.cloneObject(availableExchangableItems);
				if (!newAvailableExchangableItems.removeIf(i->i.equivilent(item)))
				{
					throw new RuntimeException("Unable to remove: "+item+" from available exchangable items... item is not found.");
				};
				
				
				List<Effect> newTradeEffects = Utilities.cloneObject(tradeEffects);
				
				
				resolveUniqueCostingCombinations(gameState, newPlayer, nextRequirement,newRemainingCardRequirements,newAvailableExchangableItems,newTradeEffects,uniqueCostings,newCostings);
			}
		}
	}
	
	/**
	 * Creates unique combinations of available exchangeable items that meet the requirements provided (if any).
	 * Returns the resultant combinations (if any).
	 * Where unique is the union of player, exchange item and cost of the exchange item.
	 * @param gameState
	 * @param player
	 * @param requirement
	 * @param remainingCardRequirements
	 * @param availableExchangableItems
	 * @param tradeEffects
	 */
	
	private static Set<Costings> resolveUniqueCostingCombinations(GameState gameState, Player player, List<ExchangableItem> requirements, List<CostedOwnedExchangableItem> availableExchangableItems, List<Effect> tradeEffects)
	{
		ExchangableItem requirement = requirements.remove(0);
		
		HashSet<Costings> uniqueCostings = new HashSet<Costings>();
		Costings costings = new Costings();
		resolveUniqueCostingCombinations(gameState, player, requirement,requirements,availableExchangableItems,tradeEffects,uniqueCostings,costings);
		return uniqueCostings;
	}
}

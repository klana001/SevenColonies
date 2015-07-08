package game;

import interfaces.ExchangableItem;
import interfaces.UpgradableFrom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import common.Utilities;
import classes.Card;
import classes.Coin;
import classes.CostedOwnedExchangableItem;
import effects.Effect;
import effects.Effect.ActivationPoint;
import actions.Build;
import actions.DiscardForThreeCoins;
import actions.Upgrade;
import player.Player;

public class ActionGenerator
{
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
		// iterate over all cards in hand and see what can be constructed and how (combinations of requirements for each card).
		final List<CostedOwnedExchangableItem> availableExchangableItems = new ArrayList<CostedOwnedExchangableItem>();
		
		availableExchangableItems.add(new CostedOwnedExchangableItem(new Coin(player.getCoins()), 0,player));
		Utilities.filterElements(player.getGameElements(),ExchangableItem.class).forEach(e->availableExchangableItems.add(new CostedOwnedExchangableItem(e, 0,player)));
		Utilities.filterElements(gameState.getPlayer(player.getLeftNeighbourId()).getGameElements().stream(),ExchangableItem.class).filter(e->!Coin.class.isInstance(e)).forEach(e->availableExchangableItems.add(new CostedOwnedExchangableItem(e, 2,gameState.getPlayer(player.getLeftNeighbourId()))));
		Utilities.filterElements(gameState.getPlayer(player.getRightNeighbourId()).getGameElements().stream(),ExchangableItem.class).filter(e->!Coin.class.isInstance(e)).forEach(e->availableExchangableItems.add(new CostedOwnedExchangableItem(e, 2,gameState.getPlayer(player.getRightNeighbourId()))));
		
		final List<Effect> tradeEffectsMaster = Utilities.filterElements(player.getGameElements(),Effect.class).stream().filter(e->e.getActivationPoint()==ActivationPoint.EVERY_TRADE).collect(Collectors.toList());
		

		
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
				
				for (Effect effect : tradeEffects)
				{
					effect.performEffect(gameState, item, newPlayer);
				}
				
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

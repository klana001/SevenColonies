package game;

import interfaces.ExchangableItem;

import java.util.ArrayList;
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
import player.Player;

public class ActionGenerator
{
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
		
		public Costings(Costings source)
		{
			costings = Utilities.cloneList(source.costings);
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
		HashMap<Integer,Action> actions = new HashMap<Integer,Action>();
		int id=0;
		
		// add always available discard for 3 coin action
		actions.put(id,new DiscardForThreeCoins(id++,player));
		
		// iterate over all cards in hand and see what can be constructed and how (combinations of requirements for each card).
		final List<CostedOwnedExchangableItem> availableExchangableItems = new ArrayList<CostedOwnedExchangableItem>();
		
		availableExchangableItems.add(new CostedOwnedExchangableItem(new Coin(player.getCoins()), 0,player));
		Utilities.filterElements(player.getGameElements(),ExchangableItem.class).forEach(e->availableExchangableItems.add(new CostedOwnedExchangableItem(e, 0,player)));
		Utilities.filterElements(gameState.getPlayer(player.getLeftNeighbourId()).getGameElements(),ExchangableItem.class).forEach(e->availableExchangableItems.add(new CostedOwnedExchangableItem(e, 2,gameState.getPlayer(player.getLeftNeighbourId()))));
		Utilities.filterElements(gameState.getPlayer(player.getRightNeighbourId()).getGameElements(),ExchangableItem.class).forEach(e->availableExchangableItems.add(new CostedOwnedExchangableItem(e, 2,gameState.getPlayer(player.getRightNeighbourId()))));
		
		final List<Effect> tradeEffectsMaster = Utilities.filterElements(player.getGameElements(),Effect.class).stream().filter(e->e.getActivationPoint()==ActivationPoint.EVERY_TRADE).collect(Collectors.toList());
		

		
		for (Card card : player.getHand())
		{
			List<CostedOwnedExchangableItem> availableExchangableItemsCopy = Utilities.cloneList(availableExchangableItems);
			List<ExchangableItem> cardRequirements = Utilities.cloneList(card.getCost());
			
	
			if (cardRequirements.isEmpty())
			{
				actions.put(id, new Build(id++,player,card,new ArrayList<CostedOwnedExchangableItem>()));
			}
			else
			{
				
				List<Effect> tradeEffects = Utilities.cloneList(tradeEffectsMaster);
				
				Set<Costings> uniqueCostings = resolveUniqueCostingCombinations(gameState, player, cardRequirements, availableExchangableItemsCopy,tradeEffects );
				
				
				// create and add build card actions for each unique costing...
				for (Costings costing : uniqueCostings)
				{
					actions.put(id, new Build(id++,player,card,costing.getCostings()));
				}
			}
		}
		
		return actions;
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

			List<CostedOwnedExchangableItem> exchangeableItemsThatMeetRequirment = availableExchangableItems.stream().filter(c->requirement.equals(c.getItem())).collect(Collectors.toList());
			
			while (exchangeableItemsThatMeetRequirment.size()>0)
			{
				CostedOwnedExchangableItem originalItem = exchangeableItemsThatMeetRequirment.remove(0);
				
				CostedOwnedExchangableItem item = new CostedOwnedExchangableItem(requirement, originalItem.getCost(), originalItem.getOwner()); 
				
				for (Effect effect : tradeEffects)
				{
					effect.performEffect(gameState, item, player);
				}
				
				costings.add(item);
				
				Costings newCostings=new Costings(costings);
				
				List<CostedOwnedExchangableItem> newAvailableExchangableItems = Utilities.cloneList(availableExchangableItems);
				newAvailableExchangableItems.remove(item);
				
				ExchangableItem nextRequirement = remainingCardRequirements.isEmpty()?null:remainingCardRequirements.remove(0);
				List<ExchangableItem> newRemainingCardRequirements = Utilities.cloneList(remainingCardRequirements);
				
				List<Effect> newTradeEffects = Utilities.cloneList(tradeEffects);
				
				
				resolveUniqueCostingCombinations(gameState, player, nextRequirement,newRemainingCardRequirements,newAvailableExchangableItems,newTradeEffects,uniqueCostings,newCostings);
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

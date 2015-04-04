package actions;

import common.Utilities;

import classes.Card;
import player.Player;
import game.Action;
import game.GameState;

public class DiscardForThreeCoins extends Action
{
	private Card cardToDiscard;
	
	public DiscardForThreeCoins(int id,Player player)
	{
		super(id,player);
	}
	
	public DiscardForThreeCoins(DiscardForThreeCoins source)
	{
		super(source.getId(),Utilities.cloneObject(source.getOwner()));
		cardToDiscard = Utilities.cloneObject(source.cardToDiscard);
	}
	

	@Override
	public void perform(GameState oldGamestate, GameState newGameState) throws Exception
	{
		if (owner.getHand().contains(cardToDiscard))
		{
			if (owner.getGameElements().contains(cardToDiscard))
			
			owner.setCoins(owner.getCoins()+3);
			newGameState.getDiscards().add(cardToDiscard);
			owner.getGameElements().remove(cardToDiscard);
		}
		else
		{
			throw new Exception("Player "+owner+" attempted to discard card: "+cardToDiscard+" however the player did not have the card in the current hand.");
		}
	}

	@Override
	public void setData(Object data) throws Exception
	{
		if (!(data instanceof Card))
		{
			throw new Exception("Data: "+data+" is not a card.");
		}
		
		cardToDiscard = (Card) data;
	}

	@Override
	public String toString()
	{
		return "DiscardForThreeGold";
	}


}

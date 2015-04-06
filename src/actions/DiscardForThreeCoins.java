package actions;

import java.util.ArrayList;
import java.util.List;

import common.Utilities;
import classes.Card;
import player.Player;
import game.Action;
import game.GameState;
import game.Payment;

public class DiscardForThreeCoins extends Action
{
	private Card cardToDiscard;
	
	public DiscardForThreeCoins(int id,Player player)
	{
		super(id,player);
	}


	@Override
	public void perform(GameState oldGamestate, GameState newGameState) throws Exception
	{
		newGameState.discard(cardToDiscard,owner);
	}

	@Override
	public void setData(Object... data) throws Exception
	{
		if (!(data[0] instanceof Card))
		{
			throw new Exception("Data: "+data[0]+" is not a card.");
		}
		
		cardToDiscard = (Card) data[0];
	}
	
	@Override
	public Object[] getData()
	{
		return new Object[]{cardToDiscard};
	}

	@Override
	public String toString()
	{
		return "DiscardForThreeGold";
	}

	public void setCardToDiscard(Card card)
	{
		cardToDiscard=card;
	}


	@Override
	public List<Payment> getPayments()
	{
		return new ArrayList<Payment>();
	}

}

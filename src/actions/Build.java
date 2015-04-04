package actions;

import java.util.List;

import common.Utilities;
import classes.Card;
import classes.CostedOwnedExchangableItem;
import player.Player;
import game.Action;
import game.GameState;

public class Build extends Action
{
	private List<CostedOwnedExchangableItem> costedOwnedExchangeableItems;
	private Card card;
	
	public Build(int id, Player owner,Card card, List<CostedOwnedExchangableItem> costedOwnedExchangeableItems)
	{
		super(id, owner);
		this.costedOwnedExchangeableItems=costedOwnedExchangeableItems;
		this.setCard(card);
	}
	
	public Build(Build source)
	{
		super(source.getId(), Utilities.cloneObject(source.getOwner()));
		costedOwnedExchangeableItems = Utilities.cloneList(costedOwnedExchangeableItems);
		setCard(Utilities.cloneObject(source.getCard()));
	}

	@Override
	public void setData(Object data) throws Exception
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void perform(GameState olsGamestate, GameState newGameState) throws Exception
	{
		// TODO Auto-generated method stub
		throw new RuntimeException("TBD");
	}
	
	@Override
	public String toString()
	{
		String result = "Build: \n";
		result+="\tCard:"+card+"\n";
		result+="\tCost:\n";
		for (CostedOwnedExchangableItem item : costedOwnedExchangeableItems)
		{
			result+="\t\t"+item.getItem().getName();
			if (item.getOwner().getId()!=owner.getId())
			{
				result+=" + "+item.getCost()+" coin for trading with neighbour: "+owner;
			}
		}
		result+="\n";
		
		return result;
	}

	public Card getCard()
	{
		return card;
	}

	public void setCard(Card card)
	{
		this.card = card;
	}

}

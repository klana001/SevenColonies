package actions;

import java.util.ArrayList;
import java.util.List;

import common.Utilities;
import classes.Card;
import classes.CostedOwnedExchangableItem;
import player.Player;
import game.Action;
import game.BankPayment;
import game.GameState;
import game.Payment;
import game.TradePayment;

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

	@Override
	public void setData(Object... data) throws Exception
	{
		costedOwnedExchangeableItems=(List<CostedOwnedExchangableItem>) data[0];
		card = (Card)data[1];

	}

	@Override
	public void perform(GameState olsGamestate, GameState newGameState) throws Exception
	{
		// TODO Auto-generated method stub
		Player player=newGameState.getPlayer(owner.getId());
		int coins = player.getCoins();
		
		for (CostedOwnedExchangableItem  item : costedOwnedExchangeableItems)
		{
			coins+=item.getCost();
		}

		player.placeCard(newGameState,card);
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
				result+=" + "+item.getCost()+" coin for trading with neighbour: "+item.getOwner();
			}
			result+="\n";
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

	@Override
	public Object[] getData()
	{
		return new Object[]{costedOwnedExchangeableItems,card};
	}

	@Override
	public List<Payment> getPayments()
	{
		List<Payment> results = new ArrayList<Payment>();
		
		for (CostedOwnedExchangableItem item : costedOwnedExchangeableItems)
		{
			if (item.getOwner().getId()!=getOwner().getId())
			{
				results.add(new TradePayment(item.getCost(), item.getOwner()));
			}
			else if (item.getCost()!=0)
			{
				results.add(new BankPayment(item.getCost()));
			}
		}
		return results;
	}

}

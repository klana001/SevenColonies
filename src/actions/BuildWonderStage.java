package actions;

import java.util.ArrayList;
import java.util.List;

import common.Utilities;
import classes.Card;
import classes.CostedOwnedExchangableItem;
import player.Player;
import wonders.wonderstage.WonderStage;
import game.Action;
import game.BankPayment;
import game.GameState;
import game.Payment;
import game.TradePayment;

public class BuildWonderStage extends Action
{
	private List<CostedOwnedExchangableItem> costedOwnedExchangeableItems;
	private WonderStage wonderStage;
	private Card card;
	
	public BuildWonderStage(Player owner,WonderStage wonderStage, List<CostedOwnedExchangableItem> costedOwnedExchangeableItems)
	{
		super(owner);
		this.costedOwnedExchangeableItems=costedOwnedExchangeableItems;
		this.setWonderStage(wonderStage);
	}

	@Override
	public void setData(Object... data) throws Exception
	{
//		costedOwnedExchangeableItems=(List<CostedOwnedExchangableItem>) data[0];
		card = (Card)data[0];
	}

	@Override
	public void perform(GameState oldGamestate, GameState newGameState) throws Exception
	{
		Player player=newGameState.getPlayer(owner.getId());
		int coins = player.getCoins();
		
		for (CostedOwnedExchangableItem  item : costedOwnedExchangeableItems)
		{
			coins+=item.getCost();
		}

		player.buildWonderStage(wonderStage,newGameState,card);
	}
	
	@Override
	public String toString()
	{
		String result = "Build Wonder Stage: \n";
		result+="\tWonderStage:"+wonderStage+"\n";
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

	public WonderStage getWonderStage()
	{
		return wonderStage;
	}

	public void setWonderStage(WonderStage wonderStage)
	{
		this.wonderStage = wonderStage;
	}

	@Override
	public Object[] getData()
	{
		return new Object[]{card};
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

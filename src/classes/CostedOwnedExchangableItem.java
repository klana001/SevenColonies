package classes;

import game.Action;
import interfaces.ExchangableItem;
import player.Player;
import common.Utilities;

public class CostedOwnedExchangableItem 
{
	private ExchangableItem item;
	int cost;
	Player owner;
	
	public CostedOwnedExchangableItem(ExchangableItem item, int cost, Player owner)
	{
		super();
		this.setItem(item);
		this.cost = cost;
		this.owner = owner;
	}
	
	public CostedOwnedExchangableItem(CostedOwnedExchangableItem source)
	{
		this.setItem(Utilities.cloneObject(source.getItem()));
		this.owner = Utilities.cloneObject(source.owner);
		this.cost = source.cost;
	}
	
	@Override 
	public int hashCode()
	{
		return (getItem().getName().hashCode()+owner.hashCode())*(cost*113);
	}
	@Override
	public boolean equals(Object other)
	{
		if (!(other instanceof CostedOwnedExchangableItem)) return false;
		CostedOwnedExchangableItem otherCostedOwnedExchangableItem = (CostedOwnedExchangableItem) other;
		return cost==otherCostedOwnedExchangableItem.cost &&
			   getItem().equals(otherCostedOwnedExchangableItem.getItem()) &&
			   owner.equals(otherCostedOwnedExchangableItem.owner);
	}

	public ExchangableItem getItem()
	{
		return item;
	}

	private void setItem(ExchangableItem item)
	{
		this.item = item;
	}

	public int getCost()
	{
		return cost;
	}

	public Player getOwner()
	{
		return owner;
	}
}
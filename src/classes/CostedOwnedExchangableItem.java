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
	
	
	@Override 
	public int hashCode()
	{
		return (getItem().getName().hashCode()+owner.getName().hashCode())*(cost*113);
	}
	@Override
	public boolean equals(Object other)
	{
//		throw new RuntimeException();
		if (!(other instanceof CostedOwnedExchangableItem)) return false;
		CostedOwnedExchangableItem otherCostedOwnedExchangableItem = (CostedOwnedExchangableItem) other;
		return cost==otherCostedOwnedExchangableItem.cost &&
			   getItem().equivilent(otherCostedOwnedExchangableItem.getItem(), owner) &&
			   owner.getId()==otherCostedOwnedExchangableItem.owner.getId();
	}
	
	public boolean equivilent(CostedOwnedExchangableItem otherCostedOwnedExchangableItem)
	{
		if (otherCostedOwnedExchangableItem==null) return false;
		return cost==otherCostedOwnedExchangableItem.cost &&
			   getItem().equivilent(otherCostedOwnedExchangableItem.getItem(), owner) &&
			   owner.getId()==otherCostedOwnedExchangableItem.owner.getId();
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
	
	public String toString()
	{
		return ""+this.getClass().getName()+"@"+Integer.toHexString(System.identityHashCode(this))+":\n\tItem: "+item+"\n\tCost: "+cost+"\n\tOwner: "+owner+"\n";
	}
}
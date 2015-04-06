package common;

import player.Player;
import classes.Base;
import classes.Coin;
import interfaces.ExchangableItem;


public class NoCost extends Base implements ExchangableItem
{
	public NoCost()
	{
		
	}
	
	@Override
	public String getName()
	{
		return "No Cost";
	}

	@Override
	public int compareTo(Object o)
	{
		if (o instanceof NoCost) return 0;
		return -1;
	}
	
	@Override
	public boolean equivilent(ExchangableItem otherItem, Player currentPlayer)
	{
		return true;
	}

}

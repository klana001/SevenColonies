package common;

import classes.Base;
import interfaces.ExchangableItem;


public class NoCost extends Base implements ExchangableItem
{
	public NoCost()
	{
		
	}
	
	public NoCost(NoCost source)
	{
		// default copy constructor
	}
	
	@Override
	public String getName()
	{
		return "No Cost";
	}

}

package cards.manufacturedgoods;

import java.util.Arrays;
import java.util.Collection;

import cards.IManufacturedGood;


public class Glass implements IManufacturedGood
{
	public Glass()
	{
		super();
		// default constructor
	}
	
	public Glass(Glass source)
	{
		super();
		// default copy constructor	
	}
	
	@Override
	public Collection<Type> getManufacturedGoodType()
	{
		return Arrays.asList(new Type[]{Type.GLASS});
	}

	@Override
	public Collection<IManufacturedGood> getManufacturedGoods()
	{
		return Arrays.asList(this);
	}
	
	@Override
	public int compareTo(Object arg0)
	{
		return this.getClass().isInstance(arg0)?0:-1;
	}

	@Override
	public String getName()
	{
		return "Glass";
	}
	
	@Override
	public String toString()
	{
		return getName();
	}
}

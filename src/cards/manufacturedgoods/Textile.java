package cards.manufacturedgoods;

import java.util.Arrays;
import java.util.Collection;

import cards.IManufacturedGood;


public class Textile implements IManufacturedGood
{
	public Textile()
	{
		super();
		// default constructor
	}
	
	public Textile(Textile source)
	{
		super();
		// default copy constructor
	}
	
	@Override
	public Collection<Type> getManufacturedGoodType()
	{
		return Arrays.asList(new Type[]{Type.TEXTILE});
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
		return this.getClass().getSimpleName();
	}
	
	@Override
	public String toString()
	{
		return getName();
	}
}


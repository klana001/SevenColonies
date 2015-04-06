package cards.manufacturedgoods;

import interfaces.ExchangableItem;

import java.util.Arrays;
import java.util.Collection;

import player.Player;
import cards.IManufacturedGood;

public class Papyrus implements IManufacturedGood
{
	public Papyrus()
	{
		super();
		// default constructor
	}
	
	@Override
	public Collection<Type> getManufacturedGoodType()
	{
		return Arrays.asList(new Type[]{Type.PAPYRUS});
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
	
	@Override
	public boolean equivilent(ExchangableItem otherItem, Player currentPlayer)
	{
		if (otherItem instanceof IManufacturedGood)
		{
			IManufacturedGood otherManufacturedGood = (IManufacturedGood) otherItem;
			for (IManufacturedGood.Type type : getManufacturedGoodType())
			{
				if (otherManufacturedGood.getManufacturedGoodType().contains(type)) return true;
			}
		}
		return false;
	}
}

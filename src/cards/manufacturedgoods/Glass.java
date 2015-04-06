package cards.manufacturedgoods;

import interfaces.ExchangableItem;

import java.util.Arrays;
import java.util.Collection;

import player.Player;
import cards.IManufacturedGood;
import cards.IRawMaterial;
import cards.rawmaterials.RawMaterial;


public class Glass implements IManufacturedGood
{
	public Glass()
	{
		super();
		// default constructor
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

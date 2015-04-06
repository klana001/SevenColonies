package cards.rawmaterials;

import interfaces.ExchangableItem;

import java.util.Arrays;
import java.util.Collection;

import player.Player;
import cards.IRawMaterial;
import cards.IRawMaterial.Type;

public class WoodOrStone implements IRawMaterial
{

	public WoodOrStone()
	{

	}
	
	@Override
	public Collection<IRawMaterial> getRawMaterials()
	{
		return Arrays.asList(new Wood(),new Stone());
	}

	@Override
	public Collection<Type> getRawMaterialType()
	{
		return Arrays.asList(new Type[]{Type.WOOD,Type.STONE});
	}
	@Override
	public int compareTo(Object arg0)
	{
		return (arg0 instanceof Wood || arg0 instanceof Stone)?0:-1;
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
		if (otherItem instanceof IRawMaterial)
		{
			IRawMaterial otherMaterial = (IRawMaterial) otherItem;
			for (Type type : getRawMaterialType())
			{
				if (otherMaterial.getRawMaterialType().contains(type))
				{
					return true;
				}
			}
		}
		return false;
	}
}

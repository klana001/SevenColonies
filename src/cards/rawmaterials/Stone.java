package cards.rawmaterials;

import interfaces.ExchangableItem;

import java.util.Arrays;
import java.util.Collection;

import player.Player;
import cards.IRawMaterial;

public class Stone implements IRawMaterial
{
	
	public Stone()
	{

	}
	
	@Override
	public Collection<Type> getRawMaterialType()
	{
		return Arrays.asList(new Type[]{Type.STONE});
	}

	@Override
	public Collection<IRawMaterial> getRawMaterials()
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
		if (otherItem instanceof IRawMaterial)
		{
			IRawMaterial otherMaterial = (IRawMaterial) otherItem;
			for (IRawMaterial.Type type : getRawMaterialType())
			{
				if (otherMaterial.getRawMaterialType().contains(type)) return true;
			}
		}
		return false;
	}
}

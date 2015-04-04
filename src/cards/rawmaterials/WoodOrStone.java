package cards.rawmaterials;

import java.util.Arrays;
import java.util.Collection;

import cards.IRawMaterial;

public class WoodOrStone implements IRawMaterial
{
	public WoodOrStone(WoodOrStone source)
	{
		super();
		// default copy constructor	
	}
	
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
}

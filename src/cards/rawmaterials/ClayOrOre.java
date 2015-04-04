package cards.rawmaterials;

import java.util.Arrays;
import java.util.Collection;

import cards.IRawMaterial;

public class ClayOrOre implements IRawMaterial
{
	public ClayOrOre(ClayOrOre source)
	{
		super();
		// default copy constructor	
	}
	
	public ClayOrOre()
	{

	}
	
	@Override
	public Collection<IRawMaterial> getRawMaterials()
	{
		return Arrays.asList(new Clay(),new Ore());
	}

	@Override
	public Collection<Type> getRawMaterialType()
	{
		return Arrays.asList(new Type[]{Type.CLAY,Type.ORE});
	}
	
	@Override
	public int compareTo(Object arg0)
	{
		return (arg0 instanceof Clay || arg0 instanceof Ore)?0:-1;
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

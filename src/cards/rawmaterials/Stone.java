package cards.rawmaterials;

import java.util.Arrays;
import java.util.Collection;

import cards.IRawMaterial;

public class Stone implements IRawMaterial
{
	public Stone(Stone source)
	{
		super();
		// default copy constructor	
	}
	
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
}

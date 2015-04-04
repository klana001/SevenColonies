package cards.rawmaterials;

import java.util.Arrays;
import java.util.Collection;

import cards.IRawMaterial;
import cards.manufacturedgoods.Glass;

public class Clay implements IRawMaterial
{
	public Clay(Clay source)
	{
		super();
		// default copy constructor	
	}
	public Clay()
	{
		
	}
	
	@Override
	public Collection<Type> getRawMaterialType()
	{
		return Arrays.asList(new Type[]{Type.CLAY});
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

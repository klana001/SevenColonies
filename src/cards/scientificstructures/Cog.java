package cards.scientificstructures;

import java.util.Arrays;
import java.util.Collection;

import cards.IScientificStructure;
import cards.rawmaterials.WoodOrStone;


public class Cog implements IScientificStructure
{

	public Cog()
	{

	}
	
	@Override
	public Collection<Type> getScientificStructureType()
	{
		return Arrays.asList(new Type[]{Type.COG});
	}


	@Override
	public Collection<IScientificStructure> getScientificStructures()
	{
		return Arrays.asList(this);
	}
}

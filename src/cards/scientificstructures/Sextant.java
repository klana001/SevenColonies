package cards.scientificstructures;

import java.util.Arrays;
import java.util.Collection;

import cards.IScientificStructure;


public class Sextant implements IScientificStructure
{
	public Sextant(Sextant source)
	{
		super();
		// default copy constructor	
	}
	
	public Sextant()
	{

	}
	
	
	@Override
	public Collection<Type> getScientificStructureType()
	{
		return Arrays.asList(new Type[]{Type.SEXTANT});
	}


	@Override
	public Collection<IScientificStructure> getScientificStructures()
	{
		return Arrays.asList(this);
	}
}

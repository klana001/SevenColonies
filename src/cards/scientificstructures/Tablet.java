package cards.scientificstructures;

import java.util.Arrays;
import java.util.Collection;

import cards.IScientificStructure;


public class Tablet implements IScientificStructure
{
	public Tablet(Tablet source)
	{
		super();
		// default copy constructor	
	}
	
	public Tablet()
	{

	}
	
	@Override
	public Collection<Type> getScientificStructureType()
	{
		return Arrays.asList(new Type[]{Type.TABLET});
	}


	@Override
	public Collection<IScientificStructure> getScientificStructures()
	{
		return Arrays.asList(this);
	}
}

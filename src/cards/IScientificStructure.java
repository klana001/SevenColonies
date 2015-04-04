package cards;

import java.util.Collection;

public interface IScientificStructure
{

	public enum Type
	{
		COG,
		SEXTANT,
		TABLET
	}
	
	public Collection<Type> getScientificStructureType();
	
	public Collection<IScientificStructure> getScientificStructures(); 
	
}

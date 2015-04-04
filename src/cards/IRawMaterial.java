package cards;

import interfaces.ExchangableItem;

import java.util.Collection;

public interface IRawMaterial extends ExchangableItem
{

	public enum Type
	{
		CLAY,
		ORE,
		STONE,
		WOOD,
	}
	
	public Collection<Type> getRawMaterialType();
	
	public Collection<IRawMaterial> getRawMaterials(); 
	
	
	
}

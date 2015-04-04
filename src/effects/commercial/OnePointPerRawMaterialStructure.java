package effects.commercial;

import cards.CommercialStructure;
import cards.rawmaterials.RawMaterial;
import effects.PointsPerType;

public class OnePointPerRawMaterialStructure extends PointsPerType
{
	public OnePointPerRawMaterialStructure(OnePointPerRawMaterialStructure source)
	{
		super(1, RawMaterial.class);
		// default copy constructor
	}
	
	
	public OnePointPerRawMaterialStructure()
	{
		super(1, RawMaterial.class);
	}

}

package effects.commercial;

import cards.CommercialStructure;
import cards.rawmaterials.RawMaterial;
import effects.CoinsPerType;

public class OneCoinPerRawMaterialStructure extends CoinsPerType
{
	public OneCoinPerRawMaterialStructure(OneCoinPerRawMaterialStructure source)
	{
		super(1, RawMaterial.class);
		// default copy constructor
	}

	public OneCoinPerRawMaterialStructure()
	{
		super(1, RawMaterial.class);
	}

}

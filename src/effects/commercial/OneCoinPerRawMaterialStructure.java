package effects.commercial;

import cards.CommercialStructure;
import cards.rawmaterials.RawMaterial;
import effects.CoinsPerType;

public class OneCoinPerRawMaterialStructure extends CoinsPerType
{

	public OneCoinPerRawMaterialStructure()
	{
		super(1, RawMaterial.class);
	}

}

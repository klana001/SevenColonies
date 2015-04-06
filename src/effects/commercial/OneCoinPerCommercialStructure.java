package effects.commercial;

import cards.CommercialStructure;
import effects.CoinsPerType;

public class OneCoinPerCommercialStructure extends CoinsPerType
{

	public OneCoinPerCommercialStructure()
	{
		super(1, CommercialStructure.class);
	}

}

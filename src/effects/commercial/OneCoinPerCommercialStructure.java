package effects.commercial;

import cards.CommercialStructure;
import effects.CoinsPerType;

public class OneCoinPerCommercialStructure extends CoinsPerType
{

	public OneCoinPerCommercialStructure(OneCoinPerCommercialStructure source)
	{
		super(1, CommercialStructure.class);
		// default copy constructor
	}
	
	public OneCoinPerCommercialStructure()
	{
		super(1, CommercialStructure.class);
	}

}

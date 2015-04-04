package effects.commercial;

import cards.CommercialStructure;
import effects.PointsPerType;

public class OnePointPerCommercialStructure extends PointsPerType
{
	
	public OnePointPerCommercialStructure(OnePointPerCommercialStructure source)
	{
		super(1, CommercialStructure.class);
		// default copy constructor
	}
	

	public OnePointPerCommercialStructure()
	{
		super(1, CommercialStructure.class);
	}

}

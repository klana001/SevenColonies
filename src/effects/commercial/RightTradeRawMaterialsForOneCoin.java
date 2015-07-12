package effects.commercial;

import cards.rawmaterials.RawMaterial;
import effects.Effect;
import game.GameState;


public class RightTradeRawMaterialsForOneCoin implements Effect
{
	public RightTradeRawMaterialsForOneCoin()
	{
		super();
		// default constructor
	}

	@Override
	public void performEffect(GameState gameState, Object... data)
	{
		// no effect. The cost reduction occurs during action generation. 
	}

	@Override
	public ActivationPoint getActivationPoint()
	{
		return ActivationPoint.EVERY_TRADE;
	}

	
}

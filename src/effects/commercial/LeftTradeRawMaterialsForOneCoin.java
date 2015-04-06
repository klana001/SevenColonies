package effects.commercial;

import cards.rawmaterials.RawMaterial;
import effects.Effect;
import game.GameState;


public class LeftTradeRawMaterialsForOneCoin implements Effect
{
//	@Override
//	public String getName()
//	{
//		return "RawMaterial trade with left neighbour costs 1 coin";
//	}
	
	public LeftTradeRawMaterialsForOneCoin()
	{
		super();
		// default constructor
	}


	@Override
	public void performEffect(GameState gameState, Object... data)
	{
		throw new RuntimeException("TBD");
	}

	@Override
	public ActivationPoint getActivationPoint()
	{
		return ActivationPoint.EVERY_TRADE;
	}

	
}

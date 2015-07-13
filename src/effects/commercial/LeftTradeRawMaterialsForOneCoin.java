package effects.commercial;

import player.Player;
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
		// no effect. The cost reduction occurs during action generation. 
	}

	@Override
	public ActivationPoint getActivationPoint(GameState gameState, Player player)
	{
		return ActivationPoint.EVERY_TRADE;
	}

	
}

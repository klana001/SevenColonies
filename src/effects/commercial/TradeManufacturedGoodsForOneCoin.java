package effects.commercial;

import player.Player;
import effects.Effect;
import game.GameState;


public class TradeManufacturedGoodsForOneCoin implements Effect
{
	public TradeManufacturedGoodsForOneCoin()
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

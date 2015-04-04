package effects.commercial;

import effects.Effect;
import game.GameState;


public class TradeManufacturedGoodsForOneCoin implements Effect
{
	public TradeManufacturedGoodsForOneCoin()
	{
		super();
		// default constructor
	}

	
	public TradeManufacturedGoodsForOneCoin(TradeManufacturedGoodsForOneCoin source)
	{
		super();
		// default copy constructor
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

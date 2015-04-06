package wonders.wonderstage;

import java.util.Arrays;
import java.util.Collection;

import game.GameState;
import interfaces.ExchangableItem;
import common.NoCost;
import common.Utilities;
import effects.Effect;

public class PlayLastCardWonderStage extends WonderStage implements Effect
{
	public PlayLastCardWonderStage(Collection<ExchangableItem> cost)
	{
		super(cost);
	}
	
	public PlayLastCardWonderStage()
	{
		this(Arrays.asList(new ExchangableItem[]{new NoCost()}));
	}

	@Override
	public String getName()
	{
		return "Play the last card of each age Wonder Stage";
	}

	@Override
	public void performEffect(GameState gameState, Object... data)
	{
		gameState.addExtraTurn(this);
	}

	@Override
	public ActivationPoint getActivationPoint()
	{
		return ActivationPoint.AT_END_OF_AGE;
	}

	
}

package wonders.wonderstage;

import java.util.Arrays;
import java.util.Collection;

import player.Player;
import game.GameState;
import interfaces.ExchangableItem;
import common.NoCost;
import common.Utilities;
import effects.Effect;

public class PlayLastCardWonderStage extends WonderStage implements Effect
{
	public PlayLastCardWonderStage(Collection<ExchangableItem> cost, int orderIndex)
	{
		super(cost,orderIndex);
	}
	
	public PlayLastCardWonderStage(int orderIndex)
	{
		this(Arrays.asList(new ExchangableItem[]{new NoCost()}),orderIndex);
	}
	
	public PlayLastCardWonderStage()
	{
		this(Arrays.asList(new ExchangableItem[]{new NoCost()}),0);
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
	public ActivationPoint getActivationPoint(GameState gameState, Player player)
	{
		return ActivationPoint.AT_END_OF_AGE;
	}

	
}

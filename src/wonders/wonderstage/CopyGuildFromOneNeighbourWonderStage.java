package wonders.wonderstage;


import java.util.Arrays;
import java.util.Collection;

import player.Player;
import common.NoCost;
import common.Utilities;
import effects.Effect;
import game.GameState;
import interfaces.ExchangableItem;


public class CopyGuildFromOneNeighbourWonderStage extends WonderStage implements Effect
{
	public CopyGuildFromOneNeighbourWonderStage(Collection<ExchangableItem> cost, int orderIndex)
	{
		super(cost,orderIndex);
	}
	
	public CopyGuildFromOneNeighbourWonderStage(int orderIndex)
	{
		this(Arrays.asList(new ExchangableItem[]{new NoCost()}),orderIndex);
	}
	
	public CopyGuildFromOneNeighbourWonderStage()
	{
		this(Arrays.asList(new ExchangableItem[]{new NoCost()}),0);
	}

	@Override
	public String getName()
	{
		return "Copy any Guild from a Neighbour";
	}

	@Override
	public void performEffect(GameState gameState, Object... data)
	{
		throw new RuntimeException("TBD");
		// get neighbour
		// get age 1 wonder stage
		// process wonder stage
	}

	@Override
	public ActivationPoint getActivationPoint(GameState gameState, Player player)
	{
		return ActivationPoint.AT_END_OF_GAME;
	}

	
}

package wonders.wonderstage;


import java.util.Arrays;
import java.util.Collection;

import player.Player;
import common.NoCost;
import common.Utilities;
import effects.Effect;
import game.GameState;
import interfaces.ExchangableItem;
import interfaces.RequestsInput;


public class CopyLeftAgeOneWonderStage extends WonderStage implements Effect
{
	public CopyLeftAgeOneWonderStage(Collection<ExchangableItem> cost, int orderIndex)
	{
		super(cost,orderIndex);
	}
	
	public CopyLeftAgeOneWonderStage(int orderIndex)
	{
		this(Arrays.asList(new ExchangableItem[]{new NoCost()}),orderIndex);
	}

	public CopyLeftAgeOneWonderStage()
	{
		this(Arrays.asList(new ExchangableItem[]{new NoCost()}),0);
	}

	@Override
	public String getName()
	{
		return "Copy Effect of Left-Neighbour Age 1 Wonder";
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
		//gameState.getPlayer(player.getLeftNeighbourId()).getWonder().getWonderStage(getBuildOrderIndex());
		throw new RuntimeException("TBD");
		// dependant on copied wonder... but firstly will be instantaneous to allow peform effect to copy wonder!
	}

	
}

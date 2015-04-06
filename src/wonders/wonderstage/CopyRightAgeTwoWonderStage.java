package wonders.wonderstage;


import java.util.Arrays;
import java.util.Collection;

import common.NoCost;
import common.Utilities;
import effects.Effect;
import game.GameState;
import interfaces.ExchangableItem;


public class CopyRightAgeTwoWonderStage extends WonderStage implements Effect
{
	public CopyRightAgeTwoWonderStage(Collection<ExchangableItem> cost)
	{
		super(cost);
	}
	
	public CopyRightAgeTwoWonderStage()
	{
		this(Arrays.asList(new ExchangableItem[]{new NoCost()}));
	}


	@Override
	public String getName()
	{
		return "Copy Effect of Right-Neighbour Age 2 Wonder";
	}

	@Override
	public void performEffect(GameState gameState, Object... data)
	{
		throw new RuntimeException("TBD");
		// get neighbour
		// get age 2 wonder stage
		// process wonder stage
	}

	@Override
	public ActivationPoint getActivationPoint()
	{
		throw new RuntimeException("TBD");
		// dependant on copied wonder... but firstly will be instantaneous to allow peform effect to copy wonder!
	}

	
}

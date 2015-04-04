package wonders.wonderstage;


import java.util.Arrays;
import java.util.Collection;

import common.NoCost;
import common.Utilities;
import effects.Effect;
import game.GameState;
import interfaces.ExchangableItem;


public class CopyLeftOrRightAgeThreeWonderStage extends WonderStage implements Effect
{
	public CopyLeftOrRightAgeThreeWonderStage(Collection<ExchangableItem> cost)
	{
		super(cost);
	}
	
	public CopyLeftOrRightAgeThreeWonderStage()
	{
		this(Arrays.asList(new ExchangableItem[]{new NoCost()}));
	}

	public CopyLeftOrRightAgeThreeWonderStage(CopyLeftOrRightAgeThreeWonderStage source)
	{
		super(Utilities.cloneList(source.getCost()));
	}
	
	@Override
	public String getName()
	{
		return "Copy Effect of Left or Right-Neighbour Age 3 Wonder";
	}

	@Override
	public void performEffect(GameState gameState, Object... data)
	{
		throw new RuntimeException("TBD");
		// get neighbour
		// get age 3 wonder stage
		// process wonder stage
	}

	@Override
	public ActivationPoint getActivationPoint()
	{
		throw new RuntimeException("TBD");
		// dependant on copied wonder... but firstly will be instantaneous to allow peform effect to copy wonder!
	}

	
}
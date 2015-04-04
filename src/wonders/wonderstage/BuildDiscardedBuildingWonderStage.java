package wonders.wonderstage;


import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import common.NoCost;
import common.Utilities;
import effects.Effect;
import game.GameState;
import interfaces.ExchangableItem;


public class BuildDiscardedBuildingWonderStage extends WonderStage implements Effect
{
	public BuildDiscardedBuildingWonderStage(List<ExchangableItem> cost)
	{
		super(cost);
	}
	
	public BuildDiscardedBuildingWonderStage(BuildDiscardedBuildingWonderStage source)
	{
		super(Utilities.cloneList(source.getCost()));
	}
	
	public BuildDiscardedBuildingWonderStage()
	{
		this(Arrays.asList(new ExchangableItem[]{new NoCost()}));
	}

	@Override
	public String getName()
	{
		return "Build 1 Free Building For Each Age";
	}

	@Override
	public void performEffect(GameState gameState, Object... data)
	{
		throw new RuntimeException("TBD");
	}

	@Override
	public ActivationPoint getActivationPoint()
	{
		return ActivationPoint.INSTANTLY;
	}

	
}

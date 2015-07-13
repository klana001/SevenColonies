package wonders.wonderstage;


import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import player.Player;
import common.NoCost;
import common.Utilities;
import effects.Effect;
import game.GameState;
import interfaces.ExchangableItem;


public class BuildOneFreeBuildingEachAgeWonderStage extends WonderStage implements Effect
{
	public BuildOneFreeBuildingEachAgeWonderStage(List<ExchangableItem> cost,int orderIndex)
	{
		super(cost,orderIndex);
	}
	

	public BuildOneFreeBuildingEachAgeWonderStage(int orderIndex)
	{
		this(Arrays.asList(new ExchangableItem[]{new NoCost()}),orderIndex);
	}
	
	public BuildOneFreeBuildingEachAgeWonderStage()
	{
		this(Arrays.asList(new ExchangableItem[]{new NoCost()}),0);
	}

	@Override
	public String getName()
	{
		return "Build Now a discarded Building for free";
	}

	@Override
	public void performEffect(GameState gameState, Object... data)
	{
		throw new RuntimeException("TBD");
	}

	@Override
	public ActivationPoint getActivationPoint(GameState gameState, Player player)
	{
		return ActivationPoint.ONCE_PER_AGE;
	}

	
}

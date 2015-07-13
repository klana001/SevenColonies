package wonders.wonderstage;


import java.util.Arrays;
import java.util.Collection;

import player.Player;
import common.NoCost;
import common.Utilities;
import effects.Effect;
import game.GameState;
import interfaces.ExchangableItem;


public class RawMaterialTradeWithNeighboursCostOneCoinWonderStage extends WonderStage implements Effect
{
	public RawMaterialTradeWithNeighboursCostOneCoinWonderStage(Collection<ExchangableItem> cost,int orderIndex)
	{
		super(cost,orderIndex);
	}
	
	public RawMaterialTradeWithNeighboursCostOneCoinWonderStage(int orderIndex)
	{
		this(Arrays.asList(new ExchangableItem[]{new NoCost()}),orderIndex);
	}
	
	public RawMaterialTradeWithNeighboursCostOneCoinWonderStage()
	{
		this(Arrays.asList(new ExchangableItem[]{new NoCost()}),0);
	}
	

	@Override
	public String getName()
	{
		return "RawMaterial trade with neighbours costs 1 coin";
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
		return ActivationPoint.EVERY_TURN;
	}

	
}

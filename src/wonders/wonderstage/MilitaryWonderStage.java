package wonders.wonderstage;

import java.util.Arrays;
import java.util.Collection;

import interfaces.ExchangableItem;
import common.Military;
import common.NoCost;
import common.Utilities;

public class MilitaryWonderStage extends WonderStage implements Military
{
	private final int strengh;

	public MilitaryWonderStage( int strength,int orderIndex)
	{
		this(Arrays.asList(new ExchangableItem[]{new NoCost()}),strength,orderIndex);
	}
	
	public MilitaryWonderStage( int strength)
	{
		this(Arrays.asList(new ExchangableItem[]{new NoCost()}),strength,0);
	}
	
	public MilitaryWonderStage(Collection<ExchangableItem> cost, int strength,int orderIndex)
	{
		super(cost,orderIndex);
		this.strengh=strength;
	}
	
	@Override
	public int calculateMilitary()
	{
		return strengh;
	}

	@Override
	public String getName()
	{
		return "Military Wonder Stage";
	}

	
}

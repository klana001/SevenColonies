package wonders.wonderstage;

import java.util.Arrays;
import java.util.Collection;

import interfaces.ExchangableItem;
import common.NoCost;
import common.Scoreable;
import common.Utilities;

public class PointWonderStage extends WonderStage implements Scoreable
{
	private final int score;

	public PointWonderStage( int score, int orderIndex)
	{
		this(Arrays.asList(new ExchangableItem[]{new NoCost()}),score, orderIndex);
	}
	
	public PointWonderStage( int score)
	{
		this(Arrays.asList(new ExchangableItem[]{new NoCost()}),score, 0);
	}
	
	public PointWonderStage(Collection<ExchangableItem> cost, int score,int orderIndex)
	{
		super(cost,orderIndex);
		this.score=score;
	}
	

	@Override
	public int calculateScore()
	{
		return score;
	}

	@Override
	public String getName()
	{
		return "Points Wonder Stage";
	}

	
}

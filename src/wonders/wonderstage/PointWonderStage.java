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

	public PointWonderStage( int score)
	{
		this(Arrays.asList(new ExchangableItem[]{new NoCost()}),score);
	}
	
	public PointWonderStage(Collection<ExchangableItem> cost, int score)
	{
		super(cost);
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

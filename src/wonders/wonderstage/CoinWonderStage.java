package wonders.wonderstage;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import classes.Coin;
import interfaces.ExchangableItem;
import common.Money;
import common.NoCost;
import common.Utilities;

public class CoinWonderStage extends WonderStage implements Money
{
	private final Coin coin;

	public CoinWonderStage( Coin coin,int orderIndex)
	{
		this(Arrays.asList(new ExchangableItem[]{new NoCost()}),coin,orderIndex);
	}
	
	public CoinWonderStage( Coin coin)
	{
		this(Arrays.asList(new ExchangableItem[]{new NoCost()}),coin,0);
	}
	
	public CoinWonderStage(List<ExchangableItem> cost, Coin coin,int orderIndex)
	{
		super(cost,orderIndex);
		this.coin=coin;
	}
	
	@Override
	public Coin getCoin()
	{
		return coin;
	}

	@Override
	public String getName()
	{
		return "Coin Wonder Stage";
	}

	
}

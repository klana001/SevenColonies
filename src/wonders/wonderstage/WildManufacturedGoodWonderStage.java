package wonders.wonderstage;

import interfaces.ExchangableItem;

import java.util.Arrays;
import java.util.Collection;

import player.Player;
import common.NoCost;
import common.Utilities;
import cards.IManufacturedGood;
import effects.manufacturedgoods.WildManufacturedGood;

public class WildManufacturedGoodWonderStage extends WonderStage implements IManufacturedGood
{
	private WildManufacturedGood wildManufacturedGood = new WildManufacturedGood();
	public WildManufacturedGoodWonderStage(Collection<ExchangableItem> cost,int orderIndex)
	{
		super(cost,orderIndex);
	}
	
	public WildManufacturedGoodWonderStage(int orderIndex)
	{
		this(Arrays.asList(new ExchangableItem[]{new NoCost()}),orderIndex);
	}
	
	public WildManufacturedGoodWonderStage()
	{
		this(Arrays.asList(new ExchangableItem[]{new NoCost()}),0);
	}
	

	@Override
	public String getName()
	{
		return "WildManufacturedGoodWonderStage";
	}

	@Override
	public Collection<Type> getManufacturedGoodType()
	{
		return wildManufacturedGood.getManufacturedGoodType();
	}

	@Override
	public Collection<IManufacturedGood> getManufacturedGoods()
	{
		return wildManufacturedGood.getManufacturedGoods();
	}

	@Override
	public boolean equivilent(ExchangableItem otherItem, Player currentPlayer)
	{
		return wildManufacturedGood.equivilent(otherItem, null);
	}

}

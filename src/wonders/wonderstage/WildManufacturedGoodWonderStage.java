package wonders.wonderstage;

import interfaces.ExchangableItem;

import java.util.Arrays;
import java.util.Collection;

import common.NoCost;
import common.Utilities;
import cards.IManufacturedGood;
import effects.manufacturedgoods.WildManufacturedGood;

public class WildManufacturedGoodWonderStage extends WonderStage implements IManufacturedGood
{
	private WildManufacturedGood wildManufacturedGood = new WildManufacturedGood();
	public WildManufacturedGoodWonderStage(Collection<ExchangableItem> cost)
	{
		super(cost);
	}
	
	public WildManufacturedGoodWonderStage()
	{
		this(Arrays.asList(new ExchangableItem[]{new NoCost()}));
	}
	
	public WildManufacturedGoodWonderStage(WildManufacturedGoodWonderStage source)
	{
		super(Utilities.cloneList(source.getCost()));
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

}

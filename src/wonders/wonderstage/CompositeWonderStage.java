package wonders.wonderstage;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import classes.Coin;
import interfaces.ExchangableItem;
import common.Military;
import common.Money;
import common.MutableInteger;
import common.NoCost;
import common.Scoreable;
import common.Utilities;

public class CompositeWonderStage extends WonderStage implements Scoreable,Military,Money
{
	private final List<WonderStage> wonderStages;

	public CompositeWonderStage( List<WonderStage> wonderStages)
	{
		this(Arrays.asList(new ExchangableItem[]{new NoCost()}),wonderStages);
	}
	
	public CompositeWonderStage(List<ExchangableItem> cost, List<WonderStage> wonderStages)
	{
		super(cost);
		this.wonderStages=wonderStages;
	}
	
	public CompositeWonderStage(CompositeWonderStage source)
	{
		super(Utilities.cloneList(source.getCost()));
		wonderStages=Utilities.cloneList(source.wonderStages);
	}
	
	@Override
	public int calculateScore()
	{
		final MutableInteger score = new MutableInteger();
		Utilities.filterElements(wonderStages, Scoreable.class).forEach(stage-> score.val+=stage.calculateScore());
		return score.val;
	}

	@Override
	public String getName()
	{
		return "Composite Wonder Stage";
	}

	@Override
	public Coin getCoin()
	{
		final MutableInteger amount = new MutableInteger();
		Utilities.filterElements(wonderStages, Money.class).forEach(stage-> amount.val+=stage.getCoin().getAmount());
		return new Coin(amount.val);
	}

	@Override
	public int calculateMilitary()
	{
		final MutableInteger strength = new MutableInteger();
		Utilities.filterElements(wonderStages, Military.class).forEach(stage-> strength.val+=stage.calculateMilitary());
		return strength.val;
	}

	
}

package wonders.wonderstage;

import java.util.Collection;

import interfaces.ExchangableItem;
import interfaces.Purchasable;
import classes.Base;

abstract public class WonderStage extends Base implements Purchasable
{
	private boolean built;
	private final Collection<ExchangableItem> cost;

	public WonderStage(Collection<ExchangableItem> cost)
	{
		super();
		this.cost = cost;
	}

	public boolean isBuilt()
	{
		return built;
	}

	public void setBuilt(boolean built)
	{
		this.built = built;
	}
	
	@Override
	public Collection<ExchangableItem> getCost()
	{
		return cost;
	}

	@Override
	public int compareTo(Object arg0)
	{
		return this.getClass().isInstance(arg0)?0:-1;
	}
	
}

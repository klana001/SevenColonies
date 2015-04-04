package classes;

import interfaces.ExchangableItem;

public class Coin extends Base implements ExchangableItem
{
	private int amount = 0;
	
	public Coin(int amount)
	{
		super();
		this.amount = amount;
	}
	
	public Coin(Coin source)
	{
		super();
		this.amount = source.amount;
	}

	public int getAmount()
	{
		return amount;
	}
	
	@Override
	public int compareTo(Object o)
	{
		if (o instanceof Coin)
		{
			return getAmount()-((Coin) o).getAmount();
		}
		return -1;
	}

	@Override
	public String getName()
	{
		return ""+amount+" Coin(s)";
	}
}

package effects.manufacturedgoods;



import java.util.Arrays;
import java.util.Collection;

import cards.IManufacturedGood;
import cards.manufacturedgoods.Glass;
import cards.manufacturedgoods.Papyrus;
import cards.manufacturedgoods.Textile;
import effects.Effect;
import effects.commercial.TradeManufacturedGoodsForOneCoin;
import game.GameState;


public class WildManufacturedGood implements Effect, IManufacturedGood
{
	public WildManufacturedGood()
	{
		super();
		// default constructor
	}

	
	public WildManufacturedGood(WildManufacturedGood source)
	{
		super();
		// default copy constructor
	}

	@Override
	public Collection<Type> getManufacturedGoodType()
	{
		return Arrays.asList(Type.values());
	}

	@Override
	public Collection<IManufacturedGood> getManufacturedGoods()
	{
		return Arrays.asList(new IManufacturedGood[]{new Textile(),new Papyrus(), new Glass()});
	}

	@Override
	public int compareTo(Object o)
	{
		return (o instanceof IManufacturedGood)?0:-1;
	}

	@Override
	public String getName()
	{
		return "WildManufacturedGood";
	}

	@Override
	public ActivationPoint getActivationPoint()
	{
		return null; // effect is redundant.
	}

	@Override
	public void performEffect(GameState gameState, Object... data)
	{
		throw new RuntimeException("no effect!");
		
	}

}

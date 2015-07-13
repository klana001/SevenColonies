package effects.manufacturedgoods;



import interfaces.ExchangableItem;

import java.util.Arrays;
import java.util.Collection;

import player.Player;
import classes.Coin;
import cards.IManufacturedGood;
import cards.IManufacturedGood;
import cards.IManufacturedGood.Type;
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
	public ActivationPoint getActivationPoint(GameState gameState, Player player)
	{
		return null; // effect is redundant.
	}

	@Override
	public void performEffect(GameState gameState, Object... data)
	{
		throw new RuntimeException("no effect!");
		
	}
	
	@Override
	public boolean equivilent(ExchangableItem otherItem, Player currentPlayer)
	{
		if (otherItem instanceof IManufacturedGood)
		{
			IManufacturedGood otherMaterial = (IManufacturedGood) otherItem;
			for (Type type : getManufacturedGoodType())
			{
				if (otherMaterial.getManufacturedGoodType().contains(type))
				{
					return true;
				}
			}
		}
		return false;
	}

}

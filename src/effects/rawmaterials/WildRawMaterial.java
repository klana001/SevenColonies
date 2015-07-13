package effects.rawmaterials;

import interfaces.ExchangableItem;

import java.util.Arrays;
import java.util.Collection;

import player.Player;
import cards.IRawMaterial;
import cards.IRawMaterial.Type;
import cards.rawmaterials.Clay;
import cards.rawmaterials.Ore;
import cards.rawmaterials.Stone;
import cards.rawmaterials.Wood;
import effects.Effect;
import effects.manufacturedgoods.WildManufacturedGood;
import game.GameState;


public class WildRawMaterial implements Effect, IRawMaterial
{
	public WildRawMaterial()
	{
		super();
		// default constructor
	}
	

	@Override
	public String getName()
	{
		return "WildRawMaterial";
	}

	@Override
	public Collection<Type> getRawMaterialType()
	{
		return Arrays.asList(Type.values());
	}

	@Override
	public Collection<IRawMaterial> getRawMaterials()
	{
		return Arrays.asList(new IRawMaterial[]{new Clay(), new Ore(),new Wood(),new Stone()});
	}
	
	@Override
	public int compareTo(Object arg0)
	{
		return (arg0 instanceof IRawMaterial)?0:-1;
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
		if (otherItem instanceof IRawMaterial)
		{
			IRawMaterial otherMaterial = (IRawMaterial) otherItem;
			for (Type type : getRawMaterialType())
			{
				if (otherMaterial.getRawMaterialType().contains(type))
				{
					return true;
				}
			}
		}
		return false;
	}
}

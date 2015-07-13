package effects.commercial;

import java.util.ArrayList;
import java.util.Collection;

import player.Player;
import wonders.wonderstage.WonderStage;
import cards.rawmaterials.RawMaterial;
import effects.Effect;
import game.GameState;


public class ThreeCoinPerBuiltWonderStage implements Effect
{
	public ThreeCoinPerBuiltWonderStage()
	{
		super();
		// default constructor
	}


	
	@Override
	public void performEffect(GameState gameState, Object... data)
	{
		// get wonderstages
		Collection<WonderStage> wonderStages = new ArrayList<WonderStage>();
		
		int coinsToAdd=0;
		for (WonderStage stage : wonderStages)
		{
			if (stage.isBuilt())
			{
				coinsToAdd++;
			}
		}
	
		// add coins to total
		int coins = 0 + coinsToAdd;

	}

	@Override
	public ActivationPoint getActivationPoint(GameState gameState, Player player)
	{
		return ActivationPoint.INSTANTLY;
	}

}

package effects.commercial;

import java.util.ArrayList;
import java.util.Collection;

import wonders.wonderstage.WonderStage;
import cards.rawmaterials.RawMaterial;
import effects.Effect;
import game.GameState;


public class OnePointPerBuiltWonderStage implements Effect
{

	public OnePointPerBuiltWonderStage()
	{
		super();
		// default  constructor
	}
	
	
	@Override
	public void performEffect(GameState gameState, Object... data)
	{
		// get wonderstages
		Collection<WonderStage> wonderStages = new ArrayList<WonderStage>();
		
		int pointsToAdd=0;
		for (WonderStage stage : wonderStages)
		{
			if (stage.isBuilt())
			{
				pointsToAdd++;
			}
		}
	
		// add points to total
		int points = 0 + pointsToAdd;

	}

	@Override
	public ActivationPoint getActivationPoint()
	{
		return ActivationPoint.AT_END_OF_GAME;
	}

}

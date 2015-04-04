package classes;

import interfaces.GameElement;

import java.util.ArrayList;
import java.util.HashMap;

import wonders.wonderstage.MilitaryWonderStage;
import common.Utilities;
import effects.Effect;
import effects.Effect.ActivationPoint;

public class Game1
{
	ArrayList<Player> players = new ArrayList<Player>();
	HashMap<GameElement, Player> gameElementOwnership = new HashMap<GameElement, Player>();
	
	
	
	
	public static Stage stage = Stage.SETUP;
	
	public enum Stage
	{
		SETUP,
		AGE1_INPROGRESS,
		AGE1_LAST_TURN,
		AGE1_MILITARY,
		AGE2_INPROGRESS,
		AGE2_LAST_TURN,
		AGE2_MILITARY,
		AGE3_INPROGRESS,
		AGE3_LAST_TURN,
		AGE3_MILITARY,

		END
	}
	
	// at end of age...
//	Utilities.filterElements(wonderStages, MilitaryWonderStage.class).stream().filter(stage-> stage.isBuilt()).forEach(stage->
//	{
//		if (!stage.hasBeenUsed() && stage instanceof Effect)
//		{
//			Effect effectWonderStage = (Effect) stage;
//			if ((effectWonderStage.getActivationPoint()==ActivationPoint.AT_END_OF_AGE && (Game.stage==Stage.AGE1_LAST_TURN || Game.stage==Stage.AGE2_LAST_TURN || Game.stage==Stage.AGE3_LAST_TURN)) ||
//				(effectWonderStage.getActivationPoint()==ActivationPoint.AT_END_OF_GAME && Game.stage==Stage.END))
//			{
//				effectWonderStage.performEffect();
//			}
//		}
//	});

	public static void addExtraTurn(GameElement gameElement)
	{
		// get the owner of the gameElement
		// give the owner one more turn
	}
}

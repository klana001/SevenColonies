package player;

import game.Action;
import game.Game;
import game.GameState;

import java.util.Collection;
import java.util.List;
import java.util.Random;

import actions.BuildWonderStage;
import actions.DiscardForThreeCoins;
import wonders.Wonder;

public class NullPlayer extends Player
{
	public NullPlayer(String name)
	{
		super(name);
	}
	
	@Override
	public Wonder chooseWonder(Collection<Wonder> wonders)
	{
		return wonders.stream().findFirst().orElse(null);
	}

	@Override
	public Action chooseAction(List<Action> actionCandidates, GameState gameState)
	{
//		System.out.println("Actions for player: "+this);
		
//		actionCandidates.stream().forEach(a->System.out.println(a));
		
		Action action = actionCandidates.get(Game.rand.nextInt(actionCandidates.size()));
		
		if (action instanceof DiscardForThreeCoins || action instanceof BuildWonderStage)
		{
			try
			{
				action.setData(getHand().get(Game.rand.nextInt(getHand().size())));
			}
			catch (Exception e)
			{
				throw new RuntimeException(e);
			}
		}

		
		
		
//		System.out.println("Action chosen: "+action);
		
		return action;
	}

	@Override
	public Object[] requestData(Object... data)
	{
		// TODO Auto-generated method stub
		return null;
	}

}

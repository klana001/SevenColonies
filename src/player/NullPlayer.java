package player;

import game.Action;
import game.Game;

import java.util.Collection;
import java.util.List;
import java.util.Random;

import wonders.Wonder;

public class NullPlayer extends Player
{
	public NullPlayer()
	{
		
	}
	
	public NullPlayer(NullPlayer source)
	{
		super(source);
	}
	
	@Override
	public Wonder chooseWonder(Collection<Wonder> wonders)
	{
		return wonders.stream().findFirst().orElse(null);
	}

	@Override
	public Action chooseAction(List<Action> actionCandidates)
	{
		System.out.println("Actions for player: "+this);
		
		actionCandidates.stream().forEach(a->System.out.println(a));
		
		Action action = actionCandidates.get(Game.rand.nextInt(actionCandidates.size()));
		
		System.out.println("Action chosen: "+action);
		
		return action;
	}

}

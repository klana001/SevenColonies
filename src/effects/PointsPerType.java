package effects;

import player.Player;
import game.GameState;

abstract public class PointsPerType implements Effect
{
	private int pointsPerType;
	private Class type;
	
	public PointsPerType(int pointsPerType, Class type)
	{
		super();
		this.pointsPerType = pointsPerType;
		this.type=type;
	}

	@Override
	public void performEffect(GameState gameState, Object... data)
	{
		// have to go though all of the given type and then add coinsPerType per each type instance found.
		
	}

	@Override
	public ActivationPoint getActivationPoint(GameState gameState, Player player)
	{
		return ActivationPoint.AT_END_OF_GAME;
	}

}

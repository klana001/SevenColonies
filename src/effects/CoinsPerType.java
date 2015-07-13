package effects;

import player.Player;
import game.GameState;

abstract public class CoinsPerType implements Effect
{
	private int coinsPerType;
	private Class type;
	
	public CoinsPerType(int coinsPerType, Class type)
	{
		super();
		this.coinsPerType = coinsPerType;
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
		return ActivationPoint.INSTANTLY;
	}

}

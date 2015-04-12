package effects;

import game.GameState;

public interface Effect
{
	public enum ActivationPoint
	{
		INSTANTLY,
		AT_END_OF_AGE,
		AT_END_OF_GAME,
		ONCE_PER_AGE,
		EVERY_TURN,
		EVERY_TRADE, TAKE_FROM_BANK
	}
	
	public enum Scope
	{
		LEFT,
		RIGHT,
		OWN
	}
	
	public void performEffect(GameState gameState, Object... data);
	
	public ActivationPoint getActivationPoint();
}

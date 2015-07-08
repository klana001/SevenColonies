package game;

import interfaces.GameElement;

public interface MilitaryToken extends GameElement
{
	/**
	 * Returns an amount to which a player's score should be modified due to the token.
	 * @return
	 */
	int getScoreModificationAmount();
}

package interfaces;

import game.GameState;
import player.Player;

/**
 * Seeks additional input from player 
 * @author default
 *
 */
public interface RequestsInput
{
	public Object[] requestData(GameState gameState, Player player);
}

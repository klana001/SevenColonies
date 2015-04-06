package game;

import player.Player;

public class BankPayment extends Payment
{

	public BankPayment(int cost)
	{
		super(cost);
	}

	@Override
	public void perform(GameState gameState, Player currentPlayer)
	{
		currentPlayer.setCoins(currentPlayer.getCoins()-getCost());
	}

}

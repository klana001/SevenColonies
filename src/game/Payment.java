package game;

import player.Player;

abstract public class Payment
{
	private int cost;
	
	
	
	public Payment(int cost)
	{
		super();
		this.cost = cost;
	}



	abstract public void perform(GameState gameState, Player currentPlayer);



	public int getCost()
	{
		return cost;
	}
}

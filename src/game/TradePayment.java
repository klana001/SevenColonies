package game;

import player.Player;

public class TradePayment extends Payment
{
	final int tradingWithPlayerId;
	
	public TradePayment(int cost,Player tradingWithPlayer)
	{
		super(cost);
		this.tradingWithPlayerId=tradingWithPlayer.getId();
	}

	@Override
	public void perform(GameState gameState, Player currentPlayer)
	{
		currentPlayer.setCoins(currentPlayer.getCoins()-getCost());
		Player tradingWithPlayer = gameState.getPlayer(tradingWithPlayerId);
		tradingWithPlayer.setCoins(tradingWithPlayer.getCoins()+getCost());
	}

}

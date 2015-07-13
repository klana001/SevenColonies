package game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import common.Utilities;

import effects.Effect;
import effects.Effecting;
import effects.Effect.ActivationPoint;
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
		
		final List<Effect> currentPlayerTradeEffects = new ArrayList<Effect>();
		Utilities.filterElements(currentPlayer.getGameElements(),Effecting.class).stream().forEach(effecting->
				currentPlayerTradeEffects.addAll(
						effecting.getEffect().stream().filter(e->
							e.getActivationPoint(gameState, currentPlayer)==ActivationPoint.EVERY_TRADE).collect(Collectors.toList())));
		
		final List<Effect> tradingWithPlayerTradeEffects = new ArrayList<Effect>();
		Utilities.filterElements(tradingWithPlayer.getGameElements(),Effecting.class).stream().forEach(effecting->
			tradingWithPlayerTradeEffects.addAll(
						effecting.getEffect().stream().filter(e->
							e.getActivationPoint(gameState, tradingWithPlayer)==ActivationPoint.EVERY_TRADE).collect(Collectors.toList())));
		
		for (Effect effect : currentPlayerTradeEffects)
		{
			effect.performEffect(gameState, currentPlayer, tradingWithPlayer);
		}
		
		for (Effect effect : tradingWithPlayerTradeEffects)
		{
			effect.performEffect(gameState, currentPlayer, tradingWithPlayer);
		}
	}

}

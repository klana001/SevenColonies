package actions;

import java.util.ArrayList;
import java.util.List;

import classes.Card;
import classes.CostedOwnedExchangableItem;
import player.Player;
import game.Action;
import game.GameState;
import game.Payment;

public class Upgrade extends Action
{

	final private Card prequisite;
	final private Card upgrade;

	public Upgrade(Player owner, Card prequisite, Card upgrade)
	{
		super(owner);
		this.prequisite=prequisite;
		this.upgrade=upgrade;
	}

	@Override
	public void setData(Object... data) throws Exception
	{
		// do nothing.
	}

	@Override
	public Object[] getData()
	{
		return null;
	}

	@Override
	public void perform(GameState gamestate, GameState newGameState) throws Exception
	{
		Player player=newGameState.getPlayer(owner.getId());
		player.placeCard(newGameState,getUpgrade());
	}

	@Override
	public List<Payment> getPayments()
	{
		return new ArrayList<Payment>();
	}

	public Card getPrequisite()
	{
		return prequisite;
	}

	public Card getUpgrade()
	{
		return upgrade;
	}

	public String toString()
	{
		String result = "Upgrade: \n";
		result+="\tPre-requisite Card:"+prequisite+"\n";
		result+="\tUpgrading for Card:"+upgrade+"\n";
		
		
		return result;
	}
}

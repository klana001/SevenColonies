package player;

import game.Action;
import game.GameState;
import interfaces.GameElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import common.Utilities;
import classes.Card;
import classes.Hand;
import wonders.Wonder;

abstract public class Player
{
	private String name;
	private Wonder wonder;
	private Hand hand;
	private int leftNeighbourId;
	private int rightNeighbourId;
	private List<GameElement> gameElements = new ArrayList<GameElement>();
	private List<GameElement> gameElementsNewThisAge = new ArrayList<GameElement>();
	private Action action;
	private int coins;
	private int id;

	abstract public Wonder chooseWonder(Collection<Wonder> wonders);
	
	public Player(String name)
	{
		this.name= name;
	}

	public void setWonder(Wonder chosenWonder)
	{
		this.wonder= chosenWonder;
		gameElements.addAll(wonder.getWonderBenefit());
	}

	public void setHand(Hand hand)
	{
		this.hand=hand;
	}

	public Hand getHand()
	{
		return hand;
	}

	public void setLeftNeighbourId(int playerId)
	{
		this.leftNeighbourId = playerId;
	}

	public void setRightNeighbourId(int playerId)
	{
		this.rightNeighbourId = playerId;
	}

	public int getLeftNeighbourId()
	{
		return leftNeighbourId;
	}

	public int getRightNeighbourId()
	{
		return rightNeighbourId;
	}

	public List<GameElement> getGameElements()
	{
		return gameElements;
	}

	public void setGameElements(ArrayList<GameElement> getGameElements)
	{
		this.gameElements = getGameElements;
	}

	public List<GameElement> getGetGameElementsNewThisAge()
	{
		return this.gameElementsNewThisAge;
	}

	// does this player have cards to play?
	public boolean canPerformMoreActions()
	{
		return hand.size()>1;
	}

	abstract public Action chooseAction(List<Action> actionCandidates);

	public Action getAction()
	{
		return action;
	}

	public void setAction(Action playerChosenAction)
	{
		this.action = playerChosenAction;
		
	}

	public int getCoins()
	{
		return coins;
	}

	public void setCoins(int coins)
	{
		this.coins=coins;
		
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public void placeCard(GameState gameState, Card card)
	{
		if (!getHand().remove(card))
		{
			throw new RuntimeException("tried to remove card: "+card+ " from player: "+this+" however player did not have the card in hand.");
		}
		
		addGameElement(card);
	}
	
	private void addGameElement(GameElement element)
	{
		gameElementsNewThisAge.add(element);
		gameElements.add(element);
	}

	public String getName()
	{
		return name;
	}
	
	@Override
	public String toString()
	{
		return getName();
	}

	public Wonder getWonder()
	{
		return wonder;
	}
	
}

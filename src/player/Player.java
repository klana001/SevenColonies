package player;

import game.Action;
import interfaces.GameElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import common.Utilities;
import classes.Hand;
import wonders.Wonder;

abstract public class Player
{
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
	
	public Player()
	{
	}
	
	// copy constructor
	public Player(Player source)
	{
		wonder = new Wonder(source.wonder);
		hand = new Hand(source.hand);
		leftNeighbourId = source.leftNeighbourId;
		rightNeighbourId = source.rightNeighbourId;
		gameElements = Utilities.cloneList(source.gameElements);
		gameElementsNewThisAge = Utilities.cloneList(source.gameElementsNewThisAge);
		action = Utilities.cloneObject(source.action);
		coins = source.getCoins();
		setId(source.getId());
	}

	public void setWonder(Wonder chosenWonder)
	{
		this.wonder= chosenWonder;
		
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

	public boolean canPerformMoreActions()
	{
		throw new RuntimeException("TODO");
//		return false;
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


}

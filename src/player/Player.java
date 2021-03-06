package player;

import game.Action;
import game.GameState;
import game.MilitaryToken;
import interfaces.GameElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import common.Utilities;
import classes.Card;
import classes.Hand;
import wonders.Wonder;
import wonders.wonderstage.WonderStage;

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
	private int score=0;
	private List<MilitaryToken> militaryTokens = new ArrayList<MilitaryToken>(); 

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

	public List<GameElement> getGetGameElementsNewThisAge()
	{
		return this.gameElementsNewThisAge;
	}

	// does this player have cards to play?
	public boolean canPerformMoreActions()
	{
		return hand.size()>1;
	}

	abstract public Action chooseAction(List<Action> actionCandidates, GameState gameState);

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
	
	public void buildWonderStage(WonderStage wonderStage,GameState gameState, Card buriedCard)
	{
		if (!getHand().remove(buriedCard))
		{
			throw new RuntimeException("tried to bury card: "+buriedCard+ " from player: "+this+" however player did not have the card in hand.");
		}
		getWonder().buildStage(wonderStage,gameState,this);
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

	public int getScore()
	{
		return score;
	}

	public void modifyScore(int i)
	{
		score+=i;
	}
	
	public void addMilitaryToken(MilitaryToken token)
	{
		militaryTokens.add(token);
		addGameElement(token);
	}

	public List<MilitaryToken> getMilitaryTokens()
	{
		return new ArrayList<MilitaryToken>(militaryTokens);
	}

	abstract public Object[] requestData(Object... data);
	
}

package game;

import player.Player;

abstract public class Action
{
	private int id;
	public Player owner;
	private int priority;
	
	
	public int getId()
	{
		return id;
	}

	public Action(int id,Player owner)
	{
		super();
		priority = 0;
		this.id = id;
		this.owner=owner;
	}
	
	abstract public void setData(Object data) throws Exception;
	

	abstract public void perform(GameState gamestate, GameState newGameState) throws Exception;

	public int getPriority()
	{
		return priority;
	}

	public void setPriority(int priority)
	{
		this.priority = priority;
	}

	public Player getOwner()
	{
		return owner;
	}
	
	
	
}

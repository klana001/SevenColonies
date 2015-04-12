package game;

import java.util.List;

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

	public Action(Player owner)
	{
		super();
		priority = 0;

		this.owner=owner;
	}
	
	abstract public void setData(Object... data) throws Exception;
	abstract public Object[] getData();
	

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

	/**
	 * Returns the list of payements required to be performed for this action
	 * @return
	 */
	abstract public List<Payment> getPayments();

	public void setId(int id)
	{
		this.id = id;
	}
	
	
	
}

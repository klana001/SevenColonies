package classes;

import java.util.HashMap;
import java.util.Map;

import game.Age;

abstract public class NamedCard extends Card
{
	
	protected static Map<String,NamedCard> knownNamedCards = new HashMap<String,NamedCard>();
	
	public NamedCard(Age age)
	{
		super(age);
	}

	public int compareTo(Object o)
	{
		if (o instanceof NamedCard)
		{
			NamedCard other = (NamedCard) o;
			return getName().equals(other.getName())?0:-1;
		}	
		return -1;
	}
}

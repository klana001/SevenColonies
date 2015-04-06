package classes;

import game.Age;

import cards.rawmaterials.RawMaterial;

abstract public class NamedCard extends Card
{
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

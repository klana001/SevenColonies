package cards;

import classes.Base;
import classes.Card;
import game.Age;
import interfaces.GameElement;
import interfaces.Purchasable;

abstract public class Guild extends Card
{
	public Guild(Age age)
	{
		super(age);
	}

	public Colour getColour()
	{
		return Colour.PURPLE;
	}
}

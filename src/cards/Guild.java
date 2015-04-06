package cards;

import classes.Base;
import classes.Card;
import classes.NamedCard;
import game.Age;
import interfaces.GameElement;
import interfaces.Purchasable;

abstract public class Guild extends NamedCard
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

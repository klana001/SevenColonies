package classes;

import game.Age;
import interfaces.Purchasable;

import java.util.Collection;

import common.Utilities;



abstract public class Card extends Base implements Purchasable
{
	private Age age;
	
	public Card(Age age)
	{
		super();
		this.age = age;
	}
	
	public Age getAge()
	{
		return age;
	}

	public enum Colour
	{
		RED,
		GREEN,
		YELLOW,
		BLACK,
		BLUE,
		BROWN,
		GREY,
		PURPLE
	}
	
	abstract public Colour getColour();
	
	@SuppressWarnings("unchecked")
	/**
	 * filters the provided collection of cards to those that match the provided template type 
	 * @param cards
	 * @return
	 */
	public static<T> Collection<T> filterCards(Collection<Card> cards, Class<T> filterType)
	{
		return Utilities.filterElements(cards, filterType);
	}
}

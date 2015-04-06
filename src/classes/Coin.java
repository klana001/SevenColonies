package classes;

import player.Player;
import cards.IRawMaterial;
import interfaces.ExchangableItem;

public class Coin extends Base implements ExchangableItem
{
	private int amount = 0;
	
	public Coin(int amount)
	{
		super();
		this.amount = amount;
	}

	public int getAmount()
	{
		return amount;
	}
	
	@Override
	public int compareTo(Object o)
	{
		if (o instanceof Coin)
		{
			return getAmount()-((Coin) o).getAmount();
		}
		return -1;
	}

	@Override
	public String getName()
	{
		return ""+amount+" Coin(s)";
	}
	
	@Override
	public boolean equivilent(ExchangableItem otherItem, Player currentPlayer)
	{
		if (otherItem instanceof Coin)
		{
			Coin currentPlayerCoin=new Coin(currentPlayer.getCoins());
			Coin otherCoin = (Coin) otherItem;
			boolean compareResult = currentPlayerCoin.compareTo(this)>=0;
			boolean othercompareResult = currentPlayerCoin.compareTo(otherCoin)>=0;
			
			// if this coin and other coin are less than the player's coin or this coin and other coin are both greater than the player's coin then they are deemed equivilent
			// for the purposes of player purchasbility.
			return (compareResult && othercompareResult) || !(compareResult || othercompareResult );
		}
		return false;
	}
}

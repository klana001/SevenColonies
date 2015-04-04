package interfaces;

import java.util.Collection;

/**
 * The game element is purchasble by a player by means of ExchangeableItems 
 * @author default
 *
 */
public interface Purchasable
{
	public Collection<ExchangableItem> getCost();
}

package cards;

import interfaces.ExchangableItem;

import java.util.Collection;


public interface IManufacturedGood extends ExchangableItem
{
	public enum Type
	{
		GLASS,
		PAPYRUS,
		TEXTILE
	}
	
	public Collection<Type> getManufacturedGoodType();
	
	public Collection<IManufacturedGood> getManufacturedGoods(); 
	

}

package wonders.wonderstage;

import interfaces.ExchangableItem;

import java.util.Arrays;
import java.util.Collection;

import player.Player;
import common.NoCost;
import common.Utilities;
import cards.IRawMaterial;
import effects.rawmaterials.WildRawMaterial;

public class WildRawMaterialWonderStage extends WonderStage implements IRawMaterial
{
	private WildRawMaterial wildRawMaterial = new WildRawMaterial();
	public WildRawMaterialWonderStage(Collection<ExchangableItem> cost)
	{
		super(cost);
	}
	
	public WildRawMaterialWonderStage()
	{
		this(Arrays.asList(new ExchangableItem[]{new NoCost()}));
	}
	


	@Override
	public String getName()
	{
		return "WildRawMaterialWonderStage";
	}

	@Override
	public Collection<Type> getRawMaterialType()
	{
		return wildRawMaterial.getRawMaterialType();
	}

	@Override
	public Collection<IRawMaterial> getRawMaterials()
	{
		return wildRawMaterial.getRawMaterials();
	}

	@Override
	public boolean equivilent(ExchangableItem otherItem, Player currentPlayer)
	{
		return wildRawMaterial.equivilent(otherItem, null);
	}

}

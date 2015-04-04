package wonders.wonderstage;

import interfaces.ExchangableItem;

import java.util.Arrays;
import java.util.Collection;

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
	
	public WildRawMaterialWonderStage(WildRawMaterialWonderStage source)
	{
		super(Utilities.cloneList(source.getCost()));
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

}

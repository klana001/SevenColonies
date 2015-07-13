package wonders.wonderstage;

import interfaces.ExchangableItem;

import java.util.Arrays;
import java.util.Collection;

import common.NoCost;
import common.Utilities;
import cards.IScientificStructure;

public class WildScientificStructureWonderStage extends WonderStage implements IScientificStructure
{

	public WildScientificStructureWonderStage(Collection<ExchangableItem> cost,int orderIndex)
	{
		super(cost,orderIndex);
	}
	
	public WildScientificStructureWonderStage(int orderIndex)
	{
		this(Arrays.asList(new ExchangableItem[]{new NoCost()}),orderIndex);
	}
	
	public WildScientificStructureWonderStage()
	{
		this(Arrays.asList(new ExchangableItem[]{new NoCost()}),0);
	}
	
	@Override
	public String getName()
	{
		return "WildScientificStructureMaterialWonderStage";
	}


	@Override
	public Collection<Type> getScientificStructureType()
	{
		return Arrays.asList(Type.values());
	}

	@Override
	public Collection<IScientificStructure> getScientificStructures()
	{
		return Arrays.asList(this);
	}

}

package effects;

import game.GameState;

import java.util.Arrays;
import java.util.Collection;

import org.json.simple.JSONObject;

import common.JSONReadable;

import cards.IScientificStructure;

public class WildScientificStructure implements Effect, IScientificStructure, JSONReadable
{


	private ActivationPoint activationPoint;

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

	@Override
	public void performEffect(GameState gameState, Object... data)
	{
		// TODO Auto-generated method stub
		throw new RuntimeException("TBD");
	}

	@Override
	public ActivationPoint getActivationPoint()
	{
		return activationPoint;
	}

	public void readFromJSON(JSONObject json) throws Exception
	{
		activationPoint=ActivationPoint.valueOf((String) json.get("activationPoint"));
	}
}

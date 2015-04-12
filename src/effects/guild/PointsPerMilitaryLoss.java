package effects.guild;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.simple.JSONObject;

import common.JSONReadable;
import common.MutableInteger;
import common.Utilities;
import player.Player;
import effects.Effect;
import game.GameState;

public class PointsPerMilitaryLoss implements Effect,JSONReadable
{


	@SuppressWarnings("unchecked")
	@Override
	public void readFromJSON(JSONObject json) throws Exception
	{
		points = Integer.parseInt((String) json.get("points"));
		scope = new ArrayList<Effect.Scope>();
		
		((Collection<String>) json.get("scope")).stream().forEach(o->scope.add(Scope.valueOf(o)));
		
		activationPoint=ActivationPoint.valueOf((String) json.get("activationPoint"));
	}
	
	List<Scope> scope;
	@SuppressWarnings("rawtypes")
	ActivationPoint activationPoint;
	private int points;

	
	
	@Override
	public void performEffect(GameState gameState, Object... data)
	{
		throw new RuntimeException("TBD");
	}



	@Override
	public ActivationPoint getActivationPoint()
	{
		return activationPoint;
	}
}

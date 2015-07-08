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
import effects.Effect.Scope;
import game.DefeatToken;
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
		if (data[0] instanceof Player)
		{
			Player player = (Player) data[0];
			int points = 0;
			if (scope.contains(Scope.LEFT))
			{
				points += calculatePoints(gameState.getPlayer(player.getLeftNeighbourId()));
			}
			
			if (scope.contains(Scope.RIGHT))
			{
				points += calculatePoints(gameState.getPlayer(player.getRightNeighbourId()));
			}
			
			if (scope.contains(Scope.OWN))
			{
				points += calculatePoints(player);
			}
			
			player.modifyScore(points);
		}
		else
		{
			throw new RuntimeException("data[0] is not a Player");
		}
	}

	private int calculatePoints(Player player)
	{
		return (int) Utilities.filterElements(player.getMilitaryTokens().stream(), DefeatToken.class).count()*points;
	}

	@Override
	public ActivationPoint getActivationPoint()
	{
		return activationPoint;
	}
}

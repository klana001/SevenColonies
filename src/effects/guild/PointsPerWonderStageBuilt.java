package effects.guild;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.simple.JSONObject;

import common.JSONReadable;
import common.MutableInteger;
import player.Player;
import effects.Effect;
import game.GameState;

public class PointsPerWonderStageBuilt implements Effect,JSONReadable
{
	private List<Scope> scope;
	private ActivationPoint activationPoint;
	private int points;
	
	public void readFromJSON(JSONObject json) throws Exception
	{
		points = Integer.parseInt((String) json.get("points"));
		
		scope = new ArrayList<Effect.Scope>();
		
		((Collection<String>) json.get("scope")).stream().forEach(o->scope.add(Scope.valueOf(o)));
		
		activationPoint=ActivationPoint.valueOf((String) json.get("activationPoint"));
	}

	
	
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
		return (int) player.getWonder().getWonderStages().stream().filter(s->s.isBuilt()).count()*points;
	}

	@Override
	public ActivationPoint getActivationPoint(GameState gameState, Player player)
	{
		return activationPoint;
	}
	
}

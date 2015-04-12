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

public class PointsPerCardType implements Effect,JSONReadable
{


	@SuppressWarnings("unchecked")
	@Override
	public void readFromJSON(JSONObject json) throws Exception
	{
		points = Integer.parseInt((String) json.get("points"));
		String effectTypeString = (String) json.get("type");
		
		type = ClassLoader.getSystemClassLoader().loadClass(effectTypeString);
		
		scope = new ArrayList<Effect.Scope>();
		
		((Collection<String>) json.get("scope")).stream().forEach(o->scope.add(Scope.valueOf(o)));
		
		activationPoint=ActivationPoint.valueOf((String) json.get("activationPoint"));
	}
	
	List<Scope> scope;
	@SuppressWarnings("rawtypes")
	Class type;
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
			
			if (data[1] instanceof MutableInteger)
			{
				((MutableInteger) data[1]).val+=points;
			}
			else
			{
				throw new RuntimeException("data[1] is not a MutableInteger");
			}
		}
		else
		{
			throw new RuntimeException("data[0] is not a Player");
		}
	}

	@SuppressWarnings("unchecked")
	private int calculatePoints(Player player)
	{
		return (int) Utilities.filterElements(player.getGameElements(), type).stream().count()*points;
	}



	@Override
	public ActivationPoint getActivationPoint()
	{
		return activationPoint;
	}
}

package effects;

import effects.Effect.Scope;
import game.GameState;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.json.simple.JSONObject;

import player.Player;
import common.JSONReadable;
import common.Utilities;
import cards.IScientificStructure;
import cards.scientificstructures.Cog;
import cards.scientificstructures.Sextant;
import cards.scientificstructures.Tablet;

public class WildScientificStructure implements Effect, IScientificStructure, JSONReadable
{

	private IScientificStructure assignedScientificStructure = null;

	private ActivationPoint activationPoint;

	@Override
	public Collection<Type> getScientificStructureType()
	{
		return Arrays.asList(Type.values());
	}

	@Override
	public Collection<IScientificStructure> getScientificStructures()
	{
		if (assignedScientificStructure==null)
		{
			throw new RuntimeException("Wild scientific structure has not been assigned a type.");
		}
		return Arrays.asList(assignedScientificStructure);
	}

	@Override
	public void performEffect(GameState gameState, Object... data)
	{
		if (data[0] instanceof Player)
		{
			Player player = (Player) data[0];
			
			int cogCount = (int) Utilities.filterElements(player.getGameElements(), Cog.class).stream().count();
			int sextantCount = (int) Utilities.filterElements(player.getGameElements(), Sextant.class).stream().count();
			int tabletCount = (int) Utilities.filterElements(player.getGameElements(), Tablet.class).stream().count();
			
			int setCount=Math.min(Math.min(cogCount, sextantCount),tabletCount);
			
			int setCountExtraCog=Math.min(Math.min(cogCount+1, sextantCount),tabletCount);
			int setCountExtraSextant=Math.min(Math.min(cogCount, sextantCount+1),tabletCount);
			int setCountTabletSextant=Math.min(Math.min(cogCount, sextantCount),tabletCount+1);
			
			if (setCountExtraCog>setCount)
			{
				assignedScientificStructure=new Cog();
			}
			else if (setCountExtraSextant>setCount)
			{
				assignedScientificStructure=new Sextant();
			}
			else if (setCountTabletSextant>setCount)
			{
				assignedScientificStructure=new Tablet();
			}
			else if (cogCount>sextantCount && cogCount>tabletCount)
			{
				assignedScientificStructure=new Cog();
			}
			else if (sextantCount>cogCount && sextantCount>tabletCount)
			{
				assignedScientificStructure=new Sextant();
			}
			else if (tabletCount>cogCount && tabletCount>sextantCount)
			{
				assignedScientificStructure=new Tablet();
			}
			else
			{
				assignedScientificStructure=new Cog();
			}
				
		}
		else
		{
			throw new RuntimeException("data[0] is not a Player");
		}
	}

	@Override
	public ActivationPoint getActivationPoint(GameState gameState, Player player)
	{
		return activationPoint;
	}

	public void readFromJSON(JSONObject json) throws Exception
	{
		activationPoint=ActivationPoint.valueOf((String) json.get("activationPoint"));
	}
}

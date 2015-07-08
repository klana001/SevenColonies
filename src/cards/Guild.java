package cards;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import classes.Card;
import classes.NamedCard;
import common.JSONReadable;
import common.Utilities;
import effects.Effect;
import game.Age;
import interfaces.ExchangableItem;

public class Guild extends NamedCard implements IGuild
{
	private String name;
	private List<ExchangableItem> cost;
	private List<Effect> effects;
	
	public Guild(Age age,String name,List<ExchangableItem> cost, List<Effect> effects)
	{
		super(age);
		this.name=name;
		this.cost=cost;
		this.effects=effects;
	}

	public Colour getColour()
	{
		return Colour.PURPLE;
	}
	
	@Override
	public Collection<IGuild> getGuilds()
	{
		return Arrays.asList(this);
	}
	
	public static Collection<Guild> constructGuilds(Age age) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException
	{
		JSONParser parser = new JSONParser();
		
		InputStream is = ClassLoader.getSystemResourceAsStream("data/guilds.json");
		
		if (is==null)
		{
			throw new FileNotFoundException("unable to find data/guilds.json");
		}

		JSONObject jsonMap=(JSONObject) parser.parse(new BufferedReader(new InputStreamReader(is)));
		ArrayList<JSONObject> guildJsonList = (ArrayList<JSONObject>) jsonMap.get(age.toString());
		Collection<Guild> guildsList = new ArrayList<Guild>();
		
		for (JSONObject guildEntryJson : guildJsonList)
		{
			JSONObject guildJson = (JSONObject) guildEntryJson.get("guild");
			final String guildName = (String) guildJson.get("name");
			final List<Effect> effectList = new ArrayList<Effect>();
			
			ArrayList<JSONObject> effectJsonList = (ArrayList<JSONObject>) guildJson.get("effect");
			for (JSONObject effectJson : effectJsonList)
			{
				JSONReadable effect = (JSONReadable) Utilities.createClass(effectJson);  
				try
				{
					effect.readFromJSON((JSONObject) effectJson.get("data"));
				} catch (Exception e)
				{
					throw new IOException(e);
				}
				effectList.add((Effect) effect);
			}
			
			ArrayList<JSONObject> costJson = (ArrayList<JSONObject>) guildJson.get("cost");
			final ArrayList<ExchangableItem> costInstanceList = new ArrayList<ExchangableItem>();
			
			if (costJson!=null)
			{
				for (JSONObject costElementJson : costJson)
				{
					costInstanceList.add((ExchangableItem) Utilities.createClass(costElementJson));
				}
			}
			
			ArrayList<JSONObject> upgradableFromJson = (ArrayList<JSONObject>) guildJson.get("upgradeableFrom");
			final ArrayList<Card> upgradableFromInstanceList = new ArrayList<Card>();
			
			if (upgradableFromJson!=null)
			{
				for (JSONObject upgradableFromElementJson : upgradableFromJson)
				{
					final String building = (String) upgradableFromElementJson.get("building");
					
					upgradableFromInstanceList.add(Utilities.cloneObject(knownNamedCards.get(building)));
				}
			}


			
			Guild newGuild = new Guild(age,guildName,costInstanceList,effectList);
			knownNamedCards.put(newGuild.getName(),newGuild);
			guildsList.add(newGuild);
		}
		
		

		return guildsList;
	}

	@Override
	public Collection<ExchangableItem> getCost()
	{
		return cost;
	}

	@Override
	public String getName()
	{
		return name;
	}

	
	@Override
	public String toString()
	{
		return getName();
	}

	public List<Effect> getEffects()
	{
		return effects;
	}
}

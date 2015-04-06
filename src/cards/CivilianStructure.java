package cards;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import common.Utilities;
import classes.Card;
import classes.NamedCard;
import game.Age;
import interfaces.ExchangableItem;
import interfaces.UpgradableFrom;

public class CivilianStructure extends NamedCard implements UpgradableFrom
{
	private int points;
	private List<ExchangableItem> cost;
	private String civilianStructureName;
	private List<Card> upgradableFrom;
	
	public CivilianStructure(Age age, int points, List<ExchangableItem> cost, String civilianStructureName, List<Card> upgradableFrom)
	{
		super(age);
		this.points = points;
		this.cost=cost;
		this.civilianStructureName=civilianStructureName;
		this.upgradableFrom=upgradableFrom;
	}

	public int getPoints()
	{
		return points;
	}

	public Colour getColour()
	{
		return Colour.BLUE;
	}
	
	public static Collection<CivilianStructure> constructCivilianStructures(Age age) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException
	{
		JSONParser parser = new JSONParser();
		
		InputStream is = ClassLoader.getSystemResourceAsStream("data/civilianStructures.json");
		
		if (is==null)
		{
			throw new FileNotFoundException("unable to find data/civilianStructures.json");
		}

		JSONObject root=(JSONObject) parser.parse(new BufferedReader(new InputStreamReader(is)));

		ArrayList<JSONObject> civilianStructureJsonList = (ArrayList<JSONObject>) root.get(age.toString());
		Collection<CivilianStructure> civilianStructuresList = new ArrayList<CivilianStructure>();
		
		for (JSONObject civilianStructureEntryJson : civilianStructureJsonList)
		{
			JSONObject civilianStructureJson = (JSONObject) civilianStructureEntryJson.get("civilianStructure");
			final String civilianStructureName = (String) civilianStructureJson.get("name");
			final List<CivilianStructure> typeList = new ArrayList<CivilianStructure>();
			
			final int points = Integer.parseInt((String) civilianStructureJson.get("points"));
			
			ArrayList<JSONObject> costJson = (ArrayList<JSONObject>) civilianStructureJson.get("cost");
			final ArrayList<ExchangableItem> costInstanceList = new ArrayList<ExchangableItem>();
			
			if (costJson!=null)
			{
				for (JSONObject costElementJson : costJson)
				{
					costInstanceList.add((ExchangableItem) Utilities.createClass(costElementJson));
				}
			}
			
			ArrayList<JSONObject> upgradableFromJson = (ArrayList<JSONObject>) civilianStructureJson.get("upgradableFrom");
			final ArrayList<Card> upgradableFromInstanceList = new ArrayList<Card>();
			
			if (upgradableFromJson!=null)
			{
				for (JSONObject upgradableFromElementJson : upgradableFromJson)
				{
					upgradableFromInstanceList.add((Card) Utilities.createClass(upgradableFromElementJson));
				}
			}
			
			CivilianStructure newCivilianStructure = new CivilianStructure(age,points,costInstanceList,civilianStructureName,upgradableFromInstanceList);
			civilianStructuresList.add(newCivilianStructure);
		}
		
		

		return civilianStructuresList;
	}


	@Override
	public Collection<ExchangableItem> getCost()
	{
		return cost;
	}


	@Override
	public String getName()
	{
		return civilianStructureName;
	}


	@Override
	public Collection<Card> upgradableFrom()
	{
		return upgradableFrom;
	}
	
	@Override
	public String toString()
	{
		return getName();
	}
}

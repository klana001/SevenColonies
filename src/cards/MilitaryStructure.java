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

import classes.Card;
import classes.NamedCard;
import common.Military;
import common.Utilities;
import effects.Effect;
import game.Age;
import interfaces.ExchangableItem;

public class MilitaryStructure extends NamedCard implements Military
{
	private int strength;
	private List<ExchangableItem> cost;
	private String name;


	public MilitaryStructure(Age age,int strength,	List<ExchangableItem> cost, String name)
	{
		super(age);
		this.strength = strength;
		this.cost=cost;
		this.name=name;
	}

	public int calculateMilitary()
	{
		return strength;
	}
	
	public Colour getColour()
	{
		return Colour.RED;
	}
	
	public static Collection<MilitaryStructure> constructMilitaryStructures(Age age) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException
	{
		JSONParser parser = new JSONParser();
		
		InputStream is = ClassLoader.getSystemResourceAsStream("data/militaryStructures.json");
		
		if (is==null)
		{
			throw new FileNotFoundException("unable to find data/militaryStructures.json");
		}

		JSONObject jsonMap=(JSONObject) parser.parse(new BufferedReader(new InputStreamReader(is)));
		ArrayList<JSONObject> militaryStructureJsonList = (ArrayList<JSONObject>) jsonMap.get(age.toString());
		Collection<MilitaryStructure> militaryStructuresList = new ArrayList<MilitaryStructure>();
		
		for (JSONObject militaryStructureEntryJson : militaryStructureJsonList)
		{
			JSONObject militaryStructureJson = (JSONObject) militaryStructureEntryJson.get("militaryStructure");
			final String militaryStructureName = (String) militaryStructureJson.get("name");
			final List<MilitaryStructure> typeList = new ArrayList<MilitaryStructure>();
			
			final int strength = Integer.parseInt((String) militaryStructureJson.get("strength"));
			
			ArrayList<JSONObject> costJson = (ArrayList<JSONObject>) militaryStructureJson.get("cost");
			final ArrayList<ExchangableItem> costInstanceList = new ArrayList<ExchangableItem>();
			
			if (costJson!=null)
			{
				for (JSONObject costElementJson : costJson)
				{
					costInstanceList.add((ExchangableItem) Utilities.createClass(costElementJson));
				}
			}
			
			ArrayList<JSONObject> upgradableFromJson = (ArrayList<JSONObject>) militaryStructureJson.get("upgradeableFrom");
			final ArrayList<Card> upgradableFromInstanceList = new ArrayList<Card>();
			
			if (upgradableFromJson!=null)
			{
				for (JSONObject upgradableFromElementJson : upgradableFromJson)
				{
					final String building = (String) upgradableFromElementJson.get("building");
					
					upgradableFromInstanceList.add(Utilities.cloneObject(knownNamedCards.get(building)));
				}
			}
			
			MilitaryStructure newMilitaryStructure = new MilitaryStructure(age,strength,costInstanceList,militaryStructureName);
			militaryStructuresList.add(newMilitaryStructure);
			knownNamedCards.put(newMilitaryStructure.getName(),newMilitaryStructure);
		}
		
		

		return militaryStructuresList;
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
}

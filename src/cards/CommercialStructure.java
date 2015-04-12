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
import common.Utilities;
import effects.Effect;
import game.Age;
import interfaces.ExchangableItem;


public class CommercialStructure extends NamedCard
{
	private List<ExchangableItem> cost;
	private String name;
	private List<Effect> effect;

	public CommercialStructure(Age age,	List<ExchangableItem> cost, String name, List<Effect> effect)
	{
		super(age);
		this.cost=cost;
		this.name=name;
		this.effect=effect;
	}

	public Colour getColour()
	{
		return Colour.YELLOW;
	}
	
	public Collection<Effect> getEffect()
	{
		return effect;
	}
	
	
	public static Collection<CommercialStructure> constructCommercialStructures(Age age) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException
	{
		JSONParser parser = new JSONParser();
		
		InputStream is = ClassLoader.getSystemResourceAsStream("data/commercialStructures.json");
		
		if (is==null)
		{
			throw new FileNotFoundException("unable to find data/commercialStructures.json");
		}

		JSONObject jsonMap=(JSONObject) parser.parse(new BufferedReader(new InputStreamReader(is)));
		ArrayList<JSONObject> commercialStructureJsonList = (ArrayList<JSONObject>) jsonMap.get(age.toString());
		Collection<CommercialStructure> commercialStructuresList = new ArrayList<CommercialStructure>();
		
		for (JSONObject commercialStructureEntryJson : commercialStructureJsonList)
		{
			JSONObject commercialStructureJson = (JSONObject) commercialStructureEntryJson.get("commercialStructure");
			final String commercialStructureName = (String) commercialStructureJson.get("name");
			final List<Effect> effectList = new ArrayList<Effect>();
			
			ArrayList<JSONObject> effectJsonList = (ArrayList<JSONObject>) commercialStructureJson.get("effect");
			for (JSONObject effectJson : effectJsonList)
			{
				effectList.add((Effect) Utilities.createClass(effectJson));
			}
			
			ArrayList<JSONObject> costJson = (ArrayList<JSONObject>) commercialStructureJson.get("cost");
			final ArrayList<ExchangableItem> costInstanceList = new ArrayList<ExchangableItem>();
			
			if (costJson!=null)
			{
				for (JSONObject costElementJson : costJson)
				{
					costInstanceList.add((ExchangableItem) Utilities.createClass(costElementJson));
				}
			}
			
			ArrayList<JSONObject> upgradableFromJson = (ArrayList<JSONObject>) commercialStructureJson.get("upgradeableFrom");
			final ArrayList<Card> upgradableFromInstanceList = new ArrayList<Card>();
			
			if (upgradableFromJson!=null)
			{
				for (JSONObject upgradableFromElementJson : upgradableFromJson)
				{
					final String building = (String) upgradableFromElementJson.get("building");
					
					upgradableFromInstanceList.add(Utilities.cloneObject(knownNamedCards.get(building)));
				}
			}
			
			CommercialStructure newCommercialStructure = new CommercialStructure(age,costInstanceList,commercialStructureName,effectList);
			commercialStructuresList.add(newCommercialStructure);
			knownNamedCards.put(newCommercialStructure.getName(),newCommercialStructure);
		}
		
		

		return commercialStructuresList;
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

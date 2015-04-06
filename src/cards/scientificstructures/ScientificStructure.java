package cards.scientificstructures;

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
import common.Utilities;
import cards.IScientificStructure;
import game.Age;
import interfaces.ExchangableItem;

public class ScientificStructure extends NamedCard implements IScientificStructure
{
	private List<Type> type;
	private String name;
	private List<ExchangableItem> cost;

	public ScientificStructure(Age age,List<Type> type,String name,List<ExchangableItem> cost)
	{
		super(age);
		this.type=type;
		this.name=name;
		this.cost=cost;
	}

	public Colour getColour()
	{
		return Colour.GREEN;
	}
	
	@Override
	public Collection<IScientificStructure> getScientificStructures()
	{
		return Arrays.asList(this);
	}
	
	public static Collection<ScientificStructure> constructScientificStructures(Age age) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException
	{
		JSONParser parser = new JSONParser();
		
		InputStream is = ClassLoader.getSystemResourceAsStream("data/scientificStructures.json");
		
		if (is==null)
		{
			throw new FileNotFoundException("unable to find data/scientificStructures.json");
		}

		JSONObject jsonMap=(JSONObject) parser.parse(new BufferedReader(new InputStreamReader(is)));
		ArrayList<JSONObject> scientificStructureJsonList = (ArrayList<JSONObject>) jsonMap.get(age.toString());
		Collection<ScientificStructure> scientificStructuresList = new ArrayList<ScientificStructure>();
		
		for (JSONObject scientificStructureEntryJson : scientificStructureJsonList)
		{
			JSONObject scientificStructureJson = (JSONObject) scientificStructureEntryJson.get("scientificStructure");
			final String scientificStructureName = (String) scientificStructureJson.get("name");
			final List<IScientificStructure> typeList = new ArrayList<IScientificStructure>();
			
			ArrayList<JSONObject> typeJsonList = (ArrayList<JSONObject>) scientificStructureJson.get("type");
			for (JSONObject typeJson : typeJsonList)
			{
				typeList.add((IScientificStructure) Utilities.createClass(typeJson));
			}
			
			ArrayList<JSONObject> costJson = (ArrayList<JSONObject>) scientificStructureJson.get("cost");
			final ArrayList<ExchangableItem> costInstanceList = new ArrayList<ExchangableItem>();
			
			if (costJson!=null)
			{
				for (JSONObject costElementJson : costJson)
				{
					costInstanceList.add((ExchangableItem) Utilities.createClass(costElementJson));
				}
			}
			
			Collection<Type> returnTypes = new HashSet<ScientificStructure.Type>();
			for (IScientificStructure scientificStructure : typeList)
			{
				returnTypes.addAll(scientificStructure.getScientificStructureType());
			}

			
			ScientificStructure newScientificStructure = new ScientificStructure(age,new ArrayList<Type>(returnTypes),scientificStructureName,costInstanceList);

			scientificStructuresList.add(newScientificStructure);
		}
		
		

		return scientificStructuresList;
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
	public Collection<Type> getScientificStructureType()
	{
		return type;
	}
	
	@Override
	public String toString()
	{
		return getName();
	}
}

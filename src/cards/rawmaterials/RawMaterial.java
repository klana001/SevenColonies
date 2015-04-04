package cards.rawmaterials;

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
import cards.IRawMaterial;
import common.Utilities;
import game.Age;
import interfaces.ExchangableItem;


public class RawMaterial extends Card implements ExchangableItem,IRawMaterial
{
	private List<Type> type;
	private List<ExchangableItem> cost;
	private String name;

	public RawMaterial(Age age, List<Type> type, List<ExchangableItem> cost,String name)
	{
		super(age);
		this.type=type;
		this.cost=cost;
		this.name=name;
	}
	
	public RawMaterial(RawMaterial source)
	{
		super(source.getAge());
		type = new ArrayList<Type>(source.type);
		cost = Utilities.cloneList(source.cost);
		name = new String(source.name);
	}


	public Colour getColour()
	{
		return Colour.BROWN;
	}
	
	@Override
	public Collection<IRawMaterial> getRawMaterials()
	{
		return Arrays.asList(this);
	}
	
	public static Collection<RawMaterial> constructRawMaterials(Age age) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException
	{
		JSONParser parser = new JSONParser();
		
		InputStream is = ClassLoader.getSystemResourceAsStream("data/rawMaterials.json");
		
		if (is==null)
		{
			throw new FileNotFoundException("unable to find data/rawMaterials.json");
		}

		JSONObject jsonMap=(JSONObject) parser.parse(new BufferedReader(new InputStreamReader(is)));
		ArrayList<JSONObject> rawMaterialJsonList = (ArrayList<JSONObject>) jsonMap.get(age.toString());
		Collection<RawMaterial> rawMaterialsList = new ArrayList<RawMaterial>();
		
		for (JSONObject rawMaterialEntryJson : rawMaterialJsonList)
		{
			JSONObject rawMaterialJson = (JSONObject) rawMaterialEntryJson.get("rawMaterial");
			final String rawMaterialName = (String) rawMaterialJson.get("name");
			final List<IRawMaterial> typeList = new ArrayList<IRawMaterial>();
			
			ArrayList<JSONObject> typeJsonList = (ArrayList<JSONObject>) rawMaterialJson.get("type");
			for (JSONObject typeJson : typeJsonList)
			{
				typeList.add((IRawMaterial) Utilities.createClass(typeJson));
			}
			
			ArrayList<JSONObject> costJson = (ArrayList<JSONObject>) rawMaterialJson.get("cost");
			final ArrayList<ExchangableItem> costInstanceList = new ArrayList<ExchangableItem>();
			
			if (costJson!=null)
			{
				for (JSONObject costElementJson : costJson)
				{
					costInstanceList.add((ExchangableItem) Utilities.createClass(costElementJson));
				}
			}
			
			Collection<Type> returnTypes = new HashSet<RawMaterial.Type>();
			for (IRawMaterial rawMaterial : typeList)
			{
				returnTypes.addAll(rawMaterial.getRawMaterialType());
			}
			
			RawMaterial newRawMaterial = new RawMaterial(age,new ArrayList<Type>(returnTypes),costInstanceList,rawMaterialName);
			rawMaterialsList.add(newRawMaterial);
		}
		
		

		return rawMaterialsList;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public Collection<ExchangableItem> getCost()
	{
		return cost;
	}

	@Override
	public Collection<Type> getRawMaterialType()
	{
		return type;
	}
	
	@Override
	public String toString()
	{
		return getName();
	}
}

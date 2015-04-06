package cards.manufacturedgoods;

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

import player.Player;
import classes.Card;
import classes.NamedCard;
import common.Utilities;
import cards.IManufacturedGood;
import cards.IRawMaterial;
import cards.rawmaterials.RawMaterial;
import game.Age;
import interfaces.ExchangableItem;


public class ManufacturedGood extends NamedCard implements ExchangableItem,IManufacturedGood
{
	private List<Type> type;
	private List<ExchangableItem> cost;
	private String name;

	public ManufacturedGood(Age age, List<Type> type, List<ExchangableItem> cost,String name)
	{
		super(age);
		this.type=type;
		this.cost=cost;
		this.name=name;
	}

	public Colour getColour()
	{
		return Colour.GREY;
	}
	
	@Override
	public Collection<IManufacturedGood> getManufacturedGoods()
	{
		return Arrays.asList(this);
	}
	
	public static Collection<ManufacturedGood> constructManufacturedGoods(Age age) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException
	{
		JSONParser parser = new JSONParser();
		
		InputStream is = ClassLoader.getSystemResourceAsStream("data/manufacturedGoods.json");
		
		if (is==null)
		{
			throw new FileNotFoundException("unable to find data/manufacturedGoods.json");
		}

		JSONObject jsonMap=(JSONObject) parser.parse(new BufferedReader(new InputStreamReader(is)));
		ArrayList<JSONObject> manufacturedGoodJsonList = (ArrayList<JSONObject>) jsonMap.get(age.toString());
		Collection<ManufacturedGood> manufacturedGoodsList = new ArrayList<ManufacturedGood>();
		
		if (manufacturedGoodJsonList==null)
		{
			throw new RuntimeException("No "+age.toString()+" entry in data/manufacturedGoods.json" );
		}
		
		for (JSONObject manufacturedGoodEntryJson : manufacturedGoodJsonList)
		{
			JSONObject manufacturedGoodJson = (JSONObject) manufacturedGoodEntryJson.get("manufacturedGood");
			final String manufacturedGoodName = (String) manufacturedGoodJson.get("name");
			final List<IManufacturedGood> typeList = new ArrayList<IManufacturedGood>();
			
			ArrayList<JSONObject> typeJsonList = (ArrayList<JSONObject>) manufacturedGoodJson.get("type");
			for (JSONObject typeJson : typeJsonList)
			{
				typeList.add((IManufacturedGood) Utilities.createClass(typeJson));
			}
			
			ArrayList<JSONObject> costJson = (ArrayList<JSONObject>) manufacturedGoodJson.get("cost");
			final ArrayList<ExchangableItem> costInstanceList = new ArrayList<ExchangableItem>();
			
			if (costJson!=null)
			{
				for (JSONObject costElementJson : costJson)
				{
					costInstanceList.add((ExchangableItem) Utilities.createClass(costElementJson));
				}
			}
			
			Collection<Type> returnTypes = new HashSet<ManufacturedGood.Type>();
			for (IManufacturedGood manufacturedGood : typeList)
			{
				returnTypes.addAll(manufacturedGood.getManufacturedGoodType());
			}
			
			ManufacturedGood newManufacturedGood = new ManufacturedGood(age,new ArrayList<Type>(returnTypes),costInstanceList,manufacturedGoodName);
			manufacturedGoodsList.add(newManufacturedGood);
		}
		
		

		return manufacturedGoodsList;
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
	public Collection<Type> getManufacturedGoodType()
	{
		return type;
	}
	
	@Override
	public String toString()
	{
		return getName();
	}

	@Override
	public boolean equivilent(ExchangableItem otherItem, Player currentPlayer)
	{
		if (otherItem instanceof IManufacturedGood)
		{
			IManufacturedGood otherManufacturedGood = (IManufacturedGood) otherItem;
			for (IManufacturedGood.Type type : getManufacturedGoodType())
			{
				if (otherManufacturedGood.getManufacturedGoodType().contains(type)) return true;
			}
		}
		return false;
	}
}

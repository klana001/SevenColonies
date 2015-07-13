package wonders;

import game.GameState;
import interfaces.ExchangableItem;
import interfaces.GameElement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import player.Player;
import common.Military;
import common.MutableInteger;
import common.Scoreable;
import common.Utilities;
import effects.Effect;
import wonders.wonderstage.MilitaryWonderStage;
import wonders.wonderstage.PointWonderStage;
import wonders.wonderstage.WonderStage;

/**
 * A Wonder that is owned by a player
 * @author default
 *
 */
public class Wonder implements Scoreable,Military,GameElement,Effect
{
	
	
	final private List<WonderStage> wonderStages;
	final private Map<Integer,Collection<WonderStage>> wonderStagesPerOrderIndex;
	final private String name;
//	final private List<WonderStage> wonderStagesTemplate;
	final private List<GameElement> wonderBenefit;
	
	public Wonder(String wonderName, Map<Integer,Collection<WonderStage>> wonderStagesPerOrderIndex, List<GameElement> wonderBenefit)
	{
		super();
//		wonderStagesTemplate = getWonderStagesTemplate();
		this.name=wonderName;
		this.wonderStagesPerOrderIndex=wonderStagesPerOrderIndex;
		this.wonderBenefit=wonderBenefit;
		
		wonderStages=new ArrayList<WonderStage>();
		wonderStagesPerOrderIndex.keySet().stream().forEach(k->wonderStages.addAll(wonderStagesPerOrderIndex.get(k)));
	}
	
	/**
	 * returns the collection of all known wonders 
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws ParseException
	 */
	@SuppressWarnings("unchecked")
	public static Collection<Wonder> constructWonders() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException
	{
		JSONParser parser = new JSONParser();
		
		InputStream is = ClassLoader.getSystemResourceAsStream("data/wonders.json");
		
		if (is==null)
		{
			throw new FileNotFoundException("unable to find data/wonders.json");
		}

		JSONObject jsonMap=(JSONObject) parser.parse(new BufferedReader(new InputStreamReader(is)));
		ArrayList<JSONObject> wonderJsonList = (ArrayList<JSONObject>) jsonMap.get("wonders");
		Collection<Wonder> wonderList = new ArrayList<Wonder>();
		
		for (JSONObject wonderEntryJson : wonderJsonList)
		{
			JSONObject wonderJson = (JSONObject) wonderEntryJson.get("wonder");
			final String wonderName = (String) wonderJson.get("name");
			final List<GameElement> wonderBenefit = new ArrayList<GameElement>();
			final Map<Integer,Collection<WonderStage>> wonderStages = new HashMap<Integer, Collection<WonderStage>>();
			
			ArrayList<JSONObject> benefitJsonList = (ArrayList<JSONObject>) wonderJson.get("benefit");
			for (JSONObject benefitJson : benefitJsonList)
			{
				wonderBenefit.add((GameElement) Utilities.createClass(benefitJson));
			}
			
			ArrayList<JSONObject> wonderStageJsonList = (ArrayList<JSONObject>) wonderJson.get("wonderStages");
			for (JSONObject wonderStageJson : wonderStageJsonList)
			{
				WonderStage wonderStage = createWonderStageClass(wonderStageJson);
				Collection<WonderStage> wonderStagesForBuildIndex = wonderStages.get(wonderStage.getBuildOrderIndex());
				if (wonderStagesForBuildIndex == null)
				{
					wonderStagesForBuildIndex= new ArrayList<WonderStage>();
					wonderStages.put(wonderStage.getBuildOrderIndex(), wonderStagesForBuildIndex);
				}
				wonderStagesForBuildIndex.add(wonderStage);
			}
			
			Wonder newWonder = new Wonder(wonderName,wonderStages,wonderBenefit);
			wonderList.add(newWonder);
		}
		
		

		return wonderList;
	}
	
	
	
	/**
	 * creates a WonderStage instance that has an associated cost from the given JSON object 
	 * @param classJson
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static WonderStage createWonderStageClass(JSONObject classJson) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException
	{
		WonderStage instance;
	
		String className = (String) classJson.get("class");
		ArrayList constructorArguments = (ArrayList) classJson.get("constructor");
		ArrayList<Object> constructorArgumentInstances = new ArrayList<Object>();
		int constructorArgumentsCount=1;
		
		ArrayList<JSONObject> costJson = (ArrayList<JSONObject>) classJson.get("cost");
		ArrayList<ExchangableItem> costInstanceList = new ArrayList<ExchangableItem>();
		for (JSONObject costElementJson : costJson)
		{
			costInstanceList.add((ExchangableItem) Utilities.createClass(costElementJson));
		}
		
		int orderIndex = Integer.parseInt((String) classJson.get("order"));
		
		constructorArgumentInstances.add(costInstanceList);
		
		// do we have a constructor json element?
		if (constructorArguments != null)
		{
			// we always have cost as the first argument
			constructorArgumentsCount=constructorArguments.size()+1;
			
			for (Object constructorArgument : constructorArguments)
			{
				// containing an array...
				if (constructorArgument instanceof List)
				{
					constructorArgumentInstances.add(Utilities.createList((List) constructorArgument));
				}
				// containing a class
				else
				{
					constructorArgumentInstances.add(Utilities.createClass((JSONObject) constructorArgument));
				}
			}
		}

		
		// add the build order index to list of constructor elements
		constructorArgumentsCount++;
		constructorArgumentInstances.add(orderIndex);

		
		final int constructorArgumentsCountFinal = constructorArgumentsCount;
		
		
		Class<GameElement> clazz = (Class<GameElement>) ClassLoader.getSystemClassLoader().loadClass(className);
		
		// find the constructor for the number of arguments
		Constructor constructor = Arrays.asList(clazz.getConstructors()).stream().filter(c -> c.getParameterTypes().length==constructorArgumentsCountFinal && !c.getParameterTypes()[0].getName().equals(className)).findFirst().orElse(null);

		// no constructor found... use default constructor
		if (constructor==null)
		{
			instance = (WonderStage) clazz.newInstance();
		}
		else
		{
			instance =  (WonderStage) constructor.newInstance(constructorArgumentInstances.toArray());
		}
		return instance;
	}



	public List<WonderStage> getWonderStages()
	{
		return wonderStages;
	}

	public List<GameElement> getWonderBenefit()
	{
		return wonderBenefit;
	}

	public void buildStage(WonderStage wonderStage,GameState gameState, Player player)
	{
//		WonderStage wonderStage = getWonderStages().stream().findFirst().orElseThrow(RuntimeException::new);
		
		if (wonderStage.isBuilt())
			throw new RuntimeException("Wonder Stage : "+wonderStage+" alread has been built.");
		wonderStage.setBuilt(true);
		if (wonderStage instanceof Effect)
		{
			Effect effectWonderStage = (Effect) wonderStage;
			if (effectWonderStage.getActivationPoint(gameState, player)==ActivationPoint.INSTANTLY)
			{
				effectWonderStage.performEffect(gameState, player);
			}
		}
	}
	
	@Override
	public int calculateScore()
	{
		final MutableInteger score = new MutableInteger();
		Utilities.filterElements(wonderStages, PointWonderStage.class).forEach(stage-> score.val+=stage.isBuilt()?stage.calculateScore():0);
		return score.val;
	}


	@Override
	public int calculateMilitary()
	{
		final MutableInteger strength = new MutableInteger();
		Utilities.filterElements(wonderStages, MilitaryWonderStage.class).forEach(stage-> strength.val+=stage.isBuilt()?stage.calculateMilitary():0);
		return strength.val;
	}
	
//	protected List<WonderStage> getWonderStagesTemplate()
//	{
//		return wonderStagesTemplate;
//	}

	@Override
	public void performEffect(GameState gameState, Object... data)
	{
		wonderStages.stream().filter(stage-> stage.isBuilt()).forEach(stage->
		{
			if (stage instanceof Effect)
			{
				Effect effectWonderStage = (Effect) stage;
				effectWonderStage.performEffect(null, null);
			}
		});
	}
	
	@Override
	public ActivationPoint getActivationPoint(GameState gameState, Player player){
		throw new RuntimeException("Should not be called");
	}

	/**
	 * Returns the next wonder stage available to build... can be more than one option.
	 * @return
	 */
	public Collection<WonderStage> getNextWonderStage()
	{
		int minOrderIndex=Integer.MAX_VALUE;
		for (WonderStage wonderStage : wonderStages)
		{
			if (!wonderStage.isBuilt())
			{
				if (wonderStage.getBuildOrderIndex()<minOrderIndex)
				{
					minOrderIndex=wonderStage.getBuildOrderIndex();
				}
			}
		}
		Collection<WonderStage> result;
		if (minOrderIndex==Integer.MAX_VALUE)
		{
			result = new ArrayList<WonderStage>(0);
		}
		else
		{
			result = wonderStagesPerOrderIndex.get(minOrderIndex);
		}
		return result;
	}
	
	/**
	 * Returns the next wonder stages available at the given orderIndex.
	 * @return
	 */
	public Collection<WonderStage> getWonderStage(int orderIndex)
	{
		return wonderStagesPerOrderIndex.get(orderIndex);
	}

	@Override
	public String getName()
	{
		return name;
	}
	
	@Override
	public String toString()
	{
		return name;
	}
}

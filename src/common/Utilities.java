package common;



import interfaces.GameElement;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.simple.JSONObject;

import com.rits.cloning.Cloner;


public class Utilities
{
	/**
	 * filters the provided collection to those that match the provided template type 
	 * @param cards
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static<T> Collection<T> filterElements(@SuppressWarnings("rawtypes") Collection collection, Class<T> filterType)
	{
		return (Collection<T>) collection.stream().filter(element -> filterType.isInstance(element))
				.map(element -> filterType.cast(element))
				.collect(Collectors.toList());
	}
	
	public static<T,U> Stream<T> filterElements(Stream<U> stream, Class<T> filterType)
	{
		return stream.filter(element -> filterType.isInstance(element))
				.map(element -> filterType.cast(element));
	}
	
	/**
	 * creates an instance of a class represented by the provided json object
	 * @param classJson
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object createClass(JSONObject classJson) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException
	{
		Object instance;
	
		String className = (String) classJson.get("class");
		Object constructorJson = classJson.get("constructor");
		ArrayList constructorArguments = null;
		int constructorArgumentsCount=0;
		ArrayList<Object> constructorArgumentInstances = new ArrayList<Object>();
		
		// do we have a constructor json element?
		if (constructorJson != null)
		{
			// and it is an array...
			if (constructorJson instanceof ArrayList)
			{
				constructorArguments = (ArrayList) constructorJson;
				constructorArgumentsCount=constructorArguments.size();
				
				for (Object constructorArgument : constructorArguments)
				{
					// containing an array...
					if (constructorArgument instanceof List)
					{
						constructorArgumentInstances.add(createList((List) constructorArgument));
					}
					// containing a class
					else
					{
						constructorArgumentInstances.add(createClass((JSONObject) constructorArgument));
					}
				}
				
			}
			// and it is a String so must be a primitive...
			else if (constructorJson instanceof String)
			{
				String value = (String) constructorJson;
				switch (className)
				{
					case "int":
					{
						return Integer.parseInt(value);
					}
					case "double":
					{
						return Double.parseDouble(value);
					}
				}
				return value;
			}
			// major error
			else
			{
				throw new RuntimeException("Unknown constructor type: "+constructorJson);
			}
		}
		
		Class<GameElement> clazz = (Class<GameElement>) ClassLoader.getSystemClassLoader().loadClass(className);
		final int constructorArgumentsCountFinal = constructorArgumentsCount;

		// find the constructor for the number of arguments
		Constructor constructor = Arrays.asList(clazz.getConstructors()).stream().filter(c -> c.getParameterTypes().length==constructorArgumentsCountFinal && (c.getParameterTypes().length==0 || !c.getParameterTypes()[0].getName().equals(className))).findFirst().orElse(null);

		// no constructor found... use default constructor
		if (constructor==null)
		{
			instance = clazz.newInstance();
		}
		else
		{
			instance = constructor.newInstance(constructorArgumentInstances.toArray());
		}
		return instance;
	}
	
	/**
	 * creates a List that represents the given JSON array
	 * @param listJson
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List createList(List listJson) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException
	{
		ArrayList list = new ArrayList();
		for (Object listElement : listJson)
		{
			if (listElement instanceof List)
			{
				list.add(createList((List) listElement));
			}
			else
			{
				list.add(createClass((JSONObject) listElement));
			}
		}
		return list;
	}
	
	public static <T> T cloneObject(T object)
	{
		Cloner cloner = new Cloner();
		return cloner.deepClone(object);
	}

}

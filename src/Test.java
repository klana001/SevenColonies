import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;


public class Test
{

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		// TODO Auto-generated method stub
		Abstract abstract1=Abstract.create();
		
//		Abstract abstract2 = (Abstract) abstract1.getClass().getConstructor(abstract1.getClass()).newInstance(abstract1);
		
		Arrays.asList(abstract1.getClass().getConstructors()).forEach(c->System.out.println(c));
	}

}

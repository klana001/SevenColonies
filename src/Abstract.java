
abstract public class Abstract
{
	static int count =0;
	int integer;
	abstract String getString();
	
	Abstract(int val)
	{
		super();
		this.integer=val;
	}
	
	Abstract(Abstract source)
	{
		super();
		integer = source.integer;
	}
	
	public static Abstract create()
	{
		final String str = "TEST "+ System.currentTimeMillis();
		count++;
		return new Abstract(count)
		{
			
			@Override
			String getString()
			{
				return str;
			}
		};
	}
}
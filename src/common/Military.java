package common;

public interface Military
{
	public int calculateMilitary();
	
	public static int getStrength(Military military)
	{
		return military.calculateMilitary();
	}
}

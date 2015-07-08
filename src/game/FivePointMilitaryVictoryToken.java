package game;

public class FivePointMilitaryVictoryToken implements MilitaryToken
{

	@Override
	public String getName()
	{
		return "Five Point Military Victory Token";
	}

	@Override
	public int getScoreModificationAmount()
	{
		return 5;
	}

}

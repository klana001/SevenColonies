package game;

public class OnePointMilitaryVictoryToken implements MilitaryToken
{

	@Override
	public String getName()
	{
		return "One Point Military Victory Token";
	}

	@Override
	public int getScoreModificationAmount()
	{
		return 1;
	}

}

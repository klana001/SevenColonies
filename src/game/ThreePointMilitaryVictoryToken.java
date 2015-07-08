package game;

public class ThreePointMilitaryVictoryToken implements MilitaryToken
{

	@Override
	public String getName()
	{
		return "Three Point Military Victory Token";
	}

	@Override
	public int getScoreModificationAmount()
	{
		return 3;
	}

}

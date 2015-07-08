package game;

public class DefeatToken implements MilitaryToken
{

	@Override
	public String getName()
	{
		return "Defeat Token";
	}

	@Override
	public int getScoreModificationAmount()
	{
		return -1;
	}

}

package classes;

import java.util.ArrayList;

import common.Utilities;


public class Hand extends ArrayList<Card>
{
	public Hand()
	{
		super();
	}

	public Hand(Hand source)
	{
		super(source.size());
		addAll(Utilities.cloneList(source));
	}
	
}

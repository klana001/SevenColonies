package classes;

import interfaces.GameElement;


	abstract public class Base implements Comparable, GameElement
	{
		public Base()
		{
		}
		
		@Override
		public boolean equals(Object o)
		{
			if (o instanceof Comparable )
			{
				return ((Comparable) o).compareTo(this)>=0;
			}
			return false;
		}
		
		public int compareTo(Object o)
		{
			return 0;
		}
	}

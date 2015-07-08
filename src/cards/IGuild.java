package cards;

import java.util.Collection;
import java.util.List;

import effects.Effect;

public interface IGuild
{

	public Collection<IGuild> getGuilds(); 
	
	public List<Effect> getEffects();
	
}

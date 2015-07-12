package effects;

import java.util.Collection;

/**
 * indicates that this gameElement generates effects
 * @author default
 *
 */
public interface Effecting
{
	public Collection<Effect> getEffect();
}

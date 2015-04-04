package effects.commercial;



import cards.rawmaterials.RawMaterial;
import effects.CoinsPerType;
import game.GameState;

public class LeftRightOwnCoinPerBrown extends CoinsPerType
{
	public LeftRightOwnCoinPerBrown(LeftRightOwnCoinPerBrown source)
	{
		super(1, RawMaterial.class);
		// default copy constructor
	}
	public LeftRightOwnCoinPerBrown()
	{
		super(1, RawMaterial.class);
	}
	
	@Override
	public void performEffect(GameState gameState, Object... data)
	{
		super.performEffect(gameState, data);
		
		
		// have to go though left neighbour of the given type and then add 1 per each type instance found.
		// have to go though right neighbour of the given type and then add 1 per each type instance found.
		
	}
	
}

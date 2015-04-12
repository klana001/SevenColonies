import game.Age;
import game.Game;

import java.util.Collection;

import wonders.Wonder;
import cards.CivilianStructure;
import cards.CommercialStructure;
import cards.MilitaryStructure;
import cards.manufacturedgoods.ManufacturedGood;
import cards.rawmaterials.RawMaterial;
import cards.scientificstructures.ScientificStructure;
import classes.Hand;


public class Main
{

	public static void main(String[] args) throws Exception
	{
		final Hand hand = new Hand();
		
//		// wonders
//		Collection<Wonder> wonders = Wonder.constructWonders();
//		wonders.stream().forEach(w -> System.out.println(w.getName()));
//		
//		
//		// cards
//		CivilianStructure.constructCivilianStructures(Age.AGE_1).forEach(w -> hand.add(w));
//		CivilianStructure.constructCivilianStructures(Age.AGE_2).forEach(w -> hand.add(w));
//		CivilianStructure.constructCivilianStructures(Age.AGE_3).forEach(w -> hand.add(w));
//
//		RawMaterial.constructRawMaterials(Age.AGE_1).forEach(w -> hand.add(w));
//		RawMaterial.constructRawMaterials(Age.AGE_2).forEach(w -> hand.add(w));
//
//		
//		ManufacturedGood.constructManufacturedGoods(Age.AGE_1).forEach(w -> hand.add(w));
//		ManufacturedGood.constructManufacturedGoods(Age.AGE_2).forEach(w -> hand.add(w));
//
//		
//		ScientificStructure.constructScientificStructures(Age.AGE_1).forEach(w -> hand.add(w));
//		ScientificStructure.constructScientificStructures(Age.AGE_2).forEach(w -> hand.add(w));
//		ScientificStructure.constructScientificStructures(Age.AGE_3).forEach(w -> hand.add(w));
//		
//		MilitaryStructure.constructMilitaryStructures(Age.AGE_1).forEach(w -> hand.add(w));
//		MilitaryStructure.constructMilitaryStructures(Age.AGE_2).forEach(w -> hand.add(w));
//		MilitaryStructure.constructMilitaryStructures(Age.AGE_3).forEach(w -> hand.add(w));
//		
//		CommercialStructure.constructCommercialStructures(Age.AGE_1).forEach(w -> hand.add(w));
//		CommercialStructure.constructCommercialStructures(Age.AGE_2).forEach(w -> hand.add(w));
//		CommercialStructure.constructCommercialStructures(Age.AGE_3).forEach(w -> hand.add(w));
//		
//		hand.stream().forEach(w -> System.out.println(w.getName()));
		
		
		Game game = new Game();

	}

}

package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import common.Utilities;
import classes.Card;
import classes.Round;
import effects.Effect;
import effects.Effect.ActivationPoint;
import effects.rawmaterials.WildRawMaterial;
import player.Player;
import wonders.Wonder;
import game.GameState.Stage;
import interfaces.GameElement;

public class GameState
{

	
	private List<Player> players = new ArrayList<Player>();
	private Map<GameElement, Player> gameElementOwnership = new HashMap<GameElement, Player>();
	private List<Wonder> wonders;
	private Age age;
	private Round round;
	private List<Card> discards = new ArrayList<Card>();
//	private Stage stage;
	private Stage stage;
	
	public GameState()
	{
		super();
		// default constructor
	}

	
	public enum Stage
	{
		SETUP,
		AGE1_INPROGRESS,
		AGE1_LAST_TURN,
		AGE1_MILITARY,
		AGE2_INPROGRESS,
		AGE2_LAST_TURN,
		AGE2_MILITARY,
		AGE3_INPROGRESS,
		AGE3_LAST_TURN,
		AGE3_MILITARY,

		END, MILITARY
	}
	
	// at end of age...
//	Utilities.filterElements(wonderStages, MilitaryWonderStage.class).stream().filter(stage-> stage.isBuilt()).forEach(stage->
//	{
//		if (!stage.hasBeenUsed() && stage instanceof Effect)
//		{
//			Effect effectWonderStage = (Effect) stage;
//			if ((effectWonderStage.getActivationPoint()==ActivationPoint.AT_END_OF_AGE && (Game.stage==Stage.AGE1_LAST_TURN || Game.stage==Stage.AGE2_LAST_TURN || Game.stage==Stage.AGE3_LAST_TURN)) ||
//				(effectWonderStage.getActivationPoint()==ActivationPoint.AT_END_OF_GAME && Game.stage==Stage.END))
//			{
//				effectWonderStage.performEffect();
//			}
//		}
//	});

	public void addExtraTurn(GameElement gameElement)
	{
		// get the owner of the gameElement
		// give the owner one more turn
	}

	public void setWonders(ArrayList<Wonder> wonders)
	{
		this.wonders = wonders;
	}

	public List<Wonder> getWonders()
	{
		return wonders;
	}

	public void setPlayers(List<Player> players)
	{
		this.players=players;
		
	}
	
	public List<Player> getPlayers()
	{
		return players;
	}

	public void setAge(Age age)
	{
		this.age = age;
		
	}

	public Age getAge()
	{
		return age;
	}

	public void setRound(Round round)
	{
		this.round = round;
		
	}

	public Round getRound()
	{
		return round;
	}

	public void setStage(Stage stage)
	{
		this.stage = stage;
		
	}

	public List<Card> getDiscards()
	{
		return discards;
	}


	public Player getPlayer(int playerId)
	{
		return players.get(playerId);
	}

	public void takeFromBank(int amount, final Player player)
	{
		final List<Effect> bankEffects = new ArrayList<Effect>();
		players.forEach(
				p->bankEffects.addAll(
						Utilities.filterElements(
								p.getGameElements(),Effect.class
								).stream().filter(
										e->e.getActivationPoint()==ActivationPoint.TAKE_FROM_BANK
										).collect(Collectors.toList()
								)
						)
				);
		
		
		bankEffects.stream().forEach(effect->effect.performEffect(this, player));
		player.setCoins(player.getCoins()+amount);
		if (player.getCoins()<0)
		{
			player.setCoins(0);
		}
	}

	public void discard(Card cardToDiscard,Player player) throws Exception
	{
		if (cardToDiscard == null)
		{
			throw new Exception("Trying to discard null!");
		}
		
		if (player.getHand().contains(cardToDiscard))
		{
			takeFromBank(3,player);
			player.getHand().remove(cardToDiscard);
			discards.add(cardToDiscard);
		}
		else
		{
			throw new Exception("Player "+player+" attempted to discard card: "+cardToDiscard+" however the player did not have the card in the current hand.");
		}
	}

}

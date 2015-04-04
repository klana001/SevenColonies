package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import common.Utilities;
import cards.CivilianStructure;
import cards.CommercialStructure;
import cards.MilitaryStructure;
import cards.manufacturedgoods.ManufacturedGood;
import cards.rawmaterials.RawMaterial;
import cards.scientificstructures.ScientificStructure;
import classes.Card;
import classes.Hand;
import classes.Round;
import effects.Effect;
import effects.Effect.ActivationPoint;
import player.NullPlayer;
import player.Player;
import wonders.Wonder;

public class Game
{
	static public Random rand = new Random(1);
	public Game() throws Exception
	{
		
		GameState gameState = new GameState();

		
		gameState.setWonders(new ArrayList<Wonder>(Wonder.constructWonders()));
		Collections.shuffle(gameState.getWonders(), rand);
		
		List<Player> players = new ArrayList<Player>();
		players.add(new NullPlayer());
		players.add(new NullPlayer());
		players.add(new NullPlayer());
		Collections.shuffle(players);
		
		
		gameState.setPlayers(players);
		
		// assign neighbours
		for (int i=0;i<players.size();i++)
		{
			Player currentPlayer = players.get(i);
			currentPlayer.setId(i);

			int leftNeighbourIndex=(i+players.size()-1)%players.size();
			int rightNeighbourIndex=(i+players.size()+1)%players.size();
			
			currentPlayer.setLeftNeighbourId(leftNeighbourIndex);
			currentPlayer.setRightNeighbourId(rightNeighbourIndex);
		}
		
		//set starting money
		players.stream().forEach(p->p.setCoins(3));
		

		// let players select their wonder
		int wondersPerPlayer = gameState.getWonders().size()/players.size();
		int index=0;
		for (Player player : players)
		{
			List<Wonder> wonderCandidates = gameState.getWonders().subList(index, (index+=wondersPerPlayer));
			final Wonder playerChosenWonder = player.chooseWonder(wonderCandidates);
			
			Wonder chosenWonder =wonderCandidates.stream().filter(w->w.getName().equals(playerChosenWonder.getName())).findAny().orElse(null);
			if (chosenWonder!=null)
			{
				player.setWonder(chosenWonder);
			}
			else
			{
				if (playerChosenWonder==null)
				{
					throw new Exception("Player "+player+" attempted to choose null wonder");
				}
				throw new Exception("Player "+player+" attempted to choose wonder: "+playerChosenWonder.getName()+" however it was not one of the wonders available to the player");
			}
		}
		


		gameState.setAge(Age.AGE_1);
		
		// main game loop
		while (gameState.getAge()  !=Age.END)
		{
			// assign starting hands
			
			final Hand currentAgeCards = new Hand();
			CivilianStructure.constructCivilianStructures(gameState.getAge()).forEach(w -> currentAgeCards.add(w));
			RawMaterial.constructRawMaterials(gameState.getAge()).forEach(w -> currentAgeCards.add(w));
			ManufacturedGood.constructManufacturedGoods(gameState.getAge()).forEach(w -> currentAgeCards.add(w));
			ScientificStructure.constructScientificStructures(gameState.getAge()).forEach(w -> currentAgeCards.add(w));
			MilitaryStructure.constructMilitaryStructures(gameState.getAge()).forEach(w -> currentAgeCards.add(w));
			CommercialStructure.constructCommercialStructures(gameState.getAge()).forEach(w -> currentAgeCards.add(w));

			
			Collections.shuffle(currentAgeCards, rand);
			ArrayList<Card> remainingCards = new ArrayList<Card>(currentAgeCards);
			
			System.out.println("AGE1");
			currentAgeCards.stream().forEach(c-> System.out.println(c.getName()));
			
			int cardsPerPlayer = currentAgeCards.size()/players.size();
			
			for (Player player : players)
			{
				Hand hand = new Hand();
				
				for (int i=0;i<cardsPerPlayer;i++)
				{
					hand.add(remainingCards.remove(0));
				}
				
				player.setHand(hand);
			}
			
			
			// perform rounds
			
			gameState.setRound(Round.FIRST);
			while (gameState.getRound()!=Round.LAST)
			{
				// all players make their choice of action
				List<Player> actionablePlayers = players.stream().filter(p->p.getHand().size()>0).collect(Collectors.toList());
				
				for (Player player : actionablePlayers)
				{
					// calculate possible actions;
					HashMap<Integer,Action> actionCandidates = ActionGenerator.generateActions(player, gameState);
					
					
					final Action playerChosenAction = player.chooseAction(new ArrayList<Action>(actionCandidates.values()));

					Action chosenAction = actionCandidates.get(playerChosenAction.getId());
					
					if (chosenAction!=null)
					{
						player.setAction(playerChosenAction);
					}
					else
					{
						throw new Exception("Player "+player+" attempted to choose Action: "+playerChosenAction+" however it was not one of the actions available to the player");
					}
				}
				
				// sort player processing by chosen action priority
				Collections.sort(actionablePlayers, new Comparator<Player>()
				{

					@Override
					public int compare(Player o1, Player o2)
					{
						return o1.getAction().getPriority()-o2.getAction().getPriority();
					}
				});
				
				GameState newGameState= new GameState(gameState);
				
				// update game state based on chosen actions
				for (Player player : actionablePlayers)
				{
					player.getAction().perform(gameState,newGameState);
				}
				gameState=newGameState;
				
				// reset player actions
				players.stream().forEach(p->p.setAction(null));
				
				boolean stillMoreRounds = true;
				for (Player player : players)
				{
					stillMoreRounds &= player.canPerformMoreActions();
				}
				
				// pass hands on
				Player firstPlayer = players.get(0);
				Player lastPlayer = players.get(players.size()-1);
				Hand firstPlayerHand = (Hand) firstPlayer.getHand();
				Hand lastPlayerHand = (Hand) lastPlayer.getHand();
				
				switch (gameState.getAge())
				{
					case AGE_1:
					case AGE_3:
						// pass left
						Player currentPlayer = lastPlayer;
						do
						{
							Player neighbour = gameState.getPlayer(currentPlayer.getLeftNeighbourId());
							currentPlayer.setHand(neighbour.getHand());
							currentPlayer=neighbour;
						} while (currentPlayer!=lastPlayer);
						firstPlayer.setHand(lastPlayerHand);
						break;
					case AGE_2:
						// pass right
						currentPlayer = firstPlayer;
						do
						{
							Player neighbour = gameState.getPlayer(currentPlayer.getRightNeighbourId());
							currentPlayer.setHand(neighbour.getHand());
							currentPlayer=neighbour;
						} while (currentPlayer!=firstPlayer);
						lastPlayer.setHand(firstPlayerHand);
						break;	
					default:
						throw new Exception("Unknown Age: "+gameState.getAge());
				}
				
				
				gameState.setRound(stillMoreRounds?Round.IN_BETWEEN:Round.LAST);
				final GameState finalGameState=gameState;
				if (stillMoreRounds==false)
				{
					// see if there are any end of age effect to apply.
					for (Player player : players)
					{
						Utilities.filterElements(player.getGetGameElementsNewThisAge(), Effect.class).stream().filter(e-> e.getActivationPoint()==ActivationPoint.AT_END_OF_AGE).forEach(e->e.performEffect(finalGameState, null));
					}
				}
			}
			
			// see if there are any end of age effect to apply.
			
			// perform military
			gameState.setStage(GameState.Stage.MILITARY);
			
			// increment age

			gameState.setAge(Age.values()[Arrays.asList(Age.values()).indexOf(gameState.getAge())+1]);
		}
		
		// calculate scores
		
		// determine winner
	}
}

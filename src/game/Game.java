package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import common.Military;
import common.Scoreable;
import common.Utilities;
import cards.CivilianStructure;
import cards.CommercialStructure;
import cards.Guild;
import cards.MilitaryStructure;
import cards.manufacturedgoods.ManufacturedGood;
import cards.rawmaterials.RawMaterial;
import cards.scientificstructures.Cog;
import cards.scientificstructures.ScientificStructure;
import cards.scientificstructures.Sextant;
import cards.scientificstructures.Tablet;
import classes.Card;
import classes.Hand;
import classes.Round;
import effects.Effect;
import effects.Effect.ActivationPoint;
import player.HumanPlayer;
import player.NullPlayer;
import player.Player;
import wonders.Wonder;
import wonders.wonderstage.WonderStage;

public class Game
{
	private static final int POINTS_PER_SCIENTIFIC_SYMBOL_SET = 7;
	private static final int COINS_PER_POINT = 3;
	static public Random rand = new Random(1234356);
	public Game() throws Exception
	{
		
		GameState gameState = new GameState();

		
		gameState.setWonders(new ArrayList<Wonder>(Wonder.constructWonders()));
		Collections.shuffle(gameState.getWonders(), rand);
		
		{
			List<Player> players = new ArrayList<Player>();
			players.add(new NullPlayer("Player A"));
			players.add(new NullPlayer("Player B"));
			players.add(new NullPlayer("Player C"));
//			players.add(new HumanPlayer("HUMAN"));
			Collections.shuffle(players,rand);
			
			
			gameState.setPlayers(players);
		}
		
		// assign neighbours
		for (int i=0;i<gameState.getPlayers().size();i++)
		{
			Player currentPlayer = gameState.getPlayers().get(i);
			currentPlayer.setId(i);

			int leftNeighbourIndex=(i+gameState.getPlayers().size()-1)%gameState.getPlayers().size();
			int rightNeighbourIndex=(i+gameState.getPlayers().size()+1)%gameState.getPlayers().size();
			
			currentPlayer.setLeftNeighbourId(leftNeighbourIndex);
			currentPlayer.setRightNeighbourId(rightNeighbourIndex);
		}
		
		//set starting money
		gameState.getPlayers().stream().forEach(p->p.setCoins(3));
		

		// let players select their wonder
		int wondersPerPlayer = gameState.getWonders().size()/gameState.getPlayers().size();
		int index=0;
		for (Player player : gameState.getPlayers())
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
			
			
			// add no. player + 2 random guilds to current age deck.
			if (gameState.getAge() == Age.AGE_3)
			{
				List<Card> guilds = new ArrayList<Card>();
				Guild.constructGuilds(gameState.getAge()).forEach(w -> guilds.add(w));
				Collections.shuffle(guilds, rand);
				for (int i=0;i<gameState.getPlayers().size()+2;i++)
				{
					currentAgeCards.add(guilds.remove(0));
				}
			}

			
			Collections.shuffle(currentAgeCards, rand);
			ArrayList<Card> remainingCards = new ArrayList<Card>(currentAgeCards);
			
	
//			currentAgeCards.stream().forEach(c-> System.out.println(c.getName()));
			
			int cardsPerPlayer = currentAgeCards.size()/gameState.getPlayers().size();
			
			for (Player player : gameState.getPlayers())
			{
				Hand hand = new Hand();
				
				for (int i=0;i<cardsPerPlayer;i++)
				{
					hand.add(remainingCards.remove(0));
				}
				
				player.setHand(hand);
				
				// reset player
				player.getGetGameElementsNewThisAge().clear();
			}
			
			
			// perform rounds
			
			int round=0;
			gameState.setRound(Round.FIRST);
			while (gameState.getRound()!=Round.LAST)
			{
				round++;
//				System.out.println("AGE: "+gameState.getAge()+" round: "+round);
				// all players make their choice of action
				List<Player> actionablePlayers = gameState.getPlayers().stream().filter(p->p.getHand().size()>0).collect(Collectors.toList());
				
				for (Player player : actionablePlayers)
				{
//					System.out.println("Player: "+player+" with "+player.getCoins()+" coins:");
					// calculate possible actions;
					HashMap<Integer,Action> actionCandidates = ActionGenerator.generateActions(player, gameState);
					
					
					final Action playerChosenAction = player.chooseAction(new ArrayList<Action>(actionCandidates.values()), gameState);

					Action chosenAction = actionCandidates.get(playerChosenAction.getId());
					
					if (chosenAction!=null)
					{
						chosenAction.setData(playerChosenAction.getData());
						player.setAction(chosenAction);
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
				
				GameState newGameState= Utilities.cloneObject(gameState);
				actionablePlayers = newGameState.getPlayers().stream().filter(p->p.getHand().size()>0).collect(Collectors.toList());
				
				// update game state based on chosen actions
				for (Player player : actionablePlayers)
				{
					player.getAction().perform(gameState,newGameState);
					player.getAction().getPayments().forEach(p->p.perform(newGameState, player));
				}
				gameState=newGameState;
				
				// reset player actions
				gameState.getPlayers().stream().forEach(p->p.setAction(null));
				
				boolean stillMoreRounds = true;
				for (Player player : gameState.getPlayers())
				{
					stillMoreRounds &= player.canPerformMoreActions();
				}
				
				// pass hands on
				Player firstPlayer = gameState.getPlayers().get(0);
				Player lastPlayer = gameState.getPlayers().get(gameState.getPlayers().size()-1);
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
					for (Player player : gameState.getPlayers())
					{
						Utilities.filterElements(player.getGetGameElementsNewThisAge(), Effect.class).stream().filter(e-> e.getActivationPoint(finalGameState, player)==ActivationPoint.AT_END_OF_AGE).forEach(e->e.performEffect(finalGameState, null));
					}
				}
			}
			
			// see if there are any end of age effect to apply.
			
			// perform military
			gameState.setStage(GameState.Stage.MILITARY);
			for (Player player : gameState.getPlayers())
			{
				int playerStrength = Utilities.filterElements(player.getGameElements(), Military.class).stream().mapToInt(Military::getStrength).sum();
				int leftPlayerStrength = Utilities.filterElements(gameState.getPlayer(player.getLeftNeighbourId()).getGameElements(), Military.class).stream().mapToInt(Military::getStrength).sum();
				int rightPlayerStrength = Utilities.filterElements(gameState.getPlayer(player.getRightNeighbourId()).getGameElements(), Military.class).stream().mapToInt(Military::getStrength).sum();
				
				MilitaryToken victoryToken;
				MilitaryToken defeatToken = new DefeatToken();
				switch (gameState.getAge())
				{
					case AGE_1:
						victoryToken = new OnePointMilitaryVictoryToken();
						break;
					case AGE_2:
						victoryToken = new ThreePointMilitaryVictoryToken();
						break;
					case AGE_3:
						victoryToken = new FivePointMilitaryVictoryToken();
						break;
					default:
						throw new RuntimeException("un expected age: "+gameState.getAge());
				}
				
				if (playerStrength>leftPlayerStrength)
				{
					player.addMilitaryToken(victoryToken);
				}
				else if (playerStrength==leftPlayerStrength)
				{
					// stalemate.
				}
				else
				{
					player.addMilitaryToken(defeatToken);
				}
				
				if (playerStrength>rightPlayerStrength)
				{
					player.addMilitaryToken(victoryToken);
				}
				else if (playerStrength==rightPlayerStrength)
				{
					// stalemate.
				}
				else
				{
					player.addMilitaryToken(defeatToken);
				}

			}
			
			
			// increment age

			gameState.setAge(Age.values()[Arrays.asList(Age.values()).indexOf(gameState.getAge())+1]);
		}
		final GameState finalGameState=gameState;
		
		// perform non-scoreable end of game effects 
		for (Player player : gameState.getPlayers())
		{
			Utilities.filterElements(player.getGameElements(), Effect.class).stream().filter(e-> e.getActivationPoint(finalGameState, player)==ActivationPoint.AT_END_OF_GAME).forEach(e->e.performEffect(finalGameState, player));
		}

		for (Player player : gameState.getPlayers())
		{
			// calculate guild score
			Utilities.filterElements(player.getGameElements(), Guild.class).stream().forEach(
					g->g.getEffects().stream().filter(e-> e.getActivationPoint(finalGameState, player)==ActivationPoint.AT_END_OF_GAME).forEach(e->e.performEffect(finalGameState, player))
					);
			// calculate wonder stage score
			Utilities.filterElements(Utilities.filterElements(player.getGameElements().stream(), WonderStage.class),Scoreable.class).forEach(s->player.modifyScore(s.calculateScore()));
		
			// calculate military score
			player.getMilitaryTokens().forEach(m->player.modifyScore(m.getScoreModificationAmount()));
		
			// calculate civilian score
			Utilities.filterElements(player.getGameElements(), CivilianStructure.class).forEach(c->player.modifyScore(c.getPoints()));
		
			// calculate scientific score
			int cogCount = (int) Utilities.filterElements(player.getGameElements(), Cog.class).stream().count();
			int sextantCount = (int) Utilities.filterElements(player.getGameElements(), Sextant.class).stream().count();
			int tabletCount = (int) Utilities.filterElements(player.getGameElements(), Tablet.class).stream().count();
			
			int setCount=Math.min(Math.min(cogCount, sextantCount),tabletCount);
			
			int resultantScore= (int) ( Math.pow(cogCount, 2) + Math.pow(sextantCount, 2) + Math.pow(tabletCount, 2) + POINTS_PER_SCIENTIFIC_SYMBOL_SET * setCount);
			player.modifyScore(resultantScore);
		
			// calculate treasury score
			player.modifyScore(player.getCoins()/COINS_PER_POINT);
		}
		
		// determine winner
		Collections.sort(gameState.getPlayers(),new Comparator<Player>()
		{

			// sort in reverse order
			@Override
			public int compare(Player arg0, Player arg1)
			{
				return arg1.getScore()-arg0.getScore();
			}
		});
		gameState.getPlayers().forEach(p-> System.out.println(p.getName()+" score: "+p.getScore()));
	}
}

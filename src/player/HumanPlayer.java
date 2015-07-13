package player;

import game.Action;
import game.Game;
import game.GameState;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import classes.Card;
import actions.BuildWonderStage;
import actions.DiscardForThreeCoins;
import wonders.Wonder;

public class HumanPlayer extends Player
{
	static class Selector<E>
	{
		public E select(Collection<E> options,String message)
		{
			JPanel panel = new JPanel();
			DefaultListModel<E> listModel = new DefaultListModel<E>();
			options.stream().forEach(w -> listModel.addElement(w));
			
			Object[] result = new Object[1];
			JList<E> list = new JList<E>(listModel);
			panel.add(list);
	
			list.addListSelectionListener(e-> result[0]=listModel.get(e.getFirstIndex()));
			JOptionPane.showMessageDialog(null, panel,message,JOptionPane.INFORMATION_MESSAGE);
			return (E) result[0];
		}
	}
	
	Wonder wonder;
	public HumanPlayer(String name)
	{
		super(name);
	}
	
	@Override
	public Wonder chooseWonder(Collection<Wonder> wonders)
	{
		return new Selector<Wonder>().select(wonders, "Choose Wonder...");
	}

	@Override
	public Action chooseAction(List<Action> actionCandidates, GameState gameState)
	{
		JFrame frame = new JFrame();
		JPanel panel = new JPanel(new GridLayout(1,3));
		panel.add(displayPlayer(getLeftNeighbourId(),gameState));
		panel.add(displayPlayer(getId(),gameState));
		panel.add(displayPlayer(getRightNeighbourId(),gameState));
		
		frame.add(panel);
		
		frame.setSize(900, 300);
		frame.setVisible(true);
		

		
		Action result = new Selector<Action>().select(actionCandidates, "Choose Action...");
		
		if (result instanceof DiscardForThreeCoins || result instanceof BuildWonderStage)
		{
			try
			{
				result.setData(new Selector<Card>().select(getHand(), "Choose card to discard..."));
			}
			catch (Exception e)
			{
				throw new RuntimeException(e);
			}
		}
		
		frame.dispose();
		
		return result;

	}

	private JPanel displayPlayer(int id,GameState gameState)
	{
		Player player = gameState.getPlayer(id);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(new JLabel(player.getName()),BorderLayout.NORTH);
		JTextArea ta = new JTextArea();
		ta.append("Wonder: "+player.getWonder()+"\n");
		player.getWonder().getWonderStages().stream().filter(w->w.isBuilt()).forEach(e->ta.append("      "+e+"\n"));
		ta.append("Built:\n");
		player.getGameElements().stream().forEach(e->ta.append("      "+e+"\n"));
		panel.add(ta,BorderLayout.CENTER);
		return panel;
	}

	@Override
	public Object[] requestData(Object... data)
	{
		// TODO Auto-generated method stub
		return null;
	}



}

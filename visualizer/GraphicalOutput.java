package visualizer;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import game.agent.Building;
import game.agent.Unit;
import game.map.Field;
import game.map.Field.Tile;
import game.map.Field.TileType;

import javax.swing.JFrame;

public class GraphicalOutput
{
	GamePanel	gamePanel;
	JFrame		gameFrame;
	Menu		menu;

	Field		map;

	Point		lastChangedTile	= new Point(-1, -1);

	public GraphicalOutput(Field map)
	{
		this.map = map;
		int width = map.getWidth();
		int height = map.getHeight();

		gameFrame = new JFrame(String.format("MapEditor (%d, %d)", width,
				height));
		gameFrame.setLayout(null);
		gameFrame.setResizable(false);
		gameFrame.setSize(800, 635);

		menu = new Menu(this);
		menu.setLocation(600, 0);

		gamePanel = new GamePanel(width, height);
		gamePanel.setLocation(0, 0);
		gamePanel.setMap(map);

		gameFrame.add(gamePanel);
		gameFrame.add(menu);
		gameFrame.setVisible(true);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		gameFrame.addMouseListener(new MouseListener()
		{

			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				// TODO Auto-generated method stub
				gamePanel.repaint();
			}

			@Override
			public void mousePressed(MouseEvent arg0)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent arg0)
			{
				// TODO Auto-generated method stub
				gamePanel.repaint();
			}

			@Override
			public void mouseEntered(MouseEvent arg0)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent arg0)
			{
				// TODO Auto-generated method stub
				changeTile(gamePanel.getActiveCell(), menu.getActiveEntity(),
						menu.getActiveTeam());

				gamePanel.repaint();
			}
		});

		gameFrame.addMouseMotionListener(new MouseMotionListener()
		{

			@Override
			public void mouseMoved(MouseEvent e)
			{
				// TODO Auto-generated method stub
				Point activeTile = gamePanel.getActiveCell();

				if(activeTile == null)
					return;

				if(!(lastChangedTile.x == activeTile.x && lastChangedTile.y == activeTile.y))
				{
					menu.setActiveTile(activeTile);

					lastChangedTile.x = activeTile.x;
					lastChangedTile.y = activeTile.y;
				}
			}

			@Override
			public void mouseDragged(MouseEvent e)
			{
				Point activeTile = gamePanel.getActiveCell();

				if(activeTile == null)
					return;

				if(!(lastChangedTile.x == activeTile.x && lastChangedTile.y == activeTile.y))
				{
					menu.setActiveTile(activeTile);
					
					changeTile(activeTile, menu.getActiveEntity(),
							menu.getActiveTeam());
					gamePanel.repaint();

					lastChangedTile.x = activeTile.x;
					lastChangedTile.y = activeTile.y;
				}
			}
		});
	}

	public void changeTile(Point pos, char entity, int teamNumber)
	{
		if(pos == null)
			return;

		if(map.isOutOfField(pos.x, pos.y))
			return;

		Tile tempTile = map.getTileAt(pos);
		tempTile.removeSupplies();
		tempTile.setType(TileType.ROCK);
		tempTile.setBuilding(null);
		tempTile.setUnit(null);

		switch(entity)
		{
			case '#':
				tempTile.setType(TileType.LAVA);
				break;

			case 'X':
				tempTile.putSupplies();
				break;

			case '0':
				new Building.HeadQuarters(map, pos.x, pos.y)
				.setTeamNumber(teamNumber);
				break;

			case 'a':
				new Unit.Melee(map, pos.x, pos.y).setTeamNumber(teamNumber);
				break;

			case 'A':
				new Unit.Ranged(map, pos.x, pos.y).setTeamNumber(teamNumber);
				break;
		}
		;
	}

	public void update(Field map)
	{
		this.map = map;
		gameFrame.setTitle(String.format("MapEditor (%d, %d)", map.getWidth(),
				map.getHeight()));

		gamePanel.setMap(map);
		gameFrame.setVisible(true);
		gamePanel.repaint();
	}

	public void close()
	{
		gameFrame.dispose();

		// an alternative way:
		// WindowEvent wev = new WindowEvent(gameFrame,
		// WindowEvent.WINDOW_CLOSING);
		// Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
	}
}

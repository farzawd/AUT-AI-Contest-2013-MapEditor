package visualizer;

import game.agent.Unit;
import game.map.Field;
import game.map.Field.Tile;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class GamePanel extends JPanel
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	private int					gridWidth;
	private int					gridHeight;

	private int					graphicalWidth		= 600;
	private int					graphicalHeight		= 600;

	private int					cellEdge;
	private int					startX;
	private int					startY;

	private Field				map;

	private final Color			BROWN				= new Color(0xa08010);
	private final Color			DK_GREEN			= Color.LIGHT_GRAY;

	private int					frameNum;
	private int					maxFrames;

	public GamePanel(int width, int height)
	{
		this.gridWidth = width;
		this.gridHeight = height;

		this.setSize(graphicalWidth, graphicalWidth);

		this.setLayout(null);
		this.setBackground(Color.LIGHT_GRAY);

		this.map = new Field(width, height);

		this.setScaling();
	}

	private void setScaling()
	{
		cellEdge = Math.min(graphicalHeight / gridHeight, graphicalWidth
				/ gridWidth);

		startX = (graphicalWidth - (gridWidth * cellEdge)) / 2;
		startY = (graphicalHeight - (gridHeight * cellEdge)) / 2;
	}

	public synchronized void setMap(Field map)
	{
		this.gridWidth = map.getWidth();
		this.gridHeight = map.getHeight();
		
		setScaling();
		
		this.map = map;
	}

	public static Color getColorByNum(int num)
	{
		num %= 10;

		switch(num)
		{
			case 0:
				return Color.GREEN;
			case 1:
				return Color.WHITE;
			case 2:
				return Color.BLUE;
			case 3:
				return Color.RED;
			case 4:
				return Color.CYAN;
			case 5:
				return Color.MAGENTA;
			case 6:
				return Color.GRAY;
			case 7:
				return Color.YELLOW;
			case 8:
				return Color.BLACK;
			default:
				return Color.PINK;

		}
	}

	private void drawGrid(Graphics g)
	{
		g.setColor(Color.GRAY);

		for(int i = 0 ; i < gridWidth ; i++)
			for(int j = 0 ; j < gridHeight ; j++)
			{
				int tempX = startX + (i * cellEdge);
				int tempY = startY + (j * cellEdge);

				g.drawRect(tempX, tempY, cellEdge, cellEdge);
			}
	}

	private void drawSupply(Graphics g, int x, int y)
	{
		int tempX = startX + (x * cellEdge);
		int tempY = startY + (y * cellEdge);

		g.setColor(BROWN);
		g.fillRect(tempX + (cellEdge / 4), tempY + (cellEdge / 4),
				cellEdge / 2, cellEdge / 2);

		g.setColor(Color.BLACK);
		g.drawRect(tempX + (cellEdge / 4), tempY + (cellEdge / 4),
				cellEdge / 2, cellEdge / 2);
	}

	private Point drawActiveCell(Graphics g)
	{
		try
		{
			Point p = this.getMousePosition();

			g.setColor(Color.BLACK);
			g.drawRect(startX + ((p.x - startX) / cellEdge) * cellEdge, startY
					+ ((p.y - startY) / cellEdge) * cellEdge, cellEdge,
					cellEdge);
			
			p.x = (p.x - startX) / cellEdge;
			p.y = (p.y - startY) / cellEdge;
			return p;
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	public Point getActiveCell()
	{
		Point p = this.getMousePosition();
		
		try
		{
			p.x = (p.x - startX) / cellEdge;
			p.y = (p.y - startY) / cellEdge;
			
			return p;
		}
		catch(Exception e)
		{
			return null;
		}
	}

	// private void drawWall(Graphics g, int x, int y)
	// {
	// int tempX = startX + (x * cellEdge);
	// int tempY = startY + (y * cellEdge);
	//
	// g.setColor(Color.ORANGE);
	// g.fillRect(tempX, tempY, cellEdge, cellEdge);
	// }


	private void drawWall(Graphics g, int x, int y)
	{
		g.setColor(Color.ORANGE);
		
		int tempX = startX + (x * cellEdge);
		int tempY = startY + (y * cellEdge);

		g.fillRect(tempX, tempY, cellEdge, cellEdge);

	}

	private void drawMeleeUnit(Graphics g, int x, int y, Color color)
	{
		int tempX = startX + (x * cellEdge);
		int tempY = startY + (y * cellEdge);
		
		int[] xs =
			{ tempX, tempX + (cellEdge / 2), tempX + cellEdge, tempX + (cellEdge / 2) };
		int[] ys =
			{ tempY + (cellEdge / 2), tempY, tempY + (cellEdge / 2), tempY + cellEdge };

		g.setColor(color);
		g.fillPolygon(xs, ys, 4);

		g.setColor(Color.BLACK);
		g.drawPolygon(xs, ys, 4);
	}

	private void drawRangedUnit(Graphics g, int x, int y, Color color)
	{
		int tempX = startX + (x * cellEdge);
		int tempY = startY + (y * cellEdge);
		
		g.setColor(color);
		g.fillOval(tempX, tempY, cellEdge, cellEdge);

		g.setColor(Color.BLACK);
		g.drawOval(tempX, tempY, cellEdge, cellEdge);
	}

	private void drawHQ(Graphics g, int x, int y, Color color)
	{
		int tempX = startX + (x * cellEdge);
		int tempY = startY + (y * cellEdge);

		g.setColor(color);
		g.fillRect(tempX, tempY, cellEdge, cellEdge);

		g.setColor(Color.BLACK);
		g.drawRect(tempX, tempY, cellEdge, cellEdge);

		g.drawLine(tempX, tempY, tempX + cellEdge, tempY + cellEdge);
		g.drawLine(tempX + cellEdge, tempY, tempX, tempY + cellEdge);
	}

	private synchronized void paintTerrain(Graphics graphics, int frameNum,
			int maxFrames)
	{
		if(frameNum < 0)
			frameNum = 0;
		if(frameNum > maxFrames)
			frameNum = maxFrames;

		Graphics2D g = (Graphics2D) graphics;

		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHints(rh);
		// Graphics g=graphics;
		

		for(int i = 0 ; i < gridWidth ; i++)
		{
			for(int j = 0 ; j < gridHeight ; j++)
			{
				Tile tempTile = map.getTileAt(i, j);
				
				if(!tempTile.isWalkable())
					drawWall(g, i, j);
				
				if(tempTile.getBuilding() != null)
					drawHQ(g, i, j, getColorByNum(tempTile.getBuilding().getTeamNumber()));
					
				
				if(tempTile.hasSupplies())
					drawSupply(g, i, j);
				
				if(tempTile.getUnit() != null)
				{
					Unit tempUnit = tempTile.getUnit();
					if(tempUnit.getAgentTypeID() == 'a')
						drawMeleeUnit(g, i, j, getColorByNum(tempUnit.getTeamNumber()));
					else
						drawRangedUnit(g, i, j, getColorByNum(tempUnit.getTeamNumber()));
				}
			}
		}		
	}

	public synchronized void paint(Graphics g)
	{
		super.paint(g);
		
		g.setColor(DK_GREEN);
		g.fillRect(startX, startY, cellEdge * gridWidth, cellEdge * gridHeight);
		
		paintTerrain(g, frameNum, maxFrames);
		drawGrid(g);
		drawActiveCell(g);
		
		g.setColor(Color.BLACK);
		g.drawRect(startX, startY, cellEdge * gridWidth, cellEdge * gridHeight);
	}
}

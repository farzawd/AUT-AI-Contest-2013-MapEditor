package game.agent;

import game.map.Field;

import java.awt.Point;

/**
 * represents a building in the game
 * 
 * @author farzad
 * 
 */
public abstract class Building extends Agent
{
	public Building(Field map, Point position)
	{
		super(map, position);
		
		if(map.getTileAt(position).getBuilding() == null)
			map.getTileAt(position).setBuilding(this);
	}
	
	/**
	 * represents the HeadQuarters (main building)
	 * 
	 * @author farzad
	 * 
	 */
	public static final class HeadQuarters extends Building
	{
		public HeadQuarters(Field map, int x, int y)
		{
			super(map, new Point(x, y));
			
			setAgentTypeID('0');
		}
	}
}

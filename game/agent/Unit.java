package game.agent;

import game.map.Field;

import java.awt.Point;



public abstract class Unit extends Agent
{
	public Unit(Field map, Point position)
	{
		super(map, position);

		if(map.getTileAt(position).getUnit() == null)
			map.getTileAt(position).setUnit(this);
	}

	/**
	 * represents a melee unit
	 * @author farzad
	 *
	 */
	public static final class Melee extends Unit
	{
		public Melee(Field map, int x, int y)
		{
			super(map, new Point(x, y));
			
			setAgentTypeID('a');
		}
	}

	/**
	 * represents a ranged unit
	 * @author farzad
	 *
	 */
	public static final class Ranged extends Unit
	{
		public Ranged(Field map, int x, int y)
		{
			super(map, new Point(x, y));
			
			setAgentTypeID('A');
		}
	}

	/**
	 * represents a unit's type. do not change the order of the values, as it matters while spawning new units.
	 * @author farzad
	 *
	 */
	public enum UnitType
	{
		MELEE, RANGED;
	}
}
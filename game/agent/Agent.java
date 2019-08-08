package game.agent;

import game.map.Field;

import java.awt.Point;

/**
 * represents an agent in the game
 * 
 * @author farzad
 * 
 */
public abstract class Agent
{
	private Field	activeGameField;
	private Point	position;
	private int		teamNumber;

	private char	agentTypeID;
	public Agent(Field map, Point position)
	{		
		this.activeGameField = map;
		this.position = position;
	}
	
	public char getAgentTypeID()
	{
		return this.agentTypeID;
	}

	public void setAgentTypeID(char id)
	{
		this.agentTypeID = id;
	}

	/**
	 * 
	 * @return the agents position.x
	 */
	public int getX()
	{
		return position.x;
	}

	/**
	 * 
	 * @return the agents position.y
	 */
	public int getY()
	{
		return position.y;
	}

	public Point getPosition()
	{
		return this.position;
	}

	public void setPosition(Point position)
	{
		this.position.x = position.x;
		this.position.y = position.y;
	}
	
	/**
	 * 
	 * @return this agent's team number
	 */
	public int getTeamNumber()
	{
		return teamNumber;
	}

	/**
	 * sets this agent's team number
	 * 
	 * @param teamNumber
	 */
	public Agent setTeamNumber(int teamNumber)
	{
		this.teamNumber = teamNumber;
		
		return this;
	}

	public Field getActiveGameField()
	{
		return activeGameField;
	}
}

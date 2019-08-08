package game.map;

import game.map.Field.Tile;

public class MapHandler
{
	private Field map;

	private int width;
	private int height;

	public MapHandler(Field map)
	{
		this.map = map;

		this.width = map.getWidth();
		this.height = map.getHeight();
	}

	public char[][] getPrintableMap()
	{
		char[][] tempMap = new char [width][height];

		for(int col = 0 ; col < width ; col++)
			for(int row = 0 ; row < height ; row++)
			{
				Tile tempTile = map.getTileAt(col, row);

				if(tempTile.getUnit() != null)
					tempMap[col][row] = (char)(tempTile.getUnit().getAgentTypeID() + tempTile.getUnit().getTeamNumber());
				else if(tempTile.getBuilding() != null)
					tempMap[col][row] = (char)(tempTile.getBuilding().getTeamNumber() + '0');
				else if(tempTile.hasSupplies())
					tempMap[col][row] = 'X';
				else if(!tempTile.isWalkable())
					tempMap[col][row] = '#';
				else
					tempMap[col][row] = '.';
			}

		return tempMap;
	}
}

package main;

import java.io.IOException;

import game.map.Field;
import visualizer.GraphicalOutput;

public class Main
{
	public static void main(String[] args) throws IOException
	{
		new GraphicalOutput(new Field(20, 20));
	}
}

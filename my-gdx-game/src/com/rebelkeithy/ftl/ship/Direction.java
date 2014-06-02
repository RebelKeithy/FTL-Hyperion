package com.rebelkeithy.ftl.ship;

public class Direction 
{
	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	
	public static int getFromString(String dir) 
	{
		if(dir.toLowerCase().equals("up"))
			return UP;
		if(dir.toLowerCase().equals("down"))
			return DOWN;
		if(dir.toLowerCase().equals("left"))
			return LEFT;
		if(dir.toLowerCase().equals("right"))
			return RIGHT;
		
		return -1;
	}
}

package com.rebelkeithy.ftl.ship;

import com.rebelkeithy.ftl.properties.Properties;

public class Tile 
{
	public static final String crew = "crew";
	public static final String blocked = "blocked";
	
	private Room room;
	private int x;
	private int y;
	
	private Properties properties;
	
	public Tile(Room room, int x, int y)
	{
		this.room = room;
		this.x = x;
		this.y = y;
		
		properties = new Properties();
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public Room getRoom()
	{
		return room;
	}
	
	public Properties getProperties()
	{
		return properties;
	}
}

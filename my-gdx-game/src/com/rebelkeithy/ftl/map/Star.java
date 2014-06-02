package com.rebelkeithy.ftl.map;

import java.util.ArrayList;
import java.util.List;

import com.rebelkeithy.ftl.map.events.MapEvent;

public class Star 
{
	private int starID;
	private static int nextID = 0;
	
	private Sector sector;
	private List<Integer> neighbors;
	
	private MapEvent event;
	
	public Star()
	{
		this.starID = nextID;
		nextID++;
		neighbors = new ArrayList<Integer>();
	}
	
	public void addNeighbor(int starID)
	{
		neighbors.add(starID);
	}
	
	public int getStarID()
	{
		return starID;
	}

	public MapEvent getEvent() 
	{
		return event;
	}

	public boolean isConnectedTo(int starID) 
	{
		return neighbors.contains(starID);
	}

	public void setEvent(MapEvent event) 
	{
		this.event = event;
	}
	
	public Sector getSector() 
	{
		return sector;
	}
}

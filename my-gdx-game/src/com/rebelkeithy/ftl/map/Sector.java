package com.rebelkeithy.ftl.map;

import java.util.HashMap;
import java.util.Map;

public class Sector 
{
	private int sectorID;
	private static int nextID = 0;
	
	//private String sectorType;
	private Map<Integer, Star> starMap;
	
	public Sector()
	{
		sectorID = nextID;
		nextID++;
		starMap = new HashMap<Integer, Star>();
	}
	
	public int getSectorID()
	{
		return sectorID;
	}

	public void addStar(Star star) 
	{
		starMap.put(star.getStarID(), star);
	}

	public Star getStar(int starID) 
	{
		return starMap.get(starID);
	}
}

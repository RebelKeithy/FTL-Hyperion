package com.rebelkeithy.ftl.map.events;

import java.util.List;

import com.rebelkeithy.ftl.FTLGame;
import com.rebelkeithy.ftl.ship.Ship;

public abstract class MapEvent 
{
	protected FTLGame game;
	protected int sector;
	protected int star;
	
	public MapEvent(FTLGame game, int sector, int star)
	{
		this.game = game;
		this.sector = sector;
		this.star = star;
	}
	
	public void enter(Ship ship)
	{
		
	}

	public void leave(Ship ship) 
	{
		
	}
	
	public abstract void activate();
	
	public abstract void preformAction(String action);
	
	public abstract List<String> getActions();

	public abstract boolean allowShipControl();

	public abstract void update(double dt);
}

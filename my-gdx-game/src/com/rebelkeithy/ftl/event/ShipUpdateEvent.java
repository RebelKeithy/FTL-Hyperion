package com.rebelkeithy.ftl.event;

import com.rebelkeithy.ftl.ship.Ship;

public class ShipUpdateEvent extends Event 
{
	public Ship ship;
	public double dt;
	
	public ShipUpdateEvent(Ship ship, double dt)
	{
		this.ship = ship;
		this.dt = dt;
	}
}

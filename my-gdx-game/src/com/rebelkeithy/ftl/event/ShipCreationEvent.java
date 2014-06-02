package com.rebelkeithy.ftl.event;

import com.rebelkeithy.ftl.ship.Ship;

public class ShipCreationEvent extends Event
{
	public Ship ship;
	
	public ShipCreationEvent(Ship ship)
	{
		this.ship = ship;
	}
}

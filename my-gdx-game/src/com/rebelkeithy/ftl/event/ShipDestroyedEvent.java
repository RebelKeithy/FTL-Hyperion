package com.rebelkeithy.ftl.event;

import com.rebelkeithy.ftl.ship.Ship;

public class ShipDestroyedEvent extends Event
{
	public Ship ship;

	public ShipDestroyedEvent(Ship ship)
	{
		this.ship = ship;
	}
}

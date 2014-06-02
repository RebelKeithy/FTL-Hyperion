package com.rebelkeithy.ftl.event;

import com.rebelkeithy.ftl.ship.Door;

public class DoorBreakEvent extends Event
{
	private Door door;

	public DoorBreakEvent(Door door) 
	{
		this.door = door;
	}
	
	public Door getDoor()
	{
		return door;
	}	
}

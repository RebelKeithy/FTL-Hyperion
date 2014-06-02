package com.rebelkeithy.ftl.event;

import com.rebelkeithy.ftl.crew.Crew;
import com.rebelkeithy.ftl.ship.Door;

public class DoorUnlockEvent extends Event
{
	private Door door;
	private Crew crew;

	public DoorUnlockEvent(Door door, Crew crew) 
	{
		this.door = door;
		this.crew = crew;
	}
	
	public Door getDoor()
	{
		return door;
	}
	
	public Crew getCrew()
	{
		return crew;
	}
	
}

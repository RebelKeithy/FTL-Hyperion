package com.rebelkeithy.ftl.event;

import com.rebelkeithy.ftl.crew.Crew;
import com.rebelkeithy.ftl.ship.Door;

public class DoorAttackEvent extends Event
{
	public double attack;
	private Door door;
	private Crew crew;
	
	public DoorAttackEvent(Door door, Crew crew, double attack) 
	{
		this.attack = attack;
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

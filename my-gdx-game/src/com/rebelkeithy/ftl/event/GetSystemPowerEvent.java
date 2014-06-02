package com.rebelkeithy.ftl.event;

import com.rebelkeithy.ftl.ship.Ship;

public class GetSystemPowerEvent extends Event
{
	public Ship ship;
	public String system;
	public int power;
	
	public GetSystemPowerEvent(Ship ship, String system, int power)
	{
		this.ship = ship;
		this.system = system;
		this.power = power;
	}
}

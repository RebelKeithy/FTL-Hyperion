package com.rebelkeithy.ftl.crew;

import java.util.Set;

import com.google.common.eventbus.Subscribe;
import com.rebelkeithy.ftl.Main;
import com.rebelkeithy.ftl.event.CrewDamagedEvent;
import com.rebelkeithy.ftl.event.CrewUpdateEvent;
import com.rebelkeithy.ftl.event.GetSystemPowerEvent;
import com.rebelkeithy.ftl.event.ShipCreationEvent;
import com.rebelkeithy.ftl.properties.Properties;
import com.rebelkeithy.ftl.ship.Room;
import com.rebelkeithy.ftl.systems.AbstractShipSystem;

public class CrewEffects 
{
	
	private int dt;

	public CrewEffects()
	{
		Main.GLOBAL_BUS.register(this);
	}
	
	@Subscribe
	public void shipCreation(ShipCreationEvent event)
	{
		//event.ship.EVENT_BUS.register(this);
	}
	
	@Subscribe
	public void crewUdate(CrewUpdateEvent event)
	{
		Crew crew = event.getCrew();
		if(crew.getRace().equals("Lanius"))
		{
			if(crew.getRoom() != null)
			{
				Properties properties = crew.getRoom().getProperties();
				double oxygen = properties.getDouble("oxygen");
				oxygen -= 2 * dt;
				if(oxygen < 0)
					oxygen = 0;
				properties.setDouble("oxygen", oxygen);
			}
		}
	}
	
	@Subscribe
	public void crewDamagedEvent(CrewDamagedEvent event)
	{
		if(event.getSource().equals("oxygen"))
		{
			if(event.getCrew().getRace().equals("Lanius"))
			{
				event.cancel = true;
			}
			if(event.getCrew().getRace().equals("Crystal"))
			{
				event.damage *= 0.5;
			}
		}
	}
	
	@Subscribe
	public void getPowerEvent(GetSystemPowerEvent event)
	{
		AbstractShipSystem system = event.ship.getSystem(event.system);
		
		Room room = system.getRoom();
		if(room != null)
		{
			Set<Crew> crew = room.getCrew();
			for(Crew member : crew)
			{
				if(member.getRace().equals("Zoltan"))
				{
					// TODO: What if max power is 1 and there are 2 zoltans; calc additional power in loop then check to make sure it is not more than max power
					// TODO: What if the system was at max power and a zoltan walks in; perhaps should be taken care of in the system, if power is greater than max, remove curr-max power
					event.power++;
				}
			}
		}
	}
}

package com.rebelkeithy.ftl.systems;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.rebelkeithy.ftl.crew.Crew;
import com.rebelkeithy.ftl.ship.Door;
import com.rebelkeithy.ftl.ship.Room;
import com.rebelkeithy.ftl.ship.Ship;

public class OxygenSystem extends AbstractShipSystem 
{

	public OxygenSystem(Ship ship, String name, int maxPower) 
	{
		super(ship, name, maxPower);
	}
	
	public String getDisplayName() { return "Oxygen"; }
	
	public String getDescription() 
	{ 
		return "Refills the oxygen in the ship. Upgrading\nincreases the rate of refill"; 
	}
	
	public String getUpgradeDescription(int level)
	{
		switch(level)
		{
		case 0:
			return "O2 Refill Boost: 1x";
		case 1:
			return "O2 Refill Boost: 3x";
		case 2:
			return "O2 Refill Boost: 6x";
		}
		return "";
	}

	@Override
	public void update(double dt)
	{
		super.update(dt);
		
		double oxyChange = 0;
		
		if(getPower() <= 0)
			oxyChange = -1;
		if(getPower() == 1)
			oxyChange = 1;
		if(getPower() == 2)
			oxyChange = 3;
		if(getPower() == 3)
			oxyChange = 6;
		
		Map<String, Room> rooms = getShip().getRooms();

		// TODO: make oxygen propagate from room to room
		for(Room room : rooms.values())
		{
			double oxygen = 100;
			if(room.getProperties().containsValue("oxygen"))
				oxygen = room.getProperties().getDouble("oxygen");
			oxygen += oxyChange * dt;
			if(oxygen < 0)
				oxygen = 0;
			if(oxygen > 100)
				oxygen = 100;
			room.getProperties().setDouble("oxygen", oxygen);
			if(oxygen < 5)
			{
				Set<Crew> crew = room.getCrew();	
				for(Crew member : crew)
				{
					member.damage(5*dt, "Oxygen");
				}
			}
		}
		
		double flowThroughDoor = 10;
		Map<Room, Double> changes = new HashMap<Room, Double>();
		for(Room room : rooms.values())
		{
			for(Door door : room.getDoors())
			{
				double oxy1 = room.getProperties().getDouble("oxygen");
				if(door.isOpen())
				{
					if(door.room2 == null)
					{
						double change = 0;
						if(changes.containsKey(room))
							change = changes.get(room);
						changes.put(room, change-50);
					}
					else
					{
						double oxyDiff = oxy1 - door.room2.getProperties().getDouble("oxygen");
						if(oxyDiff > 0)
						{
							if(oxyDiff > flowThroughDoor)
								oxyDiff = flowThroughDoor;

							
							if(changes.containsKey(room))
								changes.put(room, -oxyDiff + changes.get(room));
							else
								changes.put(room, -oxyDiff);

							/*
							if(changes.containsKey(door.room2))
								changes.put(room, oxyDiff + changes.get(door.room2));
							else
								changes.put(room, oxyDiff);*/
						}
					}
				}
			}
		}
		
		for(Room room : changes.keySet())
		{
			double oxygen = room.getProperties().getDouble("oxygen");
			oxygen += (changes.get(room) * dt);
			if(oxygen > 100)
				oxygen = 100;
			if(oxygen < 0)
				oxygen = 0;
			room.getProperties().setDouble("oxygen", oxygen);
		}
	}
	
	public boolean canMann()
	{
		return false;
	}
}

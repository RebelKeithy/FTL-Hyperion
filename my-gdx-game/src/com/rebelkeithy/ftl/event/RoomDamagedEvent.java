package com.rebelkeithy.ftl.event;

import java.util.Map;

import com.rebelkeithy.ftl.ship.Room;
import com.rebelkeithy.ftl.ship.Ship;

public class RoomDamagedEvent extends Event
{
	public Ship ship;
	public Room room;
	public Map<String, Integer> damages;
	
	public RoomDamagedEvent(Ship ship, Room room, Map<String, Integer> damages)
	{
		this.ship = ship;
		this.room = room;
		this.damages = damages;
	}
}

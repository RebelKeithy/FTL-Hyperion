package com.rebelkeithy.ftl.event;

import com.rebelkeithy.ftl.projectile.Projectile;
import com.rebelkeithy.ftl.ship.Room;
import com.rebelkeithy.ftl.ship.Ship;

public class HitRoomEvent 
{
	public Projectile projectile;
	public Ship target;
	public Ship source;
	public Room room;
	
	public int damage;
	
	public HitRoomEvent(Projectile projectile, Ship target, Ship source, Room room) 
	{
		this.projectile = projectile;
		this.target = target;
		this.source = source;
		this.room = room;
	}
}

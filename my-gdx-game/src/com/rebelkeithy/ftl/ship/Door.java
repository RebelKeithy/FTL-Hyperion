package com.rebelkeithy.ftl.ship;

import com.rebelkeithy.ftl.crew.Crew;
import com.rebelkeithy.ftl.event.DoorAttackEvent;
import com.rebelkeithy.ftl.event.DoorBreakEvent;
import com.rebelkeithy.ftl.event.DoorUnlockEvent;


public class Door 
{
	public int direction;
	public int offset;
	
	// Room1 is never null
	public Room room1;
	// Room2 may be null if it leads out an airlock
	public Room room2;
	public Door link;
	
	private double x;
	private double y;
	
	private double health = 100;
	private double maxHealth = 100;
	
	// Trackes how many people are walking through the door, when it reaches 0 the door closes
	public int users;
	public boolean open;
	public boolean forceOpen; // Used when the player manually opens doors;
	
	public Door(int direction, int offset, Room room1, Room room2)
	{
		this.direction = direction;
		this.offset = offset;
		this.room1 = room1;
		this.room2 = room2;
		
		if(direction == Direction.UP)
		{
			y = room1.getY() + room1.getHeight();
			x = room1.getX() + offset;
		}
		if(direction == Direction.DOWN)
		{
			y = room1.getY();
			x = room1.getX() + offset;
		}
		if(direction == Direction.LEFT)
		{
			y = room1.getY() + offset;
			x = room1.getX();
		}
		if(direction == Direction.RIGHT)
		{
			y = room1.getY() + offset;
			x = room1.getX() + room1.getWidth();
		}
	}
	
	public void link(Door link)
	{
		this.link = link;
	}
	
	public Door getLink()
	{
		return link;
	}

	// Returns the absolute x position of the door
	public double getX() 
	{
		return x;
	}
	
	// Returns the absolute y position of the door
	public double getY()
	{
		return y;
	}

	public Room nextRoom(Room room) 
	{
		if(room == room1)
			return room2;
		else
			return room1;
	}
	
	public String toString()
	{
		String n1 = null;
		String n2 = null;
		if(room1 != null)
			n1 = room1.getName();
		if(room2 != null)
			n2 = room2.getName();
			
		return "(" + n1 + "/" + n2 + ")";
	}
	
	public void update(double dt)
	{
		if(health < maxHealth)
		{
			health += dt * 10;
			if(health > maxHealth)
				health = maxHealth;
		}
		
		if(health == maxHealth && users == 0)
		{
			open = false;
		}
	}
	
	public boolean unlock(Crew crew)
	{
		if(forceOpen)
			return true;
		
		DoorUnlockEvent event = new DoorUnlockEvent(this, crew);
		room1.getShip().EVENT_BUS.post(event);
		
		if(!event.cancel)
			open = true;
		
		return open;
	}
	
	public void addUser(Crew crew)
	{
		users++;
	}
	
	public void removeUser(Crew crew)
	{
		users--;
		
		if(users == 0)
		{
			open = false;
		}
	}

	public void attack(Crew crew)
	{
		DoorAttackEvent attackEvent = new DoorAttackEvent(this, crew, crew.getAttack());
		room1.getShip().EVENT_BUS.post(attackEvent);
		
		if(!attackEvent.cancel)
			health -= attackEvent.attack;
		
		if(health <= 0)
		{
			health = 0;

			DoorBreakEvent event = new DoorBreakEvent(this);
			room1.getShip().EVENT_BUS.post(event);
			
			if(!event.cancel)
			{
				open = true;
			}
		}
	}

	public boolean isOpen() 
	{
		return forceOpen;
	}
}

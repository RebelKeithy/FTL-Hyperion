package com.rebelkeithy.ftl.crew;

import java.util.HashMap;
import java.util.Map;

import com.rebelkeithy.ftl.Clock;
import com.rebelkeithy.ftl.FTLGame;
import com.rebelkeithy.ftl.event.CrewUpdateEvent;
import com.rebelkeithy.ftl.properties.Properties;
import com.rebelkeithy.ftl.ship.Room;
import com.rebelkeithy.ftl.ship.Tile;
import com.rebelkeithy.ftl.systems.AbstractShipSystem;

public class Crew 
{
	private String race;
	private String name;
	private String homeShip;
	
	private double health = 100;
	private double maxHealth = 100;
	
	private Room room;
	private double posX;
	private double posY;
	
	private double speed = 0.5;
	private double attack = 5;
	private double repair = 0.05;

	private long deathTimer;
	
	public int state = CrewStates.IDLE;
	
	private CrewPathfinder pathfinder;
	private Properties properties;
	
	public Crew(String race, String name)
	{
		this.name = name;
		this.race = race;
		
		maxHealth = 100;
		health = 100;
		
		pathfinder = new CrewPathfinder();
		properties = new Properties();
	}
	
	public void setHomeShip(String ship)
	{
		homeShip = ship;
	}
	
	public boolean addToRoom(Room room)
	{
		for(int x = 0; x < room.getWidth(); x++)
		{
			for(int y = 0; y < room.getHeight(); y++)
			{
				Tile tile = room.getTile(x, y);
				if(!tile.getProperties().containsValue("crew"))
				{
					setPosition(room, x, y);
					return true;
				}
			}
		}
		
		return false;
	}
	
	public void setPosition(Room room, int x, int y)
	{
		if(this.room != null)
		{
			this.room.removeCrew(this);
			this.room.getTile(x, y).getProperties().removeProperty("crew");
		}
		
		this.room = room;
		this.posX = room.getX() + x;
		this.posY = room.getY() + y;
		this.room.getTile(x, y).getProperties().setString("crew", getName());
		
		room.addCrew(this);
	}
	
	public void update(double dt) 
	{
		CrewUpdateEvent event = new CrewUpdateEvent(dt, this);
		if(homeShip != null)
			FTLGame.instance().getShip(homeShip).EVENT_BUS.post(event);
		
		if(state == CrewStates.WALKING)
		{
			pathfinder.update(dt, this);
			posX = pathfinder.getX();
			posY = pathfinder.getY();
			if(pathfinder.state == 0)
			{
				state = CrewStates.IDLE;
			}
		}
		else if(state == CrewStates.REPAIRING)
		{
			AbstractShipSystem system = room.getSystem();
			if(system != null)
			{
				system.repair(dt, this);
				if(system.getDamage() == 0)
				{
					state = CrewStates.IDLE;
				}
			}
		}
		else if(state == CrewStates.MANNING)
		{
			AbstractShipSystem system = room.getSystem();
			if(system.getDamage() > 0)
			{
				system.unmann();
				state = CrewStates.REPAIRING;
			}
			else if(!system.isPowered())
			{
				system.unmann();
				state = CrewStates.IDLE;
			}
		}
		else if(state == CrewStates.FIGHTING)
		{
			// TODO: Fight
		}
		else if(state == CrewStates.IDLE)
		{
			AbstractShipSystem system = room.getSystem();
			//System.out.println(name + " " + system.isPowered());
			if(system != null && system.getDamage() > 0)
			{
				state = CrewStates.REPAIRING;
			}
			else if(system != null && system.canMann() && !system.isManned() && system.isPowered())
			{
				int stationX = system.getStationX();
				int stationY = system.getStationY();

				if(stationX + getRoom().getX() == getX() && stationY + getRoom().getY() == getY())
				{
					system.mann(this);
					state = CrewStates.MANNING;
				}
				else
				{
					if(!room.getTile(stationX, stationY).getProperties().containsValue("crew"))
						move(getRoom(), stationX, stationY);
				}
			}
			
		}
	}
	
	public void move(Room target)
	{
		boolean removeFromTile = false;
		if(this.state != CrewStates.WALKING)
		{
			removeFromTile = true;
		}

		Tile targetTile = pathfinder.setPath(this, target);

		if(targetTile != null)
		{
			if(removeFromTile)
			{
				System.out.println("removing");
				room.getTile((int)getX() - getRoom().getX(), (int)getY() - getRoom().getY()).getProperties().removeProperty("crew");
			}
			
			if(state == CrewStates.MANNING)
			{
				room.getSystem().unmann();
			}
			state = CrewStates.WALKING;
		}
	}
	
	public void move(Room target, int x, int y)
	{
		System.out.println("state " + state);
		boolean removeFromTile = false;
		if(this.state != CrewStates.WALKING)
		{
			removeFromTile = true;
		}
		
		Tile tile = target.getTile(x, y);
		if(tile.getProperties().containsValue("crew"))
			return;
		
		Tile targetTile = pathfinder.setPath(this, target, x, y);
		
		if(targetTile != null)
		{
			if(removeFromTile)
			{
				System.out.println("removing");
				room.getTile((int)getX() - getRoom().getX(), (int)getY() - getRoom().getY()).getProperties().removeProperty("crew");
			}
			
			if(state == CrewStates.MANNING)
			{
				room.getSystem().unmann();
			}
			state = CrewStates.WALKING;
		}
	}
	
	public void damage(double amount, String source)
	{
		// TODO: post damage event
		
		health -= amount;
		if(health < 0)
		{
			health = 0;
			kill(source);
		}
		if(health > maxHealth) // possible if amount is negative, as with healing blast
		{
			health = maxHealth;
		}
	}
	
	public void heal(double amount, String source)
	{
		health += amount;
		
		if(health > maxHealth)
		{
			health = maxHealth;
		}
		if(health < 0)
		{
			kill(source);
		}
	}
	
	// Will be used to transition into the dying state for animation, then after the animation set dead = true
	public void kill(String source)
	{
		if(state != CrewStates.DYING)
		{
			Clock.log(name + " died by " + source + "!");
			state = CrewStates.DYING;
			deathTimer = System.currentTimeMillis();
		}
	}
	
	public long getTimeDead()
	{
		return System.currentTimeMillis() - deathTimer;
	}
	
	public void remove()
	{
		if(state == CrewStates.WALKING)
		{
			pathfinder.cancel(this);
		}
		else
		{
			room.getTile((int)getX() - getRoom().getX(), (int)getY() - getRoom().getY()).getProperties().removeProperty("crew");
		}
	}
	
	public boolean isDead()
	{
		return state == CrewStates.DYING && getTimeDead() > 2000;
	}
	
	public Properties getProperties()
	{
		return properties;
	}
	
	public boolean isMoving()
	{
		return pathfinder.getState() != pathfinder.IDLE;
	}

	public String getName() 
	{
		return name;
	}

	public double getX() 
	{
		return posX;
	}

	public double getY() 
	{
		return posY;
	}

	public double getSpeed() 
	{
		return speed;
	}
	
	public void setRoom(Room room)
	{
		this.room = room;
	}

	public Room getRoom() 
	{
		return room;
	}

	public double getAttack() 
	{
		return attack;
	}
	
	public String getCurrentShip()
	{
		return room.getShip().getName();
	}

	public String getHomeShip() 
	{
		return homeShip;
	}

	public double repairSpeed() 
	{
		return repair;
	}

	public void setMaxHelth(int maxHealth) 
	{
		this.maxHealth = maxHealth;
	}

	public void setAttack(double attack) 
	{
		this.attack = attack;
	}

	public void setRepair(double repair) 
	{
		this.repair = repair;
	}

	public void setSpeed(double speed) 
	{
		this.speed = speed;
	}

	public String getRace() 
	{
		return race;
	}

	public void setHealth(int health) 
	{
		this.health = health;
		if(this.health > maxHealth)
			this.health = maxHealth;
	}

	public double getHealth() 
	{
		return health;
	}
	
	public double getMaxHealth()
	{
		return maxHealth;
	}

	public int getTargetX() 
	{
		return pathfinder.targetX();
	}

	public int getTargetY() 
	{
		return pathfinder.targetY();
	}
}

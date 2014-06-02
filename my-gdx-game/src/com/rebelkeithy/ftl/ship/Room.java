package com.rebelkeithy.ftl.ship;

import java.util.HashSet;
import java.util.Set;

import com.rebelkeithy.ftl.Clock;
import com.rebelkeithy.ftl.crew.Crew;
import com.rebelkeithy.ftl.event.RoomDamagedEvent;
import com.rebelkeithy.ftl.projectile.Bomb;
import com.rebelkeithy.ftl.projectile.Projectile;
import com.rebelkeithy.ftl.properties.Properties;
import com.rebelkeithy.ftl.systems.CommandSystem;
import com.rebelkeithy.ftl.systems.AbstractShipSystem;

public class Room 
{
	private Ship ship;
	private AbstractShipSystem system;
	
	private int x;
	private int y;
	private int width;
	private int height;
	
	private Tile[][] tiles;
	private Set<Door> doors;
	private Set<Crew> crew;
	private String name;
	
	private Properties properties;
	private String texture;
	
	public Room(Ship ship, String name, int x, int y, int width, int height)
	{
		this.ship = ship;
		this.name = name;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		tiles = new Tile[width][height];
		
		for(int i = 0; i < width; i++)
		{
			for(int j = 0; j < height; j++)
			{
				tiles[i][j] = new Tile(this, i, j);
			}
		}
		
		doors = new HashSet<Door>();
		crew = new HashSet<Crew>();
		
		properties = new Properties();
	}
	
	public Properties getProperties()
	{
		return properties;
	}
	
	public Tile getTile(int x, int y)
	{
		return tiles[x][y];
	}
	
	public void addSystem(AbstractShipSystem system)
	{
		this.system = system;
	}
	
	public void hit(Projectile projectile)
	{
		//EventHitRoom event = new EventHitRoom(projectile, ship, projectile.getSource(), this);
		if(!ship.isDead())
		{
			boolean dodged = ((CommandSystem)ship.getSystem("command")).dodgeProjectile(projectile);
			if(dodged)
				return;
			
			if(projectile.causesDamage("system") && system != null)
				system.damage(projectile.getDamage("system"));
			
			if(projectile.causesDamage("hull"))
				ship.damageHull(projectile.getDamage("hull"));
			
			RoomDamagedEvent event = new RoomDamagedEvent(ship, this, projectile.getDamages());
			ship.EVENT_BUS.post(event);

			Clock.log(ship.getName() + " room hit, hull at " + ship.getHull());
		}
		projectile.kill();

	}
	
	public void damage(Bomb bomb) 
	{
		if(bomb.causesDamage("system") && system != null)
			system.damage(bomb.getDamage("system"));
		
		if(bomb.causesDamage("hull"))
			ship.damageHull(bomb.getDamage("hull"));
		
		Clock.log(ship.getName() + " room bombed, hull at " + ship.getHull());
	}

	public void addDoor(Door door) 
	{
		doors.add(door);
	}

	public Set<Door> getDoors() 
	{
		return doors;
	}

	public int getHeight() 
	{
		return height;
	}

	public int getWidth() 
	{
		return width;
	}

	public int getX() 
	{
		return x;
	}

	public int getY() 
	{
		return y;
	}

	public String getName() 
	{
		return name;
	}
	
	public Ship getShip()
	{
		return ship;
	}

	public AbstractShipSystem getSystem() 
	{
		return system;
	}
	
	public void addCrew(Crew crew)
	{
		this.crew.add(crew);
	}
	
	public void removeCrew(Crew crew)
	{
		this.crew.remove(crew);
	}
	
	public Crew getCrew(String crewName)
	{
		for(Crew member : crew)
		{
			if(member.getName().equals(crewName))
			{
				return member;
			}
		}
		
		return null;
	}

	public Set<Crew> getCrew() 
	{
		return crew;
	}

	public void setTexture(String texture) 
	{
		this.texture = texture;
	}
	
	public String getTexture()
	{
		return texture;
	}
}

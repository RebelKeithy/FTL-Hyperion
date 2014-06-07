package com.rebelkeithy.ftl.ship;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.eventbus.EventBus;
import com.rebelkeithy.ftl.Clock;
import com.rebelkeithy.ftl.FTLGame;
import com.rebelkeithy.ftl.Main;
import com.rebelkeithy.ftl.crew.Crew;
import com.rebelkeithy.ftl.event.FTLChargeEvent;
import com.rebelkeithy.ftl.event.ShipCreationEvent;
import com.rebelkeithy.ftl.event.ShipDestroyedEvent;
import com.rebelkeithy.ftl.event.ShipUpdateEvent;
import com.rebelkeithy.ftl.projectile.Projectile;
import com.rebelkeithy.ftl.properties.Properties;
import com.rebelkeithy.ftl.systems.AbstractShipSystem;
import com.rebelkeithy.ftl.systems.ShieldSystem;

public class Ship 
{
	public EventBus EVENT_BUS;
	
	private FTLGame game;
	private int sector;
	private int star;
	
	private String name;
	private int maxHull;
	private int hull;
	
	public Map<String, AbstractShipSystem> systems;
	Map<String, Room> rooms;
	List<Crew> crew;
	List<Projectile> shots;
	private Properties properties;
	
	private double ftlDriveCharge;
	
	// Federation, Rebel, Pirate, etc
	public int allignment;

	private boolean isPlayer;
	
	public ShipRenderData renderData;
	
	public Ship()
	{
		EVENT_BUS = new EventBus();
		
		systems = new HashMap<String, AbstractShipSystem>();
		rooms = new HashMap<String, Room>();
		crew = new ArrayList<Crew>();
		shots = new ArrayList<Projectile>();
		properties = new Properties();

		maxHull = 30;
		hull = maxHull;
		
		renderData = new ShipRenderData();
		
		ShipCreationEvent event = new ShipCreationEvent(this);
		Main.GLOBAL_BUS.post(event);
	}
	
	public void setPosition(int sector, int star)
	{
		this.sector = sector;
		this.star = star;
	}
	
	public void setPlayer(boolean value)
	{
		if(value == true)
			FTLGame.instance().setPlayer(this);
		isPlayer = value;
	}
	
	public boolean isPlayer() 
	{
		return isPlayer;
	}

	public void jump(int star)
	{
		if(ftlDriveCharge == 1)
		{
			game.shipJump(this, star);
		}
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void update(double dt)
	{
		ShipUpdateEvent event = new ShipUpdateEvent(this, dt);
		EVENT_BUS.post(event);
		
		for(AbstractShipSystem system : systems.values())
		{
			if(system != null)
				system.update(dt);
		}
		
		for(Crew crewMember : crew)
		{
			crewMember.update(dt);
		}
		
		for(int i = 0; i < crew.size(); i++)
		{
			if(crew.get(i).isDead())
			{
				crew.get(i).remove();
				crew.remove(i);
				i--;
			}
		}
		
		this.chargeFtlDrive(dt);
	}
	
	public int getShieldLvl()
	{
		ShieldSystem shields = (ShieldSystem)systems.get("shields");
		
		if(shields != null)
		{
			return shields.getShieldLvl();
		}
		
		return 0;
	}
	
	public void hitShields(Projectile projectile)
	{
		ShieldSystem shields = (ShieldSystem)systems.get("shields");
		
		if(shields.getShieldLvl() > 0)
		{
			shields.hitShields(projectile);
		}
		else
		{
			System.out.println("no shields to protect from projectile");
		}
	}
	
	public void chargeFtlDrive(double dt)
	{
		if(ftlDriveCharge < 1 && getSystem("pilot").isManned())
		{
			FTLChargeEvent event = new FTLChargeEvent(0.01);
			EVENT_BUS.post(event);
			
			if(!event.cancel)
			{
				ftlDriveCharge += dt * event.chargeRate;
			}
			
			if(ftlDriveCharge >= 1)
			{
				ftlDriveCharge = 1;
				Clock.log(getName() + " FTL Drive is charged");
			}
		}
	}
	
	public Map<String, Room> getRooms()
	{
		return rooms;
	}

	public FTLGame getGame() 
	{
		return game;	
	}

	public void setGame(FTLGame game) 
	{
		this.game = game;
	}

	public void damageHull(int damage) 
	{
		Clock.log(getName() + " hull damaged " + damage);
		this.hull -= damage;
		
		if(hull < 0)
			hull = 0;
		
		if(hull == 0)
		{
			ShipDestroyedEvent event = new ShipDestroyedEvent(this);
			EVENT_BUS.post(event);
			
			if(!event.cancel)
			{
				kill();
			}
		}
	}
	
	public void kill()
	{
		Clock.log(getName() + " was destroyed");
	}
	
	public boolean isDead()
	{
		return hull <= 0;
	}

	public void addRoom(String roomName, Room room) 
	{
		rooms.put(roomName, room);
	}
	
	public Room getRoom(String name)
	{
		return rooms.get(name);
	}
	
	public AbstractShipSystem getSystem(String name)
	{
		return systems.get(name);
	}

	public Collection<AbstractShipSystem> getSystems() 
	{
		return systems.values();
	}

	public int getHull() 
	{
		return hull;
	}

	void addSystem(String name, AbstractShipSystem system) 
	{
		systems.put(name, system);	
	}

	public void addSystem(String name, AbstractShipSystem system, String room) 
	{
		systems.put(name, system);	
		getRoom(room).addSystem(system);
		system.setRoom(getRoom(room));
	}

	public void addCrew(Crew crew1) 
	{
		crew.add(crew1);
	}

	public List<Crew> getCrew() 
	{
		return crew;
	}

	public Crew getCrew(String name) 
	{
		for(Crew member : crew)
		{
			if(member.getName().equals(name))
			{
				return member;
			}
		}
		
		return null;
	}
	
	// Only used for text based gui
	public void examine()
	{
		System.out.println("*** " + name + " ***");
		System.out.println("Hull: " + hull + "/" + maxHull);
		System.out.println("Systems");
		for(AbstractShipSystem system : systems.values())
		{
			system.examine();
		}
		System.out.println("--------------------");
		
		System.out.println("********************");
	}
	
	public int getSector()
	{
		return sector;
	}

	public int getStar() 
	{
		return star;
	}

	public boolean isFTLCharged() 
	{
		return (ftlDriveCharge >= 1);
	}

	public double getFTLCharge() 
	{
		return ftlDriveCharge;
	}

	public Properties getProperties() 
	{
		return properties;
	}
}

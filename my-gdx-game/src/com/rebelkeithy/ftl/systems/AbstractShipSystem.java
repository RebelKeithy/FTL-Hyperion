package com.rebelkeithy.ftl.systems;

import com.rebelkeithy.ftl.Clock;
import com.rebelkeithy.ftl.crew.Crew;
import com.rebelkeithy.ftl.event.GetSystemPowerEvent;
import com.rebelkeithy.ftl.event.SystemRepairEvent;
import com.rebelkeithy.ftl.properties.Properties;
import com.rebelkeithy.ftl.ship.Room;
import com.rebelkeithy.ftl.ship.Ship;
import com.rebelkeithy.ftl.view.SystemRenderer;

public abstract class AbstractShipSystem 
{
	private Ship ship;
	private String name;
	private Properties properties;
	
	private int maxPower;
	protected int currPower;
	private int damage;
	private double repairAmount;
	private int[] cost;
	
	private Room room;

	private boolean alwaysPowered;
	
	private int ionDamage;
	private double ionTimer;
	
	private Station station;
	
	public AbstractShipSystem(Ship ship, String name, int maxPower)
	{
		this.ship = ship;
		this.name = name;
		this.properties = new Properties();
		this.maxPower = maxPower;
		currPower = 0;
		
		ship.EVENT_BUS.register(this);
	}
	
	public String getDisplayName() { return getName(); }
	
	public String getDescription() { return ""; }
	
	public String getUpgradeDescription(int level)
	{
		return "";
	}
	
	public void setCost(int[] costs)
	{
		this.cost = costs;
	}
	
	public int getMaxUpgradeLevel()
	{
		return cost.length;
	}
	
	public int getUpgradeCost(int i)
	{
		if(i < cost.length)
			return cost[i];
		else
			return -1;
	}
	
	public int getNextUpgradeCost()
	{
		return cost[getMaxPower()];
	}
	
	public Properties getProperties()
	{
		return properties;
	}
	
	public void setRoom(Room room)
	{
		this.room = room;
	}
	
	public void update(double dt)
	{
		if(ionTimer > 0)
		{
			ionTimer -= dt;
			
			if(ionTimer <= 0)
			{
				ionTimer = 0;
				ionDamage = 0;
			}
		}
	}
	
	public Ship getShip()
	{
		return ship;
	}
	
	public boolean canMann()
	{
		return station != null;
	}
	
	public void mann(Crew crew)
	{
		station.manning = crew;
	}
	
	public Crew getManning()
	{
		if(station != null)
			return station.manning;
		
		return null;
	}

	public boolean isManned() 
	{
		return station != null && station.manning != null;
	}
	
	public void unmann()
	{
		station.manning = null;
	}

	protected void setAlwaysPowered(boolean value) 
	{
		alwaysPowered = value;
	}

	
	/**
	 * @param sets the power used by the systems to the power specified. If the specified power
	 * is greater than the maximum power, it is set to the maximum
	 * @return Returns the amount of power not used
	 */
	public int setPower(int power)
	{
		if(alwaysPowered)
			return power;
		
		if(power > (maxPower - damage))
		{
			currPower = (maxPower - damage);
			return power - currPower;
		}
		
		currPower = power;
		return 0;
	}
	
	public void addPower(int amount)
	{
		if(alwaysPowered)
			return;
		
		if(currPower + amount > (maxPower - damage))
		{
			amount = (maxPower - damage) - currPower;
		}
		
		if(currPower + amount < 0)
		{
			amount = -currPower;
		}
		
		amount = ((ReactorSystem)ship.getSystem("reactor")).takePower(amount);
		
		currPower += amount;
	}
	
	public int getPower()
	{
		int power = currPower - damage;
		
		if(alwaysPowered)
			power = maxPower - damage;
			
		GetSystemPowerEvent event = new GetSystemPowerEvent(ship, name, power);
		ship.EVENT_BUS.post(event);
		return event.power;
	}
	
	public boolean alwaysPowered()
	{
		return alwaysPowered;
	}
	
	public int getMaxPower()
	{
		return maxPower;
	}
	
	public void upgrade()
	{
		maxPower++;
	}
	
	public int getDamage()
	{
		return damage;
	}
	
	public void damage(int amount)
	{
		this.damage += amount;
		if(damage > maxPower)
			damage = maxPower;
		
		if(ionDamage > maxPower - damage)
			ionDamage = maxPower - damage;
		
		if(currPower > (maxPower - damage))
			currPower = maxPower - damage;
		
		Clock.log(getShip().getName() + " " + this.getClass().getSimpleName() + " damaged, damage " + damage);
	}
	
	public void repair(double dt, Crew crew)
	{
		if(damage == 0)
			return;
		
		SystemRepairEvent event = new SystemRepairEvent(this, crew, crew.repairSpeed());  // Could be used 
		getShip().EVENT_BUS.post(event);
		double repair = crew.repairSpeed();
		
		if(!event.cancel)
		{
			repairAmount += repair * dt;
			
			if(repairAmount > 1)
			{
				repairAmount = 0;
				damage--;
			}
		}
	}
	
	public double getRepairAmount()
	{
		return repairAmount;
	}

	public Room getRoom() 
	{
		return room;
	}

	public String getName() 
	{
		return name;
	}

	public void examine() 
	{
		System.out.println("Name: " + name);
		System.out.println("Power: " + getPower() + "/" + getMaxPower());
		System.out.println("Damage: " + damage);
	}
	
	public SystemRenderer getSystemRenderer()
	{
		return new SystemRenderer(this, 0);
	}

	public boolean isPowered() 
	{
		return getPower() > 0;
	}

	public void addStation(int stationX, int stationY, int dir) 
	{
		station = new Station();
		station.x = stationX;
		station.y = stationY;
		station.dir = dir;
	}
	
	public int getStationX()
	{
		return station.x;
	}
	
	public int getStationY()
	{
		return station.y;
	}
	
	public int getStationDir()
	{
		return station.dir;
	}
}

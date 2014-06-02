package com.rebelkeithy.ftl;

import com.google.common.eventbus.Subscribe;
import com.rebelkeithy.ftl.event.HitShieldsEvent;
import com.rebelkeithy.ftl.event.GetSystemPowerEvent;
import com.rebelkeithy.ftl.event.RoomDamagedEvent;
import com.rebelkeithy.ftl.event.ShipCreationEvent;
import com.rebelkeithy.ftl.event.ShipUpdateEvent;
import com.rebelkeithy.ftl.properties.Properties;
import com.rebelkeithy.ftl.systems.AbstractShipSystem;

public class IonHandler 
{
	public static final int ionTime = 5;
	
	public IonHandler()
	{
		Main.GLOBAL_BUS.register(this);
	}
	
	@Subscribe
	public void shipCreation(ShipCreationEvent event)
	{
		event.ship.EVENT_BUS.register(this);
	}
	
	@Subscribe
	public void getSystemPower(GetSystemPowerEvent event)
	{
		AbstractShipSystem system = event.ship.getSystem(event.system);
		int ionDamage = system.getProperties().getInteger("ionDamage");
		event.power -= ionDamage;
		if(event.power < 0)
			event.power = 0;
	}
	
	@Subscribe
	public void shipUpdate(ShipUpdateEvent event)
	{
		for(AbstractShipSystem system : event.ship.getSystems())
		{
			double ionTimer = system.getProperties().getDouble("ionTimer");
			if(ionTimer > 0)
			{
				ionTimer -= event.dt;
				if(ionTimer <= 0)
				{
					ionTimer = 0;
					system.getProperties().setInteger("ionDamage", 0);
				}
				system.getProperties().setDouble("ionTimer", ionTimer);
			}
		}
	}
	
	@Subscribe
	public void roomDamagedEvent(RoomDamagedEvent event)
	{
		if(event.damages.containsKey("ion"))
		{
			int damage = event.damages.get("ion");

			if(event.room.getSystem() != null)
				addIonDamage(event.room.getSystem(), damage);
		}
	}
	
	@Subscribe
	public void shieldHitEvent(HitShieldsEvent event)
	{
		if(event.projectile.causesDamage("ion"))
		{
			event.shieldDamage = event.projectile.getDamage("ion");
			
			addIonDamage(event.target.getSystem("shields"), event.projectile.getDamage("ion"));
		}
	}
	
	public void addIonDamage(AbstractShipSystem system, int damage)
	{		
		Properties prop = system.getProperties();
		
		int currIonDamage = prop.getInteger("ionDamage");
		double ionTimer = prop.getDouble("ionTimer");
		currIonDamage += damage;
		ionTimer += ionTime * damage;
		if(ionTimer > 25)
			ionTimer = 25;
		prop.setInteger("ionDamage", currIonDamage);
		prop.setDouble("ionTimer", ionTimer);
		system.update(0);
		
		Clock.log(system.getShip().getName() + " " + system.getName() + " ion damage " + currIonDamage + ", timer " + ionTimer);
	}
}

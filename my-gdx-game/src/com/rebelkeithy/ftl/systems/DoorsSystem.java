package com.rebelkeithy.ftl.systems;

import com.google.common.eventbus.Subscribe;
import com.rebelkeithy.ftl.event.DoorAttackEvent;
import com.rebelkeithy.ftl.event.DoorUnlockEvent;
import com.rebelkeithy.ftl.ship.Ship;
import com.rebelkeithy.ftl.view.DoorSystemRenderer;
import com.rebelkeithy.ftl.view.SystemRenderer;

public class DoorsSystem extends AbstractShipSystem 
{

	public DoorsSystem(Ship ship, String name, int maxPower) 
	{
		super(ship, name, maxPower);
		
		setAlwaysPowered(true);
	}
	
	@Subscribe
	public void unlockDoorEvent(DoorUnlockEvent event)
	{
		// TODO: this should check if the two ships are hostile, not equal
		if(!event.getCrew().getHomeShip().equals(getShip().getName()))
		{
			if(this.getPower() > 0)
				event.cancel  = true;
		}
	}
	
	public String getDisplayName() { return "Door System"; }
	
	public String getDescription() 
	{ 
		return "Allows remote opening and closing of doors.\nUpgrades to Blast Doors that imede fire\nspread and intruder movement."; 
	}
	
	public String getUpgradeDescription(int level)
	{
		switch(level)
		{
		case 0:
			return "Normal doors";
		case 1:
			return "Blast Doors";
		case 2:
			return "Improved Blast Doors";
		case 3:
			return "Super Blast Doors";
		}
		return "";
	}
	
	@Subscribe
	public void doorAttackEvent(DoorAttackEvent event)
	{
		if(getPower() > 0)
		{
			event.attack /= getPower();
		}
	}
	
	@Override
	public int getPower()
	{
		if(isManned())
			return super.getPower() + 1;
		else
			return super.getPower();
	}

	@Override
	public SystemRenderer getSystemRenderer()
	{
		return new DoorSystemRenderer(this, 0);
	}
}

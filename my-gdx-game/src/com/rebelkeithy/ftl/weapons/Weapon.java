package com.rebelkeithy.ftl.weapons;

import com.rebelkeithy.ftl.Clock;
import com.rebelkeithy.ftl.event.WeaponChargeEvent;
import com.rebelkeithy.ftl.ship.Ship;

public abstract class Weapon 
{
	private String name;
	private int requiredPower;
	private boolean powered;
	
	private double chargeTime;
	private double currentCharge;
	private Ship ship;
	
	public Weapon(String name, int requiredPower, double chargeTime)
	{
		this.name = name;
		this.requiredPower = requiredPower;
		this.chargeTime = chargeTime;
	}
	
	public void setShip(Ship ship)
	{
		this.ship = ship;
	}
	
	public Ship getShip()
	{
		return ship;
	}

	public int requiredPower() 
	{
		return requiredPower;
	}
	
	public boolean isPowered()
	{
		return powered;
	}
	
	public void setPowered(boolean value)
	{
		powered = value;
	}

	public void update(double dt) 
	{
		if(isPowered())
		{
			if(currentCharge < chargeTime)
			{
				WeaponChargeEvent event = new WeaponChargeEvent(getShip().getName(), dt, currentCharge, chargeTime);
				getShip().EVENT_BUS.post(event);
				double chargeRate = event.chargeRate;
				
				if(!event.cancel)
				{
					currentCharge += chargeRate;
				
					if(currentCharge >= chargeTime)
					{
						Clock.log(ship.getName() + " " + getName() + " Charged");
						currentCharge = chargeTime;
					}
				}
			}
			else
			{
				activate();
			}
		}
		else
		{
			if(currentCharge > 0)
			{
				// TODO: Need an event for this, possible augment that stops weapons from loosing charge when unpowered
				currentCharge -= dt*5;
				if(currentCharge < 0)
					currentCharge = 0;
			}
		}
	}
	
	public String getName()
	{
		return name;
	}
	
	public boolean isCharged()
	{
		return currentCharge == chargeTime;
	}
	
	public void removeCharge()
	{
		currentCharge = 0;
	}
	
	public abstract void setTarget(String ship, String room);

	public abstract void activate();

	public double getCharge() 
	{
		return currentCharge;
	}
	public double getMaxCharge() 
	{
		return chargeTime;
	}

	public double getChargePercentage() 
	{
		return currentCharge/chargeTime;
	}
}

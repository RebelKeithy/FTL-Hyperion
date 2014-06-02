package com.rebelkeithy.ftl.systems;

import com.rebelkeithy.ftl.Clock;
import com.rebelkeithy.ftl.FTLGame;
import com.rebelkeithy.ftl.event.HitShieldsEvent;
import com.rebelkeithy.ftl.event.ShieldChargeEvent;
import com.rebelkeithy.ftl.projectile.Projectile;
import com.rebelkeithy.ftl.ship.Ship;

public class ShieldSystem extends AbstractShipSystem
{
	
	private int currShield;
	private double shieldCharge;
	private double chargeTime = 2;
	
	private double chargeDelay;
	private double delayTime = 2;
	
	public ShieldSystem(Ship ship, String name, int maxPower)
	{
		super(ship, name, maxPower);
		shieldCharge = 0;
	}
	
	public String getDisplayName() { return "Shields"; }
	
	public String getDescription() 
	{ 
		return "Powers your shields. Each additional\nbarrier can block one shot"; 
	}
	
	public String getUpgradeDescription(int level)
	{
		switch(level)
		{
		case 1:
			return "One Shield Barrier";
		case 3:
			return "Two Shield Barriers";
		case 5:
			return "Three Shield Barriers";
		case 7:
			return "Four Shield Barriers";
		}
		return "";
	}
	
	@Override
	public void update(double dt)
	{
		chargeShields(dt);
	}
	
	public void chargeShields(double dt)
	{
		if(currShield > maxShields())
		{
			currShield = maxShields();
		}
		
		if(chargeDelay < delayTime)
		{
			chargeDelay += dt;
			
			if(chargeDelay > delayTime)
				chargeDelay = delayTime;
			
			return;
		}
		
		if(currShield == maxShields())
		{
			return;
		}		
		
		double chargeRate = dt;
		if(isManned())
		{
			chargeRate *= 1.10;
		}
		
		ShieldChargeEvent event = new ShieldChargeEvent(getShip().getName(), chargeRate, shieldCharge, chargeTime);
		getShip().EVENT_BUS.post(event);
		
		if(event.cancel)
			return;
		
		shieldCharge += event.chargeRate;
		
		if(shieldCharge >= chargeTime)
		{
			shieldCharge -= chargeTime;
			currShield++;
			Clock.log(getShip().getName() + " Shields at " + currShield + ", max is " + maxShields());
		}
		
		
		if(currShield == maxShields())
			shieldCharge = 0;
	}
	
	public void damage(int amount)
	{
		super.damage(amount);
		
		if(currShield > maxShields())
		{
			currShield = maxShields();
			Clock.log(getShip().getName() + " Shields at " + currShield + ", max = " + maxShields());
		}
	}
	
	public int maxShields()
	{
		return getPower()/2;
	}
	
	public int getShieldLvl()
	{
		return currShield;
	}
	
	public void hitShields(Projectile projectile)
	{			
		HitShieldsEvent event = new HitShieldsEvent(projectile, getShip(), FTLGame.instance().getShip(projectile.getSource()), 1);
		getShip().EVENT_BUS.post(event);
		
		if(!event.cancel)
		{
			currShield -= event.shieldDamage;
			chargeDelay = 0;
			
			if(currShield < 0)
				currShield = 0;
		
			projectile.kill();
			
			Clock.log(getShip().getName() + " Shields hit, now at " + currShield);
		}
	}

	public double getChargePercent() 
	{
		return shieldCharge/chargeTime;
	}
}

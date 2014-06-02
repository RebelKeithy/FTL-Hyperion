package com.rebelkeithy.ftl.systems;

import com.rebelkeithy.ftl.ship.Ship;

public class ReactorSystem extends AbstractShipSystem 
{
	
	
	public ReactorSystem(Ship ship, String name, int maxPower) 
	{
		super(ship, name, maxPower);
		
		currPower = maxPower;
	}
	
	public String getDisplayName() { return "Reactor"; }

	public String getDescription() 
	{ 
		return "Provides power to all ship systems. Each\npower bar allows you to send one unit of\npower to a system. Subsystems do not need\npower from the reactor"; 
	}
	
	public void upgrade()
	{
		super.upgrade();
		
		currPower++;
	}
	
	public int takePower(int amount)
	{
		if(amount < getPower())
		{
			currPower -= amount;
			return amount;
		}
		else
		{
			amount = currPower;
			currPower = 0;
			return amount;
		}
	}
}

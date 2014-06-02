package com.rebelkeithy.ftl.systems;

import com.rebelkeithy.ftl.ship.Ship;
import com.rebelkeithy.ftl.view.SubSystemRenderer;
import com.rebelkeithy.ftl.view.SystemRenderer;

public class SensorsSystem extends AbstractShipSystem 
{

	public SensorsSystem(Ship ship, String name, int maxPower) 
	{
		super(ship, name, maxPower);
		setAlwaysPowered(true);
	}
	
	public String getDisplayName() { return "Sensors"; }
	
	public String getDescription() 
	{ 
		return "Reveals the interior of your ship and give\ninformation abount enemy ships"; 
	}
	
	public String getUpgradeDescription(int level)
	{
		switch(level)
		{
		case 0:
			return "See ship interior";
		case 1:
			return "See enemy interior";
		case 2:
			return "See enemy weapon charge";
		case 3:
			return "See enemy power use";
		}
		return "";
	}

	@Override
	public SystemRenderer getSystemRenderer()
	{
		return new SubSystemRenderer(this, 0);
	}
}

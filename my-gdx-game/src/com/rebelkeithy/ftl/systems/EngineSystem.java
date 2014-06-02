package com.rebelkeithy.ftl.systems;

import com.google.common.eventbus.Subscribe;
import com.rebelkeithy.ftl.crew.Crew;
import com.rebelkeithy.ftl.crew.Skill;
import com.rebelkeithy.ftl.event.FTLChargeEvent;
import com.rebelkeithy.ftl.ship.Ship;

public class EngineSystem extends AbstractShipSystem
{
	public EngineSystem(Ship ship, String name, int maxPower)
	{
		super(ship, name, maxPower);
	}
	
	public String getDisplayName() { return "Engines"; }
	
	public String getDescription() 
	{ 
		return "Powers the FTL drive and allows the ship to\ndodge. Upgrading improves dodge chance\nand the rate that your FTL drive charges."; 
	}
	
	public String getUpgradeDescription(int level)
	{
		return "Dodge: " + (5 + level * 5) + " / FTL: " + (1 + level*0.25) + "x";
	}
	
	public double getDodge()
	{
		if(getPower() == 0)
			return 0;
		
		double dodge = getPower() * 5;
		
		if(getManning() != null)
		{
			Crew crew = getManning();
			double skill = Skill.getSkill("engines").getCurrentLevel(crew);
			
			if(skill >= 2)
				dodge += (5 + dodge)*0.15;
			else if(skill >= 1)
				dodge += (5 + dodge)*0.1;
			else
				dodge += 5;
		}
		
		return dodge;
	}
	
	@Subscribe
	public void ftlChargeEvent(FTLChargeEvent event)
	{
		if(getPower() > 0)
			event.chargeRate *= 1 + ((getPower() - 1) * 0.25);
		else
		{
			event.chargeRate = 0;
			event.cancel = true;
		}
	}
}

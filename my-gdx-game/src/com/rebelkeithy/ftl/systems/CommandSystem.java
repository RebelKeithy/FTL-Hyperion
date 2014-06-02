package com.rebelkeithy.ftl.systems;

import java.util.Random;

import com.google.common.eventbus.Subscribe;
import com.rebelkeithy.ftl.Clock;
import com.rebelkeithy.ftl.crew.Crew;
import com.rebelkeithy.ftl.crew.Skill;
import com.rebelkeithy.ftl.event.FTLChargeEvent;
import com.rebelkeithy.ftl.event.HitShieldsEvent;
import com.rebelkeithy.ftl.projectile.Projectile;
import com.rebelkeithy.ftl.ship.Ship;

public class CommandSystem extends AbstractShipSystem
{

	public CommandSystem(Ship ship, String name, int maxPower) 
	{
		super(ship, name, maxPower);
		setAlwaysPowered(true);
	}
	
	public void eventHitShields(HitShieldsEvent event)
	{
		if(dodgeProjectile(event.projectile))
		{
			event.cancel = true;
		}
	}
	
	public String getDisplayName() { return "Piloting"; }
	
	public String getDescription() 
	{ 
		return "Allows the ship to make FTL jumps and dodge/nwhen piloted. Upgrading adds auto-pilot that allows/nsome evasion even without a pilot"; 
	}
	
	public String getUpgradeDescription(int level)
	{
		switch(level)
		{
		case 0:
			return "Needs pilot to function";
		case 1:
			return "Auto: 50 percent evasion";
		case 2:
			return "Auto: 80 percent evasion";
		}
		return "";
	}
	
	public boolean dodgeProjectile(Projectile projectile)
	{
		Random rand = new Random(System.currentTimeMillis());

		if(rand.nextInt(100) < getDodge())
		{
			Clock.log(getShip().getName() + " dodged! (" + getDodge() + "%)");
			projectile.dodge();
		
			Crew crew = getManning();
			if(crew != null)
			{
				Skill.getSkill("piloting").addXP(crew, 1);
			}
			crew = getShip().getSystem("engines").getManning();
			if(crew != null)
			{
				Skill.getSkill("engines").addXP(crew, 1);
			}
			
			return true;
		}
		
		return false;
	}
	
	public double getDodge()
	{
		double dodge = 0;
		
		if(getManning() != null && getPower() > 0)
		{
			dodge = ((EngineSystem)getShip().getSystem("engines")).getDodge();

			Crew crew = getManning();
			double skill = Skill.getSkill("piloting").getCurrentLevel(crew);
			
			if(getShip().getSystem("engines").getPower() > 0)
			{
				if(skill >= 2)
					dodge += (5 + dodge)*0.15;
				else if(skill >= 1)
					dodge += (5 + dodge)*0.1;
				else
					dodge += 5;
			}
		}
		
		return dodge;
	}
	
	@Subscribe
	public void ftlChargeEvent(FTLChargeEvent event)
	{
		if((getPower() == 1 && isManned()) || getPower() > 1)
			return;
		else
			event.cancel = true;
		
	}
}

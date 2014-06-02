package com.rebelkeithy.ftl.systems;

import java.util.Set;

import com.rebelkeithy.ftl.crew.Crew;
import com.rebelkeithy.ftl.ship.Room;
import com.rebelkeithy.ftl.ship.Ship;

public class HealthSystem extends AbstractShipSystem 
{

	public HealthSystem(Ship ship, String name, int maxPower) 
	{
		super(ship, name, maxPower);
	}
	
	public String getDisplayName() { return "Medbay"; }
	
	public String getDescription() 
	{ 
		return "Heals all crew-members within the Medbay\nroom. Upgrading increases healing speed."; 
	}
	
	public String getUpgradeDescription(int level)
	{
		switch(level)
		{
		case 0:
			return "Healing Boost: 1x";
		case 1:
			return "Healing Boost: 1.5x";
		case 2:
			return "Healing Boost: 3x";
		}
		return "";
	}
	
	@Override
	public void update(double dt)
	{
		super.update(dt);
		
		if(getPower() > 0)
		{
			Room room = super.getRoom();
			Set<Crew> crew = room.getCrew();
			
			float boost = 1;
			if(getPower() == 2)
				boost = 1.5f;
			if(getPower() == 3)
				boost = 3;
			
			for(Crew member : crew)
			{
				member.heal(boost * 10 * dt, "Medbay");
			}
		}
	}
	
	public boolean canMann()
	{
		return false;
	}

}

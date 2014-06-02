package com.rebelkeithy.ftl.actions;

import com.rebelkeithy.ftl.ship.Ship;
import com.rebelkeithy.ftl.systems.WeaponSystem;

public class TargetAction extends Action
{

	public TargetAction(String action) 
	{
		super(action);
	}

	@Override
	public void preform(String action, Object... params) 
	{
		Ship ship = game.getShip((String) params[0]);
		WeaponSystem weapons = (WeaponSystem) ship.getSystem("weapons");
		if(weapons != null)
		{
			weapons.setTarget((Integer)params[1], (String) params[2], (String) params[3]);
		}
	}
	
}

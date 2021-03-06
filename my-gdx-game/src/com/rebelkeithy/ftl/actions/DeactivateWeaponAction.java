package com.rebelkeithy.ftl.actions;

import com.rebelkeithy.ftl.ship.Ship;
import com.rebelkeithy.ftl.systems.WeaponSystem;

public class DeactivateWeaponAction extends Action
{

	public DeactivateWeaponAction(String action) 
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
			weapons.powerDownWeapon((Integer) params[1]);
		}
	}
	
}

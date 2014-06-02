package com.rebelkeithy.ftl.actions;

import com.rebelkeithy.ftl.ship.Ship;
import com.rebelkeithy.ftl.systems.AbstractShipSystem;

public class PowerAction extends Action
{

	public PowerAction(String action) 
	{
		super(action);
	}

	@Override
	public void preform(String action, Object... params) 
	{
		Ship ship = game.getShip((String) params[0]);
		AbstractShipSystem system = ship.getSystem((String) params[1]);
		system.addPower((Integer)params[2]);
	}
	
}

package com.rebelkeithy.ftl.actions;

import com.rebelkeithy.ftl.ship.Ship;
import com.rebelkeithy.ftl.systems.AbstractShipSystem;
import com.rebelkeithy.ftl.systems.SystemRegistry;

public class AddSystemAction extends Action
{

	public AddSystemAction(String action) 
	{
		super(action);
	}

	@Override
	public void preform(String action, Object... params) 
	{
		Ship ship = game.getShip((String) params[0]);
		AbstractShipSystem system = SystemRegistry.build((String) params[1], ship, 1);
		ship.addSystem((String) params[1], system, (String)params[2]);
	}
	
}

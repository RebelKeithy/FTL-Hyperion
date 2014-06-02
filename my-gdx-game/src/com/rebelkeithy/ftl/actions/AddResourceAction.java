package com.rebelkeithy.ftl.actions;

import com.rebelkeithy.ftl.ship.Resource;
import com.rebelkeithy.ftl.ship.Ship;

public class AddResourceAction extends Action
{

	public AddResourceAction(String action) 
	{
		super(action);
	}

	@Override
	public void preform(String action, Object... params) 
	{
		Ship ship = game.getShip((String) params[0]);
		Resource resource = Resource.getResource((String) params[1]);
		resource.addResource(ship, (Integer)params[2]);
	}
	
}

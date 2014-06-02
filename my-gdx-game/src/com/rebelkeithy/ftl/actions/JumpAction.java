package com.rebelkeithy.ftl.actions;

import com.rebelkeithy.ftl.ship.Ship;

public class JumpAction extends Action
{

	public JumpAction(String action) 
	{
		super(action);
	}

	@Override
	public void preform(String action, Object... params) 
	{
		Ship ship = game.getShip((String) params[0]);
		
		int starID = (Integer) params[1];
		
		game.shipJump(ship, starID);
	}

}

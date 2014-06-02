package com.rebelkeithy.ftl.actions;

import com.rebelkeithy.ftl.crew.Crew;
import com.rebelkeithy.ftl.ship.Ship;

public class MoveAction extends Action
{

	public MoveAction(String action) 
	{
		super(action);
	}

	@Override
	public void preform(String action, Object... params) 
	{
		Ship ship = game.getShip((String) params[0]);
		Crew crew  = ship.getCrew((String)params[1]);
		System.out.println("Moving");
		if(crew != null)
			crew.move(ship.getRoom((String) params[2]), 0, 0);
		else
			System.out.println("Crew " + params[1] + " not found");
	}
	
}

package com.rebelkeithy.ftl.actions;

import com.rebelkeithy.ftl.ship.Ship;

public class ExamineAction extends Action
{

	public ExamineAction(String action) 
	{
		super(action);
	}

	@Override
	public void preform(String action, Object... params) 
	{
		Ship ship = game.getShip((String) params[0]);
		
		ship.examine();
	}

}

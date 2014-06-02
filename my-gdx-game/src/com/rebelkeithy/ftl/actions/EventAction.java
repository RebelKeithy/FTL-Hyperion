package com.rebelkeithy.ftl.actions;

import java.util.List;

import com.rebelkeithy.ftl.map.events.MapEvent;
import com.rebelkeithy.ftl.ship.Ship;

public class EventAction extends Action
{

	public EventAction(String action) 
	{
		super(action);
	}

	@Override
	public void preform(String action, Object... params) 
	{
		Ship ship = game.getShip((String) params[0]);
		
		MapEvent event = game.getEvent(ship);
		
		String s1 = (String)params[1];
		if(s1.equals("ListActions"))
		{
			List<String> actions = event.getActions();
			for(int i = 0; i < actions.size(); i++)
			{
				System.out.println("" + i + ": " + actions.get(i));
			}
		}
		else
		{
			int actionIndex = Integer.parseInt(s1);
			List<String> actions = event.getActions();
			String eventAction = actions.get(actionIndex);
			event.preformAction(eventAction);
		}
	}

}

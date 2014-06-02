package com.rebelkeithy.ftl.map.events;

import java.util.List;

import com.rebelkeithy.ftl.Clock;
import com.rebelkeithy.ftl.FTLGame;
import com.rebelkeithy.ftl.ship.Ship;

public class DialogEvent extends MapEvent
{
	EventState state;
	
	Ship ship;
	
	public DialogEvent(FTLGame game, int sector, int star)
	{	
		super(game, sector, star);
		state = new EventState(0);
		state.dialog = "you've encountered the space kraken.";
	}
	
	@Override
	public void enter(Ship ship)
	{
		this.ship = ship;
	}

	@Override
	public void update(double dt) 
	{
		ship.update(dt);
	}
	
	@Override
	public void activate()
	{
		Clock.log(state.dialog);
		List<String> actions = state.getActions();
		for(int i = 0; i < actions.size(); i++)
		{
			Clock.log("" + i + ": " + actions.get(i));
		}
	}

	@Override
	public void preformAction(String action) 
	{
		EventState nextState = state.getResult(action);
		if(nextState != null)
		{
			setState(nextState);
		}
	}
	
	protected void setState(EventState state)
	{
		this.state = state;
		if(!state.endingState)
		{
			activate();
		}
	}

	@Override
	public List<String> getActions() 
	{
		return state.getActions();
	}

	@Override
	public boolean allowShipControl() 
	{
		return state.endingState == true;
	}
}

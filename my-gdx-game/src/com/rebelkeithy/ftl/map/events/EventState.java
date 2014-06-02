package com.rebelkeithy.ftl.map.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class EventState
{
	public int id;
	public String dialog;
	public Map<String, Map<EventState, Double>> actions = new HashMap<String, Map<EventState, Double>>();
	public boolean endingState = false;
	
	public EventState(int id)
	{
		this.id = id;
	}
	
	public void addAction(String action, EventState result, double odds)
	{
		Map<EventState, Double> results = actions.get(action);
		
		if(results == null)
		{
			results = new HashMap<EventState, Double>();
			actions.put(action, results);
		}
		
		results.put(result, odds);
	}
	
	public EventState getResult(String action)
	{
		Map<EventState, Double> results = actions.get(action);
		
		Random rand = new Random();
		double roll = rand.nextDouble();
		double odds = 0;
		for(EventState result : results.keySet())
		{
			odds += results.get(result);
			if(roll < odds)
				return result;
		}
		
		return null;
	}

	public List<String> getActions() 
	{
		List<String> ret = new ArrayList<String>();
		
		for(String action : actions.keySet())
		{
			ret.add(action);
		}
		
		return ret;
	}
}
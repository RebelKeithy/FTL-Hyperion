package com.rebelkeithy.ftl.event;

import com.rebelkeithy.ftl.crew.Crew;

public class CrewUpdateEvent extends Event 
{
	private double dt;
	private Crew crew;
	
	public CrewUpdateEvent(double dt, Crew crew)
	{
		this.dt = dt;
		this.crew = crew;
	}
	
	public Crew getCrew()
	{
		return crew;
	}
	
	public double getDt()
	{
		return dt;
	}
}

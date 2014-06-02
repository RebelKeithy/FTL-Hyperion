package com.rebelkeithy.ftl.event;

import com.rebelkeithy.ftl.crew.Crew;

public class CrewDamagedEvent extends Event 
{	
	public int damage;
	private Crew crew;
	private String source;
	
	public CrewDamagedEvent(Crew crew, int damage, String source)
	{
		this.crew = crew;
		this.damage = damage;
		this.source = source;
	}
	
	public Crew getCrew()
	{
		return crew;
	}
	
	public String getSource()
	{
		return source;
	}
}

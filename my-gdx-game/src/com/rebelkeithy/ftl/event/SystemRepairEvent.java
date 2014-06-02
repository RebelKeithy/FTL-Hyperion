package com.rebelkeithy.ftl.event;

import com.rebelkeithy.ftl.crew.Crew;
import com.rebelkeithy.ftl.systems.AbstractShipSystem;

public class SystemRepairEvent extends Event 
{
	private AbstractShipSystem system;
	private Crew crew;
	public double repair;
	
	public SystemRepairEvent(AbstractShipSystem system, Crew crew, double repair) 
	{
		this.system = system;
		this.crew = crew;
		this.repair = repair;
	}
	
	public AbstractShipSystem getSystem()
	{
		return system;
	}
	
	public Crew getCrew()
	{
		return crew;
	}

}

package com.rebelkeithy.ftl.event;

public class FTLChargeEvent extends Event
{
	// rate per second
	public double chargeRate;
	
	public FTLChargeEvent(double chargeRate)
	{
		this.chargeRate = chargeRate;
	}
}

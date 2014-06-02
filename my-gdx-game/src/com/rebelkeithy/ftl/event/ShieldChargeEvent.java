package com.rebelkeithy.ftl.event;

public class ShieldChargeEvent extends Event
{
	private String ship;
	// rate per second
	public double chargeRate;
	private double currentCharge;
	private double maxChargeTime;
	
	public ShieldChargeEvent(String ship, double chargeRate, double currentCharge, double maxChargeTime)
	{
		this.ship = ship;
		this.chargeRate = chargeRate;
		this.currentCharge = currentCharge;
		this.maxChargeTime = maxChargeTime;
	}
	
	public String getShip()
	{
		return ship;
	}
	
	public double getCurrentCharge()
	{
		return currentCharge;
	}
	
	public double getMaxChargeTime()
	{
		return maxChargeTime;
	}
}

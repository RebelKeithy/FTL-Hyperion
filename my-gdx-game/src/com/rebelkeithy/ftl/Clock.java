package com.rebelkeithy.ftl;

import java.text.DecimalFormat;


public class Clock 
{
	private static Clock instance = new Clock();
	
	private double time;
	
	private Clock()
	{
		instance = this;
	}
	
	public static Clock instance()
	{
		return instance;
	}
	
	public void update(double dt)
	{
		time += dt;
	}
	
	public String time()
	{
		DecimalFormat nf = new DecimalFormat("#,###,###,##0.00");
		return nf.format(time);
	}

	public static void log(String string) 
	{
		System.out.println(Clock.instance().time() + ": " + string);
	}
	
}

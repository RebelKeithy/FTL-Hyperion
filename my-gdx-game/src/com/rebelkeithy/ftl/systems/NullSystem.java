package com.rebelkeithy.ftl.systems;


public class NullSystem extends AbstractShipSystem 
{

	public NullSystem() 
	{
		super(null, "null", 0);
	}

	
	public boolean canMann()
	{
		return false;
	}

	public int getPower()
	{
		return 0;
	}
}

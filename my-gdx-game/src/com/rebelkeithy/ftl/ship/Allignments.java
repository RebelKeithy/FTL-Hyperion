package com.rebelkeithy.ftl.ship;

public class Allignments
{
	public static final int NEUTRAL = 0;
	public static final int FEDERATION = 1;
	public static final int REBEL = 2;
	public static final int PIRATE = 3;
	
	public boolean isHostileTo(int allignment1, int allignment2)
	{
		switch(allignment1) {
			case NEUTRAL:
				return false;
			case FEDERATION:
				return allignment2 == REBEL || allignment2 == PIRATE;
			case REBEL:
				return allignment2 == FEDERATION || allignment2 == PIRATE;
			case PIRATE:
				return allignment2 != PIRATE;
		}
		
		return false;
	}
}

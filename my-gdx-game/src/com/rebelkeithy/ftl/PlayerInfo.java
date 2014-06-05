package com.rebelkeithy.ftl;

import com.rebelkeithy.ftl.properties.Properties;

public class PlayerInfo 
{
	private String player;
	private Properties properties;
	
	public PlayerInfo(String player)
	{
		this.player = player;
		
		properties = new Properties();
	}
	
	public Properties getProperties()
	{
		return properties;
	}
	
	public Properties getAchievements()
	{
		if(!properties.containsValue("achievements"))
		{
			properties.setProperty("achievements", new Properties());
		}
		
		return properties.getProperty("achievements");
	}
	
	public Properties getShipAchievements(String shipName)
	{
		if(!properties.containsValue(shipName + "achievements"))
		{
			properties.setProperty(shipName + "achievements", new Properties());
		}
		return properties.getProperty(shipName + "achievements");
	}
	
	public boolean isShipAvaliable(String shipName)
	{
		if(!properties.containsValue("ships"))
		{
			properties.setProperty("ships", new Properties());
		}
		
		return properties.getProperty("ships").getBoolean(shipName);
	}
	
	public void unlockShip(String shipName)
	{
		if(!properties.containsValue("ships"))
		{
			properties.setProperty("ships", new Properties());
		}

		properties.getProperty("ships").setBoolean(shipName, true);
	}
}

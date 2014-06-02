package com.rebelkeithy.ftl.ship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rebelkeithy.ftl.properties.Properties;

public class Resource
{
	private String name;
	private String icon;
	private String icon_low;
	private String tooltip;
	
	private int warningAmount;
	
	private static List<String> resourceList = new ArrayList<String>();
	private static Map<String, Resource> resources = new HashMap<String, Resource>();
	
	public static void registerResource(String name, Resource resource)
	{
		if(!resources.containsKey(name))
		{
			resources.put(name, resource);
			resourceList.add(name);
		}
	}
	
	public static Resource getResource(String name)
	{
		if(!resources.containsKey(name))
				System.out.println("Cannot find resource: " + name);
		return resources.get(name);
		
	}
	
	public static List<String> getResources()
	{
		return resourceList;
	}
	
	public Resource(String name, String icon, String tooltip, int warningAmount)
	{
		this.name = name;
		this.icon = icon;
		this.icon_low = icon;
		this.tooltip = tooltip;
		this.warningAmount = warningAmount;
	}
	
	public void setLowIcon(String icon_low)
	{
		this.icon_low = icon_low;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getIcon()
	{
		return icon;
	}
	
	public String getWarningIcon()
	{
		return icon_low;
	}
	
	public String getTooltip()
	{
		return tooltip;
	}
	
	public int getWarningAmount()
	{
		return warningAmount;
	}
	
	public int getResourceAmount(Ship ship)
	{
		Properties properties = ship.getProperties();
		int amount = properties.getInteger("resource_" + name + "_amount");
		return amount;
	}
	
	public void addResource(Ship ship, int amount)
	{
		Properties properties = ship.getProperties();
		int curr = properties.getInteger("resource_" + name + "_amount");
		curr += amount;
		if(curr < 0)
			curr = 0;
		properties.setInteger("resource_" + name + "_amount", curr);
		
	}
}

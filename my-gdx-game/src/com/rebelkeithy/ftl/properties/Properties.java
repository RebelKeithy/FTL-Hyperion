package com.rebelkeithy.ftl.properties;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Properties 
{
	Map<String, Object> properties;
	
	public Properties()
	{
		properties = new HashMap<String, Object>();
	}
	
	public void setInteger(String name, int value)
	{
		properties.put(name, value);
	}
	
	public void setDouble(String name, double value)
	{
		properties.put(name, value);
	}
	
	public void setString(String name, String value)
	{
		properties.put(name, value);
	}
	
	public void setProperty(String name, Properties property)
	{
		properties.put(name, property);
	}
	
	public int getInteger(String name)
	{
		if(properties.containsKey(name))	
			return (Integer)properties.get(name);
		return 0;
	}
	
	public double getDouble(String name)
	{
		if(properties.containsKey(name))	
			return (Double)properties.get(name);
		return 0;
	}
	
	public String getString(String name)
	{
		if(properties.containsKey(name))	
			return (String)properties.get(name);
		return "";
	}
	
	public Properties getProperty(String name)
	{
		if(properties.containsKey(name))	
			return (Properties)properties.get(name);
		return null;
	}
	
	public Set<String> getKeys()
	{
		return properties.keySet();
	}

	public boolean containsValue(String string) 
	{
		return properties.containsKey(string);
	}

	public void removeProperty(String string) 
	{
		properties.remove(string);
	}
}

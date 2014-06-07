package com.rebelkeithy.ftl.systems;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.rebelkeithy.ftl.ship.Ship;

public class SystemRegistry 
{
	public static Map<String, Class<? extends AbstractShipSystem>> systems = new HashMap<String, Class<? extends AbstractShipSystem>>();
	
	public static void register(String name, Class<? extends AbstractShipSystem> system)
	{
		systems.put(name, system);
	}
	
	public static AbstractShipSystem build(String name, Ship ship, int maxPower)
	{
		Class<? extends AbstractShipSystem> clazz = systems.get(name);
		
		if(clazz != null)
		{
			try 
			{
				Constructor<? extends AbstractShipSystem> constructor = clazz.getConstructor(Ship.class, String.class, int.class);
				AbstractShipSystem system = constructor.newInstance(ship, name, maxPower);
				return system;
			} 
			catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	public static void loadTextures()
	{
		for(String systemName : systems.keySet())
		{
			AbstractShipSystem system = SystemRegistry.build(systemName, null, 1);
			system.loadTextures();
		}
	}
}

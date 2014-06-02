package com.rebelkeithy.ftl.weapons;

import java.util.HashMap;
import java.util.Map;

public class WeaponRegistry 
{
	private static Map<String, WeaponBuilder> weapons = new HashMap<String, WeaponBuilder>();
	
	public static void registerWeaponBuilder(String name, WeaponBuilder builder)
	{
		weapons.put(name, builder);
	}
	
	public static Weapon buildWeapon(String name)
	{
		WeaponBuilder builder = weapons.get(name);
		
		if(builder != null)
		{
			return builder.build(name);
		}
		else
		{
			System.out.println("Weapon " + name + " not found");
		}
		
		return null;
	}
}

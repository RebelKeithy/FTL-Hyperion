package com.rebelkeithy.ftl.weapons;

import java.util.HashMap;
import java.util.Map;


public class BombBuilder implements WeaponBuilder
{
	public Map<String, BombDef> weapons = new HashMap<String, BombDef>();
	
	static class BombDef
	{
		public int power;
		public int cooldown;
		Map<String, Integer> damages = new HashMap<String, Integer>();
		String ammo = null;
		
		public void addDamage(String damage, int amount)
		{
			damages.put(damage, amount);
		}
		
		public String toString()
		{
			return "" + power + ", " + cooldown + ", " + damages + ", " + ammo;
		}
	}
	
	public void addWeapon(String name, BombDef def)
	{
		weapons.put(name, def);
	}

	@Override
	public Weapon build(String name) 
	{
		System.out.println(name);
		BombDef def = weapons.get(name);
		
		if(def != null)
		{
			BombWeapon weapon = new BombWeapon(name, def.power, def.cooldown);
			for(String damage : def.damages.keySet())
			{
				weapon.setDamage(damage, def.damages.get(damage));
			}
			weapon.setAmmo(def.ammo);
			
			return weapon;
		}
		
		return null;
	}
	
}

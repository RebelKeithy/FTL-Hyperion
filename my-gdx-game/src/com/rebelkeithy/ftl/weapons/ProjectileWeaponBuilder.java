package com.rebelkeithy.ftl.weapons;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class ProjectileWeaponBuilder implements WeaponBuilder
{
	static class WeaponDef
	{
		String name = "";
		int power = 1;
		int cooldown = 10;
		int shots = 1;
		int pierce = 0;
		Map<String, Double> damages = new HashMap<String, Double>();
		String ammo = null;
		
		public String toString()
		{
			return "" + power + ", " + cooldown + ", " + shots + ", " + pierce + ", " + damages.toString();
		}
	}
	
	private static ProjectileWeaponBuilder instance = new ProjectileWeaponBuilder();
	private static Map<String, WeaponDef> weapons = new HashMap<String, WeaponDef>();
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void registerWeapons(File file)
	{
		Map root;
		try {
			root = (new Gson()).fromJson(new FileReader(file), Map.class);
		
			Map<String, Map> projectileDefs = (Map<String, Map>) root.get("projectile");
			for(String name : projectileDefs.keySet())
			{
				Map projectileDef = projectileDefs.get(name);
				WeaponDef weapon = new WeaponDef();
				if(projectileDef.containsKey("power"))
					weapon.power = ((Double)projectileDef.get("power")).intValue();
				if(projectileDef.containsKey("cooldown"))
					weapon.cooldown = ((Double)projectileDef.get("cooldown")).intValue();
				if(projectileDef.containsKey("shots"))
					weapon.shots = ((Double)projectileDef.get("shots")).intValue();
				if(projectileDef.containsKey("pierce"))
					weapon.pierce = ((Double)projectileDef.get("pierce")).intValue();
				if(projectileDef.containsKey("ammo"))
					weapon.ammo = (String)projectileDef.get("ammo");
				if(projectileDef.containsKey("damage"))
				{
					Map<String, Double> damages = (Map<String, Double>) projectileDef.get("damage");
					weapon.damages = damages;
				}
				else
				{
					weapon.damages.put("hull", 1.0);
					weapon.damages.put("system", 1.0);
				}

				if(projectileDef.containsKey("name"))
					weapon.name = (String)projectileDef.get("name");
				else
					weapon.name = name;
				
				WeaponRegistry.registerWeaponBuilder(name, instance);
				weapons.put(name, weapon);
			}
			
			BombBuilder bombBuilder = new BombBuilder();
			Map<String, Map> bombDefs = (Map<String, Map>) root.get("bomb");
			for(String name : bombDefs.keySet())
			{
				Map bombDef = bombDefs.get(name);
				BombBuilder.BombDef def = new BombBuilder.BombDef();
				double power = (Double) bombDef.get("power");
				double cooldown = (Double) bombDef.get("cooldown");
				if(bombDef.containsKey("damage"))
				{
					Map<String, Double> damages = (Map<String, Double>) bombDef.get("damage");
					for(String damage : damages.keySet())
					{
						double amount = damages.get(damage);
						def.addDamage(damage, (int) amount);
					}
				}
				else
				{
					def.addDamage("hull", 1);
					def.addDamage("system", 1);
				}
				def.ammo = (String) bombDef.get("ammo");
				def.power = (int) power;
				def.cooldown = (int) cooldown;

				bombBuilder.addWeapon(name, def);
				WeaponRegistry.registerWeaponBuilder(name, bombBuilder);
			}
			
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public Weapon build(String name) 
	{
		if(weapons.containsKey(name))
		{
			WeaponDef def = weapons.get(name);
			ProjectileWeapon weapon = new ProjectileWeapon(def.name, def.power, def.cooldown).setNumShots(def.shots).setPiece(def.pierce).setAmmo(def.ammo);
			for(String damage : def.damages.keySet())
				weapon.setDamage(damage, ((Double)def.damages.get(damage)).intValue());
			return weapon;
		}
		
		return null;
	}
}

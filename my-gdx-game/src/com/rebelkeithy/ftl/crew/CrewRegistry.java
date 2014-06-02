package com.rebelkeithy.ftl.crew;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class CrewRegistry 
{
	private static double baseSpeed = 1.5;
	private static double baseAttack = 5;
	private static double baseRepair = 0.05;
	
	public static class Race
	{
		public String texture;
		public int health;
		public double attack;
		public double repair;
		public double speed;
	}
	
	private static Map<String, Race> races = new HashMap<String, Race>();
	
	@SuppressWarnings({ "unchecked" })
	public static void registerRaces(File file)
	{
		Map<String, Map<String, Object>> root;
		try {
			root = (new Gson()).fromJson(new FileReader(file), Map.class);
		
			for(String name : root.keySet())
			{
				Map<String, Object> projectileDef = root.get(name);
				Race race = new Race();
				if(projectileDef.containsKey("texture"))
					race.texture = ((String)projectileDef.get("texture"));
				if(projectileDef.containsKey("health"))
					race.health = ((Double)projectileDef.get("health")).intValue();
				if(projectileDef.containsKey("attack"))
					race.attack = ((Double)projectileDef.get("attack"));
				if(projectileDef.containsKey("repair"))
					race.repair = ((Double)projectileDef.get("repair"));
				if(projectileDef.containsKey("speed"))
					race.speed = ((Double)projectileDef.get("speed"));
				
				races.put(name, race);
			}
			
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Crew build(String race, String name)
	{
		Race raceDef = races.get(race);
		if(raceDef == null)
		{
			System.out.println("Race " + race + " not found.");
		}
		
		Crew crew = new Crew(race, name);
		crew.setMaxHelth(raceDef.health);
		crew.setHealth(raceDef.health);
		crew.setAttack(raceDef.attack * baseAttack);
		crew.setRepair(raceDef.repair * baseRepair);
		crew.setSpeed(raceDef.speed * baseSpeed);
		
		return crew;
	}

	public static Race getRace(String race) 
	{
		System.out.println("Race " + race + " not found");
		return races.get(race);
	}
}

package com.rebelkeithy.ftl.ship;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.rebelkeithy.ftl.crew.Crew;
import com.rebelkeithy.ftl.crew.CrewRegistry;
import com.rebelkeithy.ftl.systems.AbstractShipSystem;
import com.rebelkeithy.ftl.systems.SystemRegistry;
import com.rebelkeithy.ftl.systems.WeaponSystem;
import com.rebelkeithy.ftl.weapons.Weapon;
import com.rebelkeithy.ftl.weapons.WeaponRegistry;

public class ShipRegistry 
{
	@SuppressWarnings("rawtypes")
	public static Map<String, Map> ships = new HashMap<String, Map>();
	
	public static void registerShip(File file)
	{

		try 
		{
			@SuppressWarnings("rawtypes")
			Map root = (new Gson()).fromJson(new FileReader(file), Map.class);
			String name = (String) root.get("name");
			
			System.out.println("Registering Ship: " + name);
			ships.put(name, root);
			
		}
		catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Ship build(String shipName, String ShipInstanceName)
	{
		System.out.println("Building ship " + shipName);
		Map root = ships.get(shipName);
		
		String layout = (String) root.get("layout");
		Ship ship = ShipLayoutRegistry.build(layout);
		ship.setName(ShipInstanceName);
		
		
		Map<String, Map> systemDefs = (Map<String, Map>) root.get("systems");
		for(String systemName : systemDefs.keySet())
		{
			Map systemDef = systemDefs.get(systemName);
			
			String name = (String) systemDef.get("room");
			double power = (Double) systemDef.get("power");
			List<Double> costs_double = (List<Double>) systemDef.get("cost");
			
			int[] costs = new int[costs_double.size()];
			
			for(int i = 0; i < costs.length; i++)
				costs[i] = costs_double.get(i).intValue();
			
			AbstractShipSystem system = SystemRegistry.build(systemName, ship, (int) power);
			system.setCost(costs);
			ship.addSystem(systemName, system);
			if(name != null)
			{
				Room room = ship.getRoom(name);
				room.addSystem(system);
				system.setRoom(room);

				if(systemDef.containsKey("stationX"))
				{
					double stationX = (Double) systemDef.get("stationX");
					double stationY = (Double) systemDef.get("stationY");
					String stationDir = (String) systemDef.get("direction");
					system.addStation((int)stationX, (int)stationY, Direction.getFromString(stationDir));
				}
			}
		}
		
		Map<String, Object> resources = (Map<String, Object>) root.get("resources");
		for(String resourceName : resources.keySet())
		{
			double amount = (Double) resources.get(resourceName);
			Resource resource = Resource.getResource(resourceName);
			resource.addResource(ship, (int) amount);
		}
		
		List<String> weapons = (List<String>) root.get("weapons");
		for(int i = 0; i < weapons.size(); i++)
		{
			Weapon weapon = WeaponRegistry.buildWeapon(weapons.get(i));
			((WeaponSystem)ship.getSystem("weapons")).setWeapon(weapon, i);
		}
		
		Map<String, Object> crew = (Map<String, Object>) root.get("crew");
		for(String crewName : crew.keySet())
		{
			Map crewMap = (Map) crew.get(crewName);
			String room = (String) crewMap.get("room");
			String race = (String) crewMap.get("race");
			System.out.println(crewName + " " + room + " " + race);
			
			Crew member = CrewRegistry.build(race, crewName);
			member.setHomeShip(ShipInstanceName);
			ship.addCrew(member);
			if(ship.getRoom(room) == null)
			{
				System.out.println("Error placing crew in room " + room + ", room not found");
			}
			
			if(!member.addToRoom(ship.getRoom(room)))
			{
				System.out.println("Error placing crew in room " + room + ", no empty tiles");
			}
		}
		
		//ship.addSystem("reactor", new ReactorSystem(ship, "reactor", 8));
		
		
		return ship;
	}
}

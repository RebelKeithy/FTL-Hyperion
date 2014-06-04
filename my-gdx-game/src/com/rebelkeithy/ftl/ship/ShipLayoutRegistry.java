package com.rebelkeithy.ftl.ship;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.rebelkeithy.ftl.view.TextureRegistry;

public class ShipLayoutRegistry 
{
	private static Map<String, Map<String, Object>> layouts = new HashMap<String, Map<String, Object>>();
	
	public static void registerLayout(File file)
	{
		try 
		{
			@SuppressWarnings("unchecked")
			Map<String, Object> root = (new Gson()).fromJson(new FileReader(file), Map.class);
			
			String name = (String) root.get("name");
			
			layouts.put(name, root);
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
	public static Ship build(String name)
	{

		Map root = layouts.get(name);
		
		Ship ship = new Ship();
		
		// Render data
		String image = (String) root.get("texture");
		TextureRegistry.registerSprite(image, "ship/" + image);
		ship.renderData.shipTexture = image;
		ship.renderData.miniShipTexture = (String) root.get("miniship_texture");
		TextureRegistry.registerSprite(ship.renderData.miniShipTexture, "customizeUI/" + ship.renderData.miniShipTexture);
		ship.renderData.roomTexture = (String) root.get("roomtexture");
		TextureRegistry.registerSprite(ship.renderData.roomTexture, "ship/" + ship.renderData.roomTexture);
		ship.renderData.shipTexture = image;
		ship.renderData.shieldTexture = (String) root.get("shieldimage");
		TextureRegistry.registerSprite(ship.renderData.shieldTexture, "ship/" + ship.renderData.shieldTexture);
		ship.renderData.offsetX = ((Double)root.get("offsetX")).intValue();
		ship.renderData.offsetY = ((Double)root.get("offsetY")).intValue();
		ship.renderData.shipTextureOffsetX = ((Double)root.get("shipTextureOffsetX")).intValue();
		ship.renderData.shipTextureOffsetY = ((Double)root.get("shipTextureOffsetY")).intValue();
		ship.renderData.roomTextureOffsetX = ((Double)root.get("roomTextureOffsetX")).intValue();
		ship.renderData.roomTextureOffsetY = ((Double)root.get("roomTextureOffsetY")).intValue();
		ship.renderData.shieldTextureOffsetX = ((Double)root.get("shieldTextureOffsetX")).intValue();
		ship.renderData.shieldTextureOffsetY = ((Double)root.get("shieldTextureOffsetY")).intValue();
		
		if(root.containsKey("thrusterX"))
		{
			List<Double> thrusterX = (List<Double>) root.get("thrusterX");
			List<Double> thrusterY = (List<Double>) root.get("thrusterY");
			ship.renderData.thrusterX = new int[thrusterX.size()];
			ship.renderData.thrusterY = new int[thrusterY.size()];
			for(int i = 0; i < thrusterX.size(); i++)
			{
				ship.renderData.thrusterX[i] = thrusterX.get(i).intValue();
				ship.renderData.thrusterY[i] = thrusterY.get(i).intValue();
			}
		}
		
		// Create Rooms
		Map<String, Room> rooms = new HashMap<String, Room>();		
		Map<String, Map> roomDefs = (Map<String, Map>) root.get("rooms");		
		for(String roomName : roomDefs.keySet())
		{
			Map roomDef = (Map) roomDefs.get(roomName);
			int x = ((Double) roomDef.get("x")).intValue();
			int y = ((Double) roomDef.get("y")).intValue();
			int width = ((Double) roomDef.get("width")).intValue();
			int height = ((Double) roomDef.get("height")).intValue();
			String texture = (String) roomDef.get("texture");
			Room room = new Room(ship, roomName, x, y, width, height);
			room.setTexture(texture);
			rooms.put(roomName, room);
			ship.addRoom(roomName, room);
		}
		
		// Add doors to rooms
		for(String roomName : roomDefs.keySet())
		{
			Map roomDef = (Map) roomDefs.get(roomName);
			Map doorDefs = (Map) roomDef.get("doors");
			for(Object doorName : doorDefs.keySet())
			{
				Map doorDef = (Map) doorDefs.get(doorName);
				String dirString = (String) doorDef.get("direction");
				int dir = Direction.getFromString(dirString);
				double offset = ((Double) doorDef.get("offset"));
				String room2 = (String) doorDef.get("room");
				
				Room r1 = rooms.get(roomName);
				Room r2 = null;
				if(room2 != null)
					r2 = rooms.get(room2);
				Door door = new Door(dir, (int) offset, r1, r2);
				r1.addDoor(door);
				
			}
		}
		
		// Link doors
		System.out.println("linking doors");
		Set<Door> doors = new HashSet<Door>();
		for(Room room : ship.getRooms().values())
		{
			for(Door door : room.getDoors())
			{
				if(door.getLink() == null)
				{
					for(Door other : doors)
					{
						System.out.println(door.getX() + " " + other.getX());
						if(door.getX() == other.getX() && door.getY() == other.getY())
						{
							if(door.room1 == other.room2)
							{
								System.out.println("linking " + door + " " + other);
								door.link(other);
								other.link(door);
								doors.remove(other);
								break;
							}
						}
					}
					if(door.getLink() == null)
					{
						doors.add(door);
					}
				}
			}
		}
		
		return ship;
	}
}



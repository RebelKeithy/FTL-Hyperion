package com.rebelkeithy.ftl.view;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rebelkeithy.ftl.crew.Crew;
import com.rebelkeithy.ftl.ship.Direction;
import com.rebelkeithy.ftl.ship.Door;
import com.rebelkeithy.ftl.ship.Room;
import com.rebelkeithy.ftl.ship.Ship;
import com.rebelkeithy.ftl.ship.Tile;

public class ShipRenderer 
{
	Texture floor;
	Texture thrusters;
	Ship ship;
	Set<Sprite> rooms;
	
	int offsetX;
	int offsetY;
	int shipOffsetX;
	int shipOffsetY;
	
	int shipTextureOffsetX = 0;
	int shipTextureOffsetY = 0;
	int shieldOffsetX = 0;
	int shieldOffsetY = 0;
	int roomTextureOffsetX = 0;
	int roomTextureOffsetY = 0;
	
	RoomRenderer roomRenderer;
	CrewRenderer crewRenderer;
	private boolean interactive;
	
	public ShipRenderer(Ship ship)
	{
		rooms = new HashSet<Sprite>();
		
		this.ship = ship;
		
		thrusters = TextureRegistry.registerSprite("thrusters_on", "effects/thrusters_on");
		roomRenderer = new RoomRenderer();
		crewRenderer = new CrewRenderer("human_base", "human_glow");

		shipOffsetX = ship.renderData.offsetX;
		shipOffsetY = ship.renderData.offsetY;
		shipTextureOffsetX = ship.renderData.shipTextureOffsetX;
		shipTextureOffsetY = ship.renderData.shipTextureOffsetY;
		roomTextureOffsetX = ship.renderData.roomTextureOffsetX;
		roomTextureOffsetY = ship.renderData.roomTextureOffsetY;
		shieldOffsetX = ship.renderData.shieldTextureOffsetX;
		shieldOffsetY = ship.renderData.shieldTextureOffsetY;

		Map<String, Room> shipRooms = ship.getRooms();
		float tileWidth = 35f;
		for(Room room : shipRooms.values())
		{
			Pixmap roomMap = new Pixmap((int)(room.getWidth()*35), (int)(room.getHeight()*35), Format.RGBA8888);
			roomMap.setColor(230/256f, 225/256f, 219/256f, 1);
			roomMap.fill();
			roomMap.setColor(0, 0, 0, 1);
			roomMap.drawRectangle(0, 0, roomMap.getWidth(), roomMap.getHeight());
			roomMap.drawRectangle(1, 1, roomMap.getWidth()-2, roomMap.getHeight()-2);


			roomMap.setColor(182/256f, 173/256f, 166/256f, 1);
			for(int i = 1; i < room.getWidth(); i++)
			{
				roomMap.drawLine(i*34, 2, i*34, roomMap.getHeight()-3);
			}
			for(int i = 1; i < room.getHeight(); i++)
			{
				roomMap.drawLine(2, i*34, roomMap.getWidth()-3, i*34);
			}
			
			
			Sprite sprite = new Sprite(new Texture(roomMap));
			roomMap.dispose();
			sprite.setPosition(shipOffsetX + room.getX() * tileWidth, shipOffsetY + room.getY() * tileWidth);
			sprite.setSize(room.getWidth() * tileWidth, room.getHeight() * tileWidth);
			rooms.add(sprite);
		}
		
	}
	
	public void setOffset(int offsetX, int offsetY)
	{
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}
	
	public void render(SpriteBatch batch, Ship ship)
	{		
		int totalShipOffsetX = shipOffsetX + offsetX;
		int totalShipOffsetY = shipOffsetY + offsetY;
		//Matrix4 m = batch.getProjectionMatrix();
		//Matrix4 m_saved = m.cpy();
		
		//if(false /*Event has another ship*/)
		//	batch.setProjectionMatrix(m.translate(shipOffsetX-150, shipOffsetY, 0));
		//else
		//	batch.setProjectionMatrix(m.translate(shipOffsetX, shipOffsetY, 0));

		
		Texture shieldImage = TextureRegistry.getTexture(ship.renderData.shieldTexture);
		int shieldLvl = ship.getShieldLvl();
		if(shieldLvl > 0)
		{
			float alpha = 0.5f + 0.12f * (shieldLvl-1);
			batch.setColor(1, 1, 1, alpha);
			batch.draw(shieldImage, totalShipOffsetX + shieldOffsetX, totalShipOffsetY + shieldOffsetY);
			batch.setColor(1, 1, 1, 1);
		}
		
		Texture texture = TextureRegistry.getTexture(ship.renderData.shipTexture);
		batch.draw(texture, totalShipOffsetX + shipTextureOffsetX, totalShipOffsetY + shipTextureOffsetY);

		if(ship.getSystem("engines").isPowered() && ship.getSystem("command").isManned())
		{
			long time = System.currentTimeMillis();
			int u = (int) ((time%500)/125);
			TextureRegion thrustersRegion = new TextureRegion(thrusters, u*22, 0, 22, 70);
			
			for(int i = 0; i < ship.renderData.thrusterX.length; i++)
			{
				batch.draw(thrustersRegion, totalShipOffsetX + ship.renderData.thrusterX[i], totalShipOffsetY + ship.renderData.thrusterY[i]);
			}
		}
		
		texture = TextureRegistry.getTexture(ship.renderData.roomTexture);
		batch.draw(texture, totalShipOffsetX + roomTextureOffsetX, totalShipOffsetY + roomTextureOffsetY);

		
		for(Room room : ship.getRooms().values())
		{
			roomRenderer.render(batch, room, totalShipOffsetX, totalShipOffsetY);
		}
		
		for(Room room : ship.getRooms().values())
		{
			for(Door door : room.getDoors())
			{
				DoorRenderer.getRenderer(door).render(batch, door, totalShipOffsetX, totalShipOffsetY);
			}
		}
		
		for(Crew member : ship.getCrew())
		{
			if(CrewRenderer.getCrewRenderer(member) == null)
			{
				CrewRenderer.registerCrewRenderer(member, new CrewRenderer("human_base", "human_glow"));
			}
			CrewRenderer.getCrewRenderer(member).render(batch, member, totalShipOffsetX, totalShipOffsetY);
		}
		
		
	}

	public boolean click(int screenX, int screenY, int button) 
	{
		if(!interactive)
			return false;
		
		for(int i = 0; i < ship.getCrew().size(); i++)
		{
			Crew member = ship.getCrew().get(i);
			int crewX = (int) (member.getX() * 35 + shipOffsetX + offsetX);
			int crewY = (int) (member.getY() * 35 + shipOffsetY + offsetY);
			
			if(button == 0 && screenX > crewX && screenX < crewX + 35 && screenY > crewY && screenY < crewY + 35)
			{
				FTLView.inputHandler.selected = member;
				
				return true;
			}
			
			CrewRenderer renderer = CrewRenderer.getCrewRenderer(member);
			renderer.click(screenX, screenY, button, member, i);
		}

		if(FTLView.inputHandler.selected instanceof Crew)
		{
			for(Room room : ship.getRooms().values())
			{
				int roomX = room.getX() * 35 + shipOffsetX + offsetX;
				int roomY = room.getY() * 35 + shipOffsetY + offsetY;
				
	
				if(screenX > roomX && screenX < roomX + room.getWidth()*35 && screenY > roomY && screenY < roomY + room.getHeight()*35)
				{
					Crew crew = (Crew) FTLView.inputHandler.selected;

					if(room.getSystem() != null && room.getSystem().canMann() && !room.getSystem().isManned())
					{
						Tile stationTile = room.getTile(room.getSystem().getStationX(), room.getSystem().getStationY());
						if(!stationTile.getProperties().containsValue("crew"))
							crew.move(room, room.getSystem().getStationX(), room.getSystem().getStationY());
						else
							crew.move(room);
					}
					else
					{
						//TODO: find open tile
						crew.move(room);
					}
				}
			}
		}
		
		if(button == 0 && FTLView.inputHandler.selected == null)
		{
			boolean changed = false;
			for(Room room : ship.getRooms().values())
			{
				for(Door door : room.getDoors())
				{
					int doorX = (int) (door.getX() * 35 + shipOffsetX + offsetX);
					int doorY = (int) (door.getY() * 35 + shipOffsetY + offsetY);
					
					int shrinkH = 5;
					int shrinkV = 10;
					if(door.direction == Direction.RIGHT || door.direction == Direction.LEFT)
						if(screenX > doorX - 35/2 + shrinkV && screenX < doorX + 35/2 - shrinkV && screenY > doorY + shrinkH && screenY < doorY + 35 - shrinkH)
						{
							changed = true;
							door.forceOpen = !door.forceOpen;
						}
					if(door.direction == Direction.UP || door.direction == Direction.DOWN)
						if(screenX > doorX + shrinkH && screenX < doorX + 35 - shrinkH && screenY > doorY - 35/2 + shrinkV && screenY < doorY + 35/2 - shrinkV)
						{
							changed = true;
							door.forceOpen = !door.forceOpen;
						}
				}
			}
			if(changed)
				return true;
		}
		
		return false;
	}

	public void setInteractive(boolean interactive) 
	{
		this.interactive = interactive;
	}
}

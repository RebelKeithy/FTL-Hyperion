package com.rebelkeithy.ftl.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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

public class RoomRenderer 
{
	private Texture lowOxygen = TextureRegistry.registerSprite("lowOxygenFloor", "effects/low_o2_stripes_2x2");
	private Texture crewDestination = TextureRegistry.registerSprite("crewTarget", "people/green_destination");
	private Texture black;
	private Texture white;
	
	public RoomRenderer()
	{
		black = TextureRegistry.getTexture("black");
		white = TextureRegistry.getTexture("white");
	}
	
	public void render(SpriteBatch batch, Room room, int shipOffsetX, int shipOffsetY)
	{
		float oxygen = (float) (room.getProperties().getDouble("oxygen")/100f);
		
		Texture texture = getRoomTexture(room.getWidth(), room.getHeight());
		batch.setColor(1, oxygen*0.23f + 0.77f, oxygen*0.23f + 0.77f, 1);
		batch.draw(texture, shipOffsetX + room.getX()*35, shipOffsetY + room.getY()*35);
		batch.setColor(1, 1, 1, 1);

		// Draw red stripes of oxygen is too low
		if(oxygen < 0.1)
		{
			batch.setColor(1, 1, 1, 0.5f);
			TextureRegion oxyregion = new TextureRegion(lowOxygen, 0, 0, room.getWidth()*35 - 4, room.getHeight()*35 - 4);
			batch.draw(oxyregion, shipOffsetX + room.getX()*35 + 2, shipOffsetY + room.getY()*35 + 2);
			batch.setColor(1, 1, 1, 1);
		}
		
		// TODO: this should be changed to accommodate multiple stations/systems in a room
		if(room.getTexture() != null)
		{
			Texture roomFloor = TextureRegistry.getTexture(room.getTexture());
			if(roomFloor == null)
				roomFloor = TextureRegistry.registerSprite(room.getTexture(), "ship/interior/" + room.getTexture());
			
			Sprite sprite = new Sprite(roomFloor);
			sprite.setPosition(shipOffsetX + room.getX()*35, shipOffsetY + room.getY()*35);
			sprite.setAlpha(0.75f);
			sprite.draw(batch);
			//batch.draw(roomFloor, room.getX()*35, room.getY()*35);
		}
		
		// Render system icon
		if(room.getSystem() != null)
		{
			String system = room.getSystem().getName();
			Texture systemTexture = TextureRegistry.getTexture("room_system_icon_" + system);
			batch.setColor(125/256f, 125/256f, 125/256f, 1);
			batch.draw(systemTexture, shipOffsetX + room.getX()*35 + texture.getWidth()/2 - systemTexture.getWidth()/2, shipOffsetY + room.getY()*35 + (int)Math.ceil(texture.getHeight()/2) - systemTexture.getHeight()/2);
			batch.setColor(1, 1, 1, 1);
		}
		
		// Draw green crew destination image
		for(int tileX = 0; tileX < room.getWidth(); tileX++)
		{
			for(int tileY = 0; tileY < room.getHeight(); tileY++)
			{
				if(room.getTile(tileX, tileY).getProperties().containsValue("crew"))
				{
					Crew crew = room.getCrew(room.getTile(tileX, tileY).getProperties().getString("crew"));
					
					if(crew == null || crew.getX() - room.getX() != tileX || crew.getY() - room.getY() != tileY)
					{
						batch.draw(crewDestination, shipOffsetX + (room.getX() + tileX) * 35, shipOffsetY + (room.getY() + tileY)*35);
					}
				}
			}
		}

		// Draw highlight if crew is selected and mouse is over
		if(FTLView.inputHandler.selected instanceof Crew)
		{
			int mouseX = Gdx.input.getX() - shipOffsetX;
			int mouseY = (Gdx.graphics.getHeight()-Gdx.input.getY()) - shipOffsetY;
	
			if(mouseX > room.getX()*35 && mouseX < room.getX()*35 + room.getWidth()*35 && mouseY > room.getY()*35 && mouseY < room.getY()*35 + room.getHeight()*35)
			{
				Texture highlight = this.getRoomHighlight(room.getWidth(), room.getHeight());
				batch.draw(highlight, shipOffsetX + room.getX()*35, shipOffsetY + room.getY()*35);
			}
		}
		
		batch.draw(new TextureRegion(black, room.getWidth()*35, 2), shipOffsetX + room.getX()*35, shipOffsetY + room.getY()*35);
		batch.draw(new TextureRegion(black, room.getWidth()*35, 2), shipOffsetX + room.getX()*35, shipOffsetY + room.getY()*35 + room.getHeight()*35 - 2);
		batch.draw(new TextureRegion(black, 2, room.getHeight()*35), shipOffsetX + room.getX()*35, shipOffsetY + room.getY()*35);
		batch.draw(new TextureRegion(black, 2, room.getHeight()*35), shipOffsetX + room.getX()*35 + room.getWidth()*35 - 2, shipOffsetY + room.getY()*35);
		
		for(Door door : room.getDoors())
		{
			batch.setColor(Color.RED);
			batch.setColor(230/256f, 225/256f, 219/256f, 1);
			if(door.direction == Direction.LEFT)
			{
				batch.draw(white, shipOffsetX + (float)door.getX()*35, shipOffsetY + (float)door.getY()*35 + 8, 2, 18);
			}
			if(door.direction == Direction.RIGHT)
			{
				batch.draw(white, shipOffsetX + (float)door.getX()*35 - 2, shipOffsetY + (float)door.getY()*35 + 8, 2, 18);
			}
			if(door.direction == Direction.UP)
			{
				batch.draw(white, shipOffsetX + (float)door.getX()*35 + 8, shipOffsetY + (float)door.getY()*35 - 2, 18, 2);
			}
			if(door.direction == Direction.DOWN)
			{
				batch.draw(white, shipOffsetX + (float)door.getX()*35 + 8, shipOffsetY + (float)door.getY()*35, 18, 2);
			}
		}
		batch.setColor(Color.WHITE);
	}
	
	public Texture getRoomTexture(int width, int height)
	{
		Texture texture = TextureRegistry.getTexture("Room" + width + "x" + height);
		if(texture == null)
		{
			Pixmap roomMap = new Pixmap(width*35, height*35, Format.RGBA8888);
			roomMap.setColor(230/256f, 225/256f, 219/256f, 1);
			roomMap.fill();
			roomMap.setColor(0, 0, 0, 1);
			//roomMap.drawRectangle(0, 0, roomMap.getWidth(), roomMap.getHeight());
			//roomMap.drawRectangle(1, 1, roomMap.getWidth()-2, roomMap.getHeight()-2);

			roomMap.setColor(182/256f, 173/256f, 166/256f, 1);
			for(int i = 1; i < width; i++)
			{
				roomMap.drawLine(i*34, 2, i*34, roomMap.getHeight()-3);
			}
			for(int i = 1; i < height; i++)
			{
				roomMap.drawLine(2, i*34, roomMap.getWidth()-3, i*34);
			}
			
			texture = new Texture(roomMap);
			roomMap.dispose();
			TextureRegistry.registerSprite("Room" + width + "x" + height, texture);
		}
		
		return texture;
	}
	
	public Texture getRoomHighlight(int width, int height)
	{
		Texture texture = TextureRegistry.getTexture("RoomHighlight" + width + "x" + height);
		if(texture == null)
		{
			Pixmap roomMap = new Pixmap(width*35, height*35, Format.RGBA8888);
			roomMap.setColor(1, 1, 0, 1);
			roomMap.drawRectangle(2, 2, roomMap.getWidth()-4, roomMap.getHeight()-4);
			roomMap.drawRectangle(3, 3, roomMap.getWidth()-6, roomMap.getHeight()-6);
			roomMap.drawRectangle(4, 4, roomMap.getWidth()-8, roomMap.getHeight()-8);
			roomMap.setColor(1, 188/256f, 0, 1);
			roomMap.drawRectangle(5, 5, roomMap.getWidth()-10, roomMap.getHeight()-10);
			
			texture = new Texture(roomMap);
			roomMap.dispose();
			TextureRegistry.registerSprite("RoomHighlight" + width + "x" + height, texture);
		}
		
		return texture;
	}
}

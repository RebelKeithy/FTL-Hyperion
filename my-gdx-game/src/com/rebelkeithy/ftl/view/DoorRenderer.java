package com.rebelkeithy.ftl.view;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rebelkeithy.ftl.ship.Direction;
import com.rebelkeithy.ftl.ship.Door;

public class DoorRenderer 
{
	private static Map<Door, DoorRenderer> renderers = new HashMap<Door, DoorRenderer>();
	
	private int spriteState = 0;
	
	public static DoorRenderer getRenderer(Door door)
	{
		if(!renderers.containsKey(door))
		{
			renderers.put(door, new DoorRenderer());
		}
		
		return renderers.get(door);
	}
	
	public void render(SpriteBatch batch, Door door, int shipOffsetX, int shipOffsetY)
	{
		Texture texture = TextureRegistry.getTexture("doors");
				
		if(spriteState < 4 && (door.open || door.forceOpen || (door.getLink() != null && door.getLink().open)))
		{
			spriteState++;
			if(spriteState > 4)
				spriteState = 4;
		}
		if(spriteState > 0 && !(door.open || door.forceOpen || (door.getLink() != null && door.getLink().open)))
		{
			spriteState--;
			if(spriteState < 0)
				spriteState = 0;
		}

		int u = spriteState;
		int v = 0;
		
		v = door.room1.getShip().getSystem("doors").getPower() - 1;
		
		TextureRegion region = new TextureRegion(texture, u*35, v*35, 35, 35);
		
		int mouseX = Gdx.input.getX() - shipOffsetX;
		int mouseY = (Gdx.graphics.getHeight() - Gdx.input.getY()) - shipOffsetY;
		
		boolean hover = false;
		int shrinkH = 5;
		int shrinkV = 10;
		float doorAlpha = 0.75f;
		if(door.direction == Direction.RIGHT || door.direction == Direction.LEFT)
			if(mouseX > door.getX()*35 - 35/2 + shrinkV && mouseX < door.getX()*35 + 35/2 - shrinkV && mouseY > door.getY()*35 + shrinkH && mouseY < door.getY()*35 + 35 - shrinkH)
				hover = true;
		if(door.direction == Direction.UP || door.direction == Direction.DOWN)
			if(mouseX > door.getX()*35 + shrinkH && mouseX < door.getX()*35 + 35 - shrinkH && mouseY > door.getY()*35 - 35/2 + shrinkV && mouseY < door.getY()*35 + 35/2 - shrinkV)
				hover = true;
		
		if(FTLView.inputHandler.selected != null)
			hover = false;
		
		Texture doorHighlight = TextureRegistry.getTexture("door_highlight");
		if(door.direction == Direction.LEFT)
		{
			if(hover)
			{
				batch.setColor(1, 1, 1, doorAlpha);
				batch.draw(doorHighlight, shipOffsetX + (float)(door.getX()-0.5)*35 + 1, shipOffsetY + (float)(door.getY())*35);
				batch.setColor(1, 1, 1, 1);
			}
			batch.draw(region, shipOffsetX + (float)(door.getX()-0.5)*35 + 1, shipOffsetY + (float)(door.getY())*35);
		}
		if(door.direction == Direction.RIGHT)
		{
			if(hover)
			{
				batch.setColor(1, 1, 1, doorAlpha);
				if(door.getLink() == null)
					batch.draw(doorHighlight, shipOffsetX + (float)(door.getX()-0.5)*35 + 1, shipOffsetY + (float)(door.getY())*35);
				batch.setColor(1, 1, 1, 1);
			}
			batch.draw(region, shipOffsetX + (float)(door.getX()-0.5)*35 + 1, shipOffsetY + (float)(door.getY())*35);
		}
		if(door.direction == Direction.DOWN)
		{
			if(hover)
			{
				batch.setColor(1, 1, 1, doorAlpha);
				batch.draw(new TextureRegion(doorHighlight), shipOffsetX + (float)(door.getX())*35-1, shipOffsetY + (float)(door.getY() - 0.5)*35 + 1, 36/2, 36/2, 35, 35, 1, 1, 90);
				batch.setColor(1, 1, 1, 1);
			}
			batch.draw(region, shipOffsetX + (float)(door.getX())*35-1, shipOffsetY + (float)(door.getY() - 0.5)*35 + 1, 36/2, 36/2, 35, 35, 1, 1, 90);
		}
		if(door.direction == Direction.UP)
		{
			if(hover)
			{
				batch.setColor(1, 1, 1, doorAlpha);
				if(door.getLink() == null)
					batch.draw(new TextureRegion(doorHighlight), shipOffsetX + (float)(door.getX())*35-1, shipOffsetY + (float)(door.getY() - 0.5)*35 + 1, 36/2, 36/2, 35, 35, 1, 1, 90);
				batch.setColor(1, 1, 1, 1);
			}
			batch.draw(region, shipOffsetX + (float)(door.getX())*35-1, shipOffsetY + (float)(door.getY() - 0.5)*35 + 1, 36/2, 36/2, 35, 35, 1, 1, 90);
		}
	}
}

package com.rebelkeithy.ftl.view;

import com.badlogic.gdx.graphics.Texture;
import com.rebelkeithy.ftl.Clock;
import com.rebelkeithy.ftl.ship.Door;
import com.rebelkeithy.ftl.ship.Room;
import com.rebelkeithy.ftl.systems.AbstractShipSystem;

public class DoorOpenButton extends Button
{
	private AbstractShipSystem system;

	private long lastClick = -1;
	
	public DoorOpenButton(AbstractShipSystem system, int imageX, int imageY, int screenX, int screenY, int width, int height, Texture image_up) 
	{
		super(imageX, imageY, screenX, screenY, width, height, image_up);
		this.system = system;
	}

	public void leftClick()
	{
		if(lastClick == -1)
		{
			lastClick = System.currentTimeMillis();
		}
		else
		{
			Clock.log("" + (lastClick - System.currentTimeMillis()));
			if(System.currentTimeMillis() - lastClick < 100)
			{
				return;
			}
			else
			{
				lastClick = System.currentTimeMillis();
			}
		}
		
		boolean changed = false;
		for(Room room : system.getShip().getRooms().values())
		{
			for(Door door : room.getDoors())
			{
				if(door.room2 != null)
				{
					if(door.forceOpen == false)
					{
						changed = true;
					}
					door.forceOpen = true;
				}
			}
		}
		Clock.log("test " + changed);
		
		if(changed == false)
		{
			for(Room room : system.getShip().getRooms().values())
			{
				for(Door door : room.getDoors())
				{
					door.forceOpen = true;
				}
			}
		}
	}
}

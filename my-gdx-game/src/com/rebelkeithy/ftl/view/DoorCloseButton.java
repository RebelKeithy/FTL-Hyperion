package com.rebelkeithy.ftl.view;

import com.badlogic.gdx.graphics.Texture;
import com.rebelkeithy.ftl.ship.Door;
import com.rebelkeithy.ftl.ship.Room;
import com.rebelkeithy.ftl.systems.AbstractShipSystem;

public class DoorCloseButton extends Button
{
	private AbstractShipSystem system;

	public DoorCloseButton(AbstractShipSystem system, int imageX, int imageY, int screenX, int screenY, int width, int height, Texture image_up) 
	{
		super(imageX, imageY, screenX, screenY, width, height, image_up);
		this.system = system;
	}

	public void leftClick()
	{
		for(Room room : system.getShip().getRooms().values())
		{
			for(Door door : room.getDoors())
			{
				door.forceOpen = false;
			}
		}
	}
}

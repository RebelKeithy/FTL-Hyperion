package com.rebelkeithy.ftl.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rebelkeithy.ftl.systems.DoorsSystem;

public class DoorSystemRenderer extends SubSystemRenderer
{

	private Button doorButtonOpen;
	private Button doorButtonClose;
	
	private Texture doorsOpen;
	private Texture doorsOpenHover;
	private Texture doorsClose;
	private Texture doorsCloseHover;
	private Texture doorButtonBase;


	public DoorSystemRenderer(DoorsSystem doors, int systemX) 
	{
		super(doors, systemX);
		
		doorsOpen = TextureRegistry.registerSprite("button_door_top_on", "systemUI/button_door_top_on");
		doorsOpenHover = TextureRegistry.registerSprite("button_door_top_select2", "systemUI/button_door_top_select2");
		doorsClose = TextureRegistry.registerSprite("button_door_bottom_on", "systemUI/button_door_bottom_on");
		doorsCloseHover = TextureRegistry.registerSprite("button_door_bottom_select2", "systemUI/button_door_bottom_select2");
		doorButtonBase = TextureRegistry.registerSprite("button_door_base", "systemUI/button_door_base");
		
		doorButtonOpen = new DoorCloseButton(doors, systemX + 16, systemY + 2, systemX + 26, systemY + 51, 20, 20, doorsOpen);
		FTLView.inputHandler.registerButton(doorButtonOpen);
		doorButtonOpen.setHoverImage(doorsOpenHover);
		
		doorButtonClose = new DoorOpenButton(doors, systemX + 16, systemY + 2, systemX + 26, systemY + 27, 20, 20, doorsClose);
		FTLView.inputHandler.registerButton(doorButtonClose);
		doorButtonClose.setHoverImage(doorsCloseHover);
	}

	
	public void render(SpriteBatch batch)
	{
		super.render(batch);
		
		batch.draw(doorButtonBase, systemX + 16, systemY + 2);
		doorButtonOpen.render(batch);
		doorButtonClose.render(batch);
	}
	
	@Override
	public void setX(int newX)
	{
		doorButtonOpen.move(newX - systemX, 0);
		doorButtonClose.move(newX - systemX, 0);
		super.setX(newX);
	}
	
	@Override
	public void setY(int newY)
	{
		doorButtonOpen.move(0, newY - systemY);
		doorButtonClose.move(0, newY - systemY);
		super.setY(newY);
	}
	
	@Override
	public boolean click(int screenX, int screenY, int mouseButton) 
	{	

		if(doorButtonOpen != null && doorButtonOpen.containsPoint(screenX, screenY))
		{
			if(mouseButton == 0)
				doorButtonOpen.leftClick();
			if(mouseButton == 1)
				doorButtonOpen.rightClick();
			return true;
		}
		if(doorButtonClose != null && doorButtonClose.containsPoint(screenX, screenY))
		{
			if(mouseButton == 0)
				doorButtonClose.leftClick();
			if(mouseButton == 1)
				doorButtonClose.rightClick();
			return true;
		}
		
		return false;
	}
}

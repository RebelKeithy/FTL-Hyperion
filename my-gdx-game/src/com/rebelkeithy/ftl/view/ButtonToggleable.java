package com.rebelkeithy.ftl.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rebelkeithy.ftl.view.upgrade.Sounds;

public class ButtonToggleable extends Button
{
	boolean down = false;

	public ButtonToggleable(int imageX, int imageY, int screenX, int screenY, int width, int height, Texture image_up) 
	{
		super(imageX, imageY, screenX, screenY, width, height, image_up);
	}
	
	public ButtonToggleable(int i, int j, Texture image_up) 
	{
		super(i, j, image_up);
	}
	
	public void setSelected(boolean selected)
	{
		down = selected;
	}
	
	public void render(SpriteBatch batch)
	{
		if(disabled)
		{
			super.render(batch);
			return;
		}
		
		int mouseX = Gdx.input.getX();
		int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
		
		boolean oldHovering = hover;
		hover = containsPoint(mouseX, mouseY);
		if(hover == true && oldHovering == false)
		{
			Sounds.playSound("buttonHover");
		}

		if(down)
		{
			batch.draw(image_down, imageX, imageY);
		}
		else if(hover)
		{
			batch.draw(image_hover, imageX, imageY);
		}
		else
		{
			batch.draw(image_up, imageX, imageY);
		}
	}
	
}

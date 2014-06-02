package com.rebelkeithy.ftl.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rebelkeithy.ftl.view.upgrade.Sounds;

public class ButtonAutofire extends Button
{
	boolean down = false;

	public ButtonAutofire(int imageX, int imageY, int screenX, int screenY, int width, int height, Texture image_up) 
	{
		super(imageX, imageY, screenX, screenY, width, height, image_up);
	}
	
	public ButtonAutofire(int i, int j, Texture autofire_up) 
	{
		super(i, j, autofire_up);
	}

	public void leftClick()
	{
		down = !down;
	}
	
	public void render(SpriteBatch batch)
	{
		int mouseX = Gdx.input.getX();
		int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
		
		boolean oldHovering = hover;
		hover = containsPoint(mouseX, mouseY);
		if(hover == true && oldHovering == false)
		{
			Sounds.playSound("buttonHover");
		}

		if(hover)
		{
			batch.setColor(1, 1, 1, 1f);
			batch.draw(image_hover, imageX, imageY);
			batch.setColor(Color.WHITE);
		}
		if(down)
		{
			batch.draw(image_down, imageX, imageY);
		}
		else
		{
			batch.draw(image_up, imageX, imageY);
		}
	}
	
}

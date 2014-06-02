package com.rebelkeithy.ftl.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rebelkeithy.ftl.view.upgrade.Sounds;

public class Button 
{
	protected int imageX;
	protected int imageY;
	protected int screenX;
	protected int screenY;
	protected int width;
	protected int height;
	protected Texture image_up;
	protected Texture image_down;
	protected Texture image_hover;
	protected Texture image_disabled;
	
	protected boolean disabled;
	protected boolean hover;

	public Button(int imageX, int imageY, Texture image_up)
	{
		this.imageX = imageX;
		this.imageY = imageY;
		this.screenX = imageX;
		this.screenY = imageY;
		this.width = image_up.getWidth();
		this.height = image_up.getHeight();
		this.image_up = image_up;
		this.image_down = image_up;
		this.image_hover = image_up;
		this.image_disabled = image_up;
	}
	
	public Button(int imageX, int imageY, int screenX, int screenY, int width, int height, Texture image_up)
	{
		this.imageX = imageX;
		this.imageY = imageY;
		this.screenX = screenX;
		this.screenY = screenY;
		this.width = width;
		this.height = height;
		this.image_up = image_up;
		this.image_down = image_up;
		this.image_hover = image_up;
	}
	
	public boolean containsPoint(int x, int y)
	{
		return x >= screenX && x < screenX+width && y >= screenY && y < screenY+height;
	}
	
	public void setDisabled(boolean disabled)
	{
		this.disabled = disabled;
	}
	
	public boolean click(int x, int y, int button)
	{
		if(disabled)
			return false;
		
		if(containsPoint(x, y))
		{
			if(button == 0)
				leftClick();
			else if(button == 1)
				rightClick();
			
			return true;
		}
		
		return false;
	}
	
	public void rightClick()
	{
		
	}
	
	public void leftClick()
	{
		
	}
	
	public boolean isHovering()
	{
		int mouseX = Gdx.input.getX();
		int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
		
		return containsPoint(mouseX, mouseY);
	}
	
	public void render(SpriteBatch batch)
	{
		int mouseX = Gdx.input.getX();
		int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
		
		if(disabled)
		{
			batch.draw(image_disabled, imageX, imageY);
			return;
		}

		boolean oldHovering = hover;
		hover = containsPoint(mouseX, mouseY);
		if(hover == true && oldHovering == false)
		{
			Sounds.playSound("buttonHover");
		}
		
		if(hover)
		{
			if(Gdx.input.isButtonPressed(com.badlogic.gdx.Input.Buttons.LEFT))
				batch.draw(image_down, imageX, imageY);
			else
				batch.draw(image_hover, imageX, imageY);
		}
		else
		{
			batch.draw(image_up, imageX, imageY);
		}
	}

	public void setHoverImage(Texture texture) 
	{
		this.image_hover = texture;
	}

	public void setDownImage(Texture texture) 
	{
		this.image_down = texture;
	}

	public void setDisabledImage(Texture texture) 
	{
		this.image_disabled = texture;
	}

	public void move(int x, int y) 
	{
		imageX += x;
		screenX += x;
		imageY += y;
		screenY += y;
	}

	public void setPosition(int x, int y) 
	{
		this.move(x - screenX, y - screenY);
	}

	public void cancel() 
	{
		
	}

	public int getWidth() 
	{
		return width;
	}
}

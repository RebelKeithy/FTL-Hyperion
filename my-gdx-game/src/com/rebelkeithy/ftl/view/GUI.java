package com.rebelkeithy.ftl.view;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GUI 
{
	public List<Button> buttons;
	protected boolean show = true;
	
	public GUI()
	{
		buttons = new ArrayList<Button>();
	}
	
	public void show(boolean show)
	{
		this.show = show;
	}
	
	public boolean show()
	{
		return show;
	}
	
	public void addButton(Button button)
	{
		buttons.add(button);
	}
	
	public void render(SpriteBatch batch)
	{
		
	}

	public void close() 
	{
		
	}

	public boolean click(int screenX, int screenY, int button) 
	{
		if(!show)
			return false;
		
		for(Button b : buttons)
		{
			if(b.click(screenX, screenY, button))
				return true;
		}
		
		return false;
	}

	public void accept() 
	{
		
	}
}

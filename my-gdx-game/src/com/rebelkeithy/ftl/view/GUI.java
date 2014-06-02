package com.rebelkeithy.ftl.view;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GUI 
{
	public List<Button> buttons;
	
	public GUI()
	{
		buttons = new ArrayList<Button>();
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

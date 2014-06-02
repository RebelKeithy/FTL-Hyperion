package com.rebelkeithy.ftl.view.upgrade;

import com.badlogic.gdx.graphics.Texture;
import com.rebelkeithy.ftl.view.Button;

public class UndoButton extends Button 
{
	private UpgradeUI gui;

	public UndoButton(UpgradeUI gui, int imageX, int imageY, Texture image_up) 
	{
		super(imageX, imageY, image_up);
	
		this.gui = gui;
	}

	
	public UndoButton(int i, int j, Texture acceptUp) 
	{
		super(i, j, acceptUp);
	}


	public void leftClick()
	{
		gui.undo();
	}
}

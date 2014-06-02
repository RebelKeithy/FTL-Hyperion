package com.rebelkeithy.ftl.view.scene.mainmenu;

import com.badlogic.gdx.graphics.Texture;
import com.rebelkeithy.ftl.view.Button;

public class QuitButton extends Button
{
	public QuitButton(int imageX, int imageY, Texture image_up) 
	{
		super(imageX, imageY, image_up);
	}

	@Override
	public void leftClick()
	{
		System.exit(0);
	}
}

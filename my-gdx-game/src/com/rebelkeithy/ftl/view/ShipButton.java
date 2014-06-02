package com.rebelkeithy.ftl.view;

import com.badlogic.gdx.graphics.Texture;
import com.rebelkeithy.ftl.view.scene.GameScreen;

public class ShipButton extends Button
{

	public ShipButton(int imageX, int imageY, int screenX, int screenY, int width, int height, Texture image_up) 
	{
		super(imageX, imageY, screenX, screenY, width, height, image_up);
	}

	public void leftClick()
	{
		if(FTLView.instance().getScreen() instanceof GameScreen)
			((GameScreen)FTLView.instance().getScreen()).openUpgradeGUI();
		
	}
}

package com.rebelkeithy.ftl.view.scene.hangar;

import com.badlogic.gdx.graphics.Texture;
import com.rebelkeithy.ftl.view.Button;
import com.rebelkeithy.ftl.view.FTLView;
import com.rebelkeithy.ftl.view.scene.GameScreen;

public class StartButton extends Button
{
	public StartButton(int imageX, int imageY, Texture image_up) 
	{
		super(imageX, imageY, image_up);
	}

	public void leftClick()
	{
		FTLView.instance().setScreen(new GameScreen());
	}
}

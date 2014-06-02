package com.rebelkeithy.ftl.view.scene.mainmenu;

import com.badlogic.gdx.graphics.Texture;
import com.rebelkeithy.ftl.view.Button;
import com.rebelkeithy.ftl.view.FTLView;
import com.rebelkeithy.ftl.view.scene.hangar.HangerScreen;

public class NewGameButton extends Button
{

	public NewGameButton(int imageX, int imageY, Texture image_up) 
	{
		super(imageX, imageY, image_up);
	}
	
	public void leftClick()
	{
		FTLView.instance().setScreen(new HangerScreen());
	}
}

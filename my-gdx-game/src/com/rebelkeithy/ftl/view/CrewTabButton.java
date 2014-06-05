package com.rebelkeithy.ftl.view;

import com.badlogic.gdx.graphics.Texture;
import com.rebelkeithy.ftl.FTLGame;
import com.rebelkeithy.ftl.view.crew.CrewUI;
import com.rebelkeithy.ftl.view.scene.SpaceScreen;

public class CrewTabButton extends Button
{
	public CrewTabButton(int imageX, int imageY, Texture image_up) 
	{
		super(imageX, imageY, image_up);
	}

	@Override
	public void leftClick()
	{
		if(FTLView.instance().getScreen() instanceof SpaceScreen)
		{
			((SpaceScreen)FTLView.instance().getScreen()).openGUI(new CrewUI(FTLGame.instance().getPlayer()));
		}
	}
}

package com.rebelkeithy.ftl.view;

import com.badlogic.gdx.graphics.Texture;
import com.rebelkeithy.ftl.FTLGame;
import com.rebelkeithy.ftl.view.scene.SpaceScreen;
import com.rebelkeithy.ftl.view.upgrade.UpgradeUI;

public class UpgradesTabButton extends Button
{
	public UpgradesTabButton(int imageX, int imageY, Texture image_up) 
	{
		super(imageX, imageY, image_up);
	}

	@Override
	public void leftClick()
	{
		if(FTLView.instance().getScreen() instanceof SpaceScreen)
			((SpaceScreen)FTLView.instance().getScreen()).openGUI(new UpgradeUI(FTLGame.instance().getPlayer()));
	}
}

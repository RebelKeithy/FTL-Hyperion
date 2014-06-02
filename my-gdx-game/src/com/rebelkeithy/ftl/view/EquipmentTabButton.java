package com.rebelkeithy.ftl.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.rebelkeithy.ftl.FTLGame;
import com.rebelkeithy.ftl.view.equipment.EquipmentUI;
import com.rebelkeithy.ftl.view.scene.GameScreen;

public class EquipmentTabButton extends Button
{
	public EquipmentTabButton(int imageX, int imageY, Texture image_up) 
	{
		super(imageX, imageY, image_up);
	}

	@Override
	public void leftClick()
	{
		if(FTLView.instance().getScreen() instanceof GameScreen)
			((GameScreen)FTLView.instance().getScreen()).openGUI(new EquipmentUI(FTLGame.instance().getPlayer()));
	}
}

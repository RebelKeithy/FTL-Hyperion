package com.rebelkeithy.ftl.view.scene.hangar;

import com.badlogic.gdx.graphics.Texture;
import com.rebelkeithy.ftl.FTLGame;
import com.rebelkeithy.ftl.view.Button;
import com.rebelkeithy.ftl.view.FTLView;
import com.rebelkeithy.ftl.view.scene.GameScreen;

public class StartButton extends Button
{
	private HangerScreen hanger;
	
	public StartButton(HangerScreen hanger, int imageX, int imageY, Texture image_up) 
	{
		super(imageX, imageY, image_up);
		this.hanger = hanger;
	}

	public void leftClick()
	{
		FTLGame.instance().setPlayer(hanger.getShip());
		FTLView.instance().setScreen(new GameScreen());
	}
}

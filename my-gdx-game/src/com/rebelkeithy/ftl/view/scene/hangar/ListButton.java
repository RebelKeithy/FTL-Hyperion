package com.rebelkeithy.ftl.view.scene.hangar;

import com.badlogic.gdx.graphics.Texture;
import com.rebelkeithy.ftl.view.Button;

public class ListButton extends Button
{
	private HangerScreen hanger;
	
	public ListButton(HangerScreen hanger, int imageX, int imageY, Texture image_up) 
	{
		super(imageX, imageY, image_up);
		
		this.hanger = hanger;
	}

	public void leftClick()
	{
		hanger.showListGUI();
	}
}

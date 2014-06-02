package com.rebelkeithy.ftl.view.upgrade;

import com.badlogic.gdx.graphics.Texture;
import com.rebelkeithy.ftl.view.Button;
import com.rebelkeithy.ftl.view.FTLView;
import com.rebelkeithy.ftl.view.GUI;
import com.rebelkeithy.ftl.view.scene.GameScreen;

public class AcceptButton extends Button 
{
	private GUI gui;

	public AcceptButton(GUI gui, int imageX, int imageY, Texture image_up) 
	{
		super(imageX, imageY, image_up);
	
		this.gui = gui;
	}

	
	public AcceptButton(int i, int j, Texture acceptUp) 
	{
		super(i, j, acceptUp);
	}


	public void leftClick()
	{
		gui.accept();
		
		if(FTLView.instance().getScreen() instanceof GameScreen)
			((GameScreen)FTLView.instance().getScreen()).closeGui();
	}
}

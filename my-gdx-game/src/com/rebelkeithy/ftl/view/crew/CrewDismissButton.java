package com.rebelkeithy.ftl.view.crew;

import com.badlogic.gdx.graphics.Texture;
import com.rebelkeithy.ftl.FTLGame;
import com.rebelkeithy.ftl.crew.Crew;
import com.rebelkeithy.ftl.view.Button;

public class CrewDismissButton extends Button
{
	private int crewID;

	public CrewDismissButton(int crewID, int imageX, int imageY, Texture image_up) 
	{
		super(imageX, imageY, image_up);
		
		this.crewID = crewID;
	}

	public void leftClick()
	{
		// dismiss crew
		Crew crew = FTLGame.instance().getPlayer().getCrew().get(crewID);
	}
}

package com.rebelkeithy.ftl.view.scene.hangar;

import com.badlogic.gdx.graphics.Texture;
import com.rebelkeithy.ftl.view.ButtonToggleable;

public class DifficultyButton extends ButtonToggleable
{
	private HangerScreen screen;
	private int difficulty;

	public DifficultyButton(HangerScreen screen, int difficulty, int i, int j, Texture autofire_up) 
	{
		super(i, j, autofire_up);
		this.screen = screen;
		this.difficulty = difficulty;
	}
	
	public void leftClick()
	{
		screen.setDifficulty(difficulty);
	}
	
}

package com.rebelkeithy.ftl.actions;

import com.rebelkeithy.ftl.FTLGame;

public abstract class Action 
{
	protected FTLGame game;
	
	public Action(String action)
	{
		this.game = FTLGame.instance();
		game.registerAction(action, this);
	}
	
	public abstract void preform(String action, Object... params);
}
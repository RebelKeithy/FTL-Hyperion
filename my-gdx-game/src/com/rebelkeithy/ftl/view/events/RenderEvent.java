package com.rebelkeithy.ftl.view.events;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RenderEvent 
{
	public SpriteBatch batch;
	
	public RenderEvent(SpriteBatch batch)
	{
		this.batch = batch;
	}
}

package com.rebelkeithy.ftl.view.events;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RenderScrap extends RenderEvent
{
	public int scrap;
	
	public RenderScrap(SpriteBatch batch, int scrap)
	{
		super(batch);
		this.scrap = scrap;
	}
}

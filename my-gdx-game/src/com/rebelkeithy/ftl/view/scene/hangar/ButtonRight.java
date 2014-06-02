package com.rebelkeithy.ftl.view.scene.hangar;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rebelkeithy.ftl.view.Button;

public class ButtonRight extends Button
{

	public ButtonRight(int imageX, int imageY, Texture image_up) 
	{
		super(imageX, imageY, image_up);
	}
	
	@Override
	protected void render(SpriteBatch batch, Texture texture, int x, int y)
	{
		TextureRegion region = new TextureRegion(texture);
		region.flip(true, false);
		
		batch.draw(region, x, y);
	}
}

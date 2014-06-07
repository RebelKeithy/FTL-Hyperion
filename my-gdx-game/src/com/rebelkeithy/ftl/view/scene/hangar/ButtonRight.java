package com.rebelkeithy.ftl.view.scene.hangar;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rebelkeithy.ftl.view.Button;

public class ButtonRight extends Button
{
	private HangerScreen hanger;
	
	public ButtonRight(HangerScreen hanger, int imageX, int imageY, Texture image_up) 
	{
		super(imageX, imageY, image_up);
		this.hanger = hanger;
	}
	
	@Override
	protected void render(SpriteBatch batch, Texture texture, int x, int y)
	{
		TextureRegion region = new TextureRegion(texture);
		region.flip(true, false);
		
		batch.draw(region, x, y);
	}
	
	public void leftClick()
	{
		hanger.nextShip();
	}
}

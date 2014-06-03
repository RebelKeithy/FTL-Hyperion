package com.rebelkeithy.ftl.view.scene.hangar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rebelkeithy.ftl.systems.AbstractShipSystem;
import com.rebelkeithy.ftl.view.SystemInfoRenderer;
import com.rebelkeithy.ftl.view.TextureRegistry;

public class HangerSystemRenderer 
{
	private Texture backgroundOn;
	private Texture backgroundSelected;
	private Texture white;
	private Texture icon;
	private AbstractShipSystem system;
	private SystemInfoRenderer infoRenderer;
	
	public HangerSystemRenderer(AbstractShipSystem system)
	{
		this.system = system;
		infoRenderer = new SystemInfoRenderer();
		backgroundOn = TextureRegistry.registerSprite("box_system_on", "customizeUI/box_system_on");
		backgroundSelected = TextureRegistry.registerSprite("box_system_select2", "customizeUI/box_system_select2");
		icon = TextureRegistry.getTexture("system_" + system.getName());
		white = TextureRegistry.getTexture("white");
	}
	
	public void render(SpriteBatch batch, int offsetX, int offsetY)
	{
		int mouseX = Gdx.input.getX();
		int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
		
		boolean hover = mouseX > offsetX && mouseX < offsetX + 38 && mouseY > offsetY && mouseY < offsetY + 76;
		
		if(hover)
		{
			batch.draw(backgroundSelected, offsetX, offsetY);
			infoRenderer.render(batch, system, 0, 945, 250);
		}
		else
			batch.draw(backgroundOn, offsetX, offsetY);
		
		batch.draw(icon, offsetX - 13, offsetY - 13);
		batch.setColor(100/256f, 1, 100/256f, 1);
		for(int i = 0; i < system.getMaxPower(); i++)
		{
			batch.draw(white, offsetX + 11, offsetY + 37 + i * 8, 16, 6);
		}
		batch.setColor(Color.WHITE);
		
		
	}
}

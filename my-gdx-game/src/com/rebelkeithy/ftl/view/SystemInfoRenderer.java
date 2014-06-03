package com.rebelkeithy.ftl.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rebelkeithy.ftl.systems.AbstractShipSystem;

public class SystemInfoRenderer 
{
	private Texture details;
	private Texture detailsOn;
	private Texture detailsOff;
	private Texture bar;
	
	public SystemInfoRenderer()
	{
		details = TextureRegistry.registerSprite("system_details", "upgradeUI/details_base");
		detailsOn = TextureRegistry.registerSprite("details_bar_on", "UpgradeUI/details_bar_on");
		detailsOff = TextureRegistry.registerSprite("details_bar_off", "UpgradeUI/details_bar_off");
		bar = TextureRegistry.getTexture("white");
	}

	public void render(SpriteBatch batch, AbstractShipSystem system, int upgradeAmount, int offsetX, int offsetY)
	{
		Window.drawWindow(batch, offsetX, offsetY + 238, 337, 135);
		Fonts.ccNewFont.setColor(Color.WHITE);
		Fonts.ccNewBigFont.draw(batch, system.getDisplayName(), offsetX + 18, offsetY + 350);
		Fonts.font10.drawMultiLine(batch, system.getDescription(),offsetX + 18, offsetY + 323);
		
		for(int i = 0; i < 8; i++)
		{
			if(i < system.getMaxUpgradeLevel() || !system.getUpgradeDescription(i).equals(""))
				batch.draw(detailsOn, offsetX + 75, offsetY + 17 + i * 26);
			else
				batch.draw(detailsOff, offsetX + 75, offsetY + 17 + i * 26);
		}
		
		batch.draw(details, offsetX, offsetY);
		
		for(int i = 0; i < system.getMaxUpgradeLevel(); i++)
		{
			if(system.getMaxPower() <= i)
				Fonts.numFont.draw(batch, "" + system.getUpgradeCost(i), offsetX + 105, offsetY + 36 + i * 26);
			
			if(system.getMaxPower() + upgradeAmount <= i)
				batch.setColor(104/256f, 97/256f, 58/256f, 1);
			else if(system.getMaxPower() <= i)
				batch.setColor(1, 1, 100/256f, 1);
			else
				batch.setColor(100/256f, 1, 100/256f, 1);

			batch.draw(bar, offsetX + 27, offsetY + 20 + i * 26, 28, 18);
		}
		batch.setColor(Color.WHITE);
		
		for(int i = 0; i < 8; i++)
		{
			if(i >= system.getMaxUpgradeLevel() && !system.getUpgradeDescription(i).equals(""))
				Fonts.numFont.draw(batch, "-", offsetX + 111, offsetY + 36 + i * 26);
			
			Fonts.font10.draw(batch, system.getUpgradeDescription(i), offsetX + 149, offsetY + 34 + i * 26);
		}
	}
}

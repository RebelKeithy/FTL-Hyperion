package com.rebelkeithy.ftl.view.upgrade;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rebelkeithy.ftl.systems.AbstractShipSystem;
import com.rebelkeithy.ftl.view.Button;
import com.rebelkeithy.ftl.view.Fonts;
import com.rebelkeithy.ftl.view.SystemInfoRenderer;
import com.rebelkeithy.ftl.view.TextureRegistry;

public class SystemButton extends Button
{
	private UpgradeUI gui;
	private AbstractShipSystem system;
	private SystemInfoRenderer infoRenderer;
	private Texture image_max_up;
	private Texture image_max_select;
	
	private Texture icon;
	private Texture bar;
	
	private int upgradeAmount = 0;
	private int scrapCost = 0;
	
	public SystemButton(UpgradeUI gui, AbstractShipSystem system, int imageX, int imageY, Texture image_up, Texture image_select, Texture image_max_up, Texture image_max_select)
	{
		super(imageX, imageY, image_up);
		
		this.gui = gui;
		this.system = system;
		infoRenderer = new SystemInfoRenderer();
		
		this.image_down = image_select;
		this.image_hover = image_select;
		
		this.image_max_up = image_max_up;
		this.image_max_select = image_max_select;
		
		icon = TextureRegistry.getTexture("system_" + system.getName());
		bar = TextureRegistry.getTexture("upgradeSystemBar");
		
		if(bar == null)
		{
			Pixmap map = new Pixmap(15, 6, Format.RGBA8888);
			map.setColor(Color.WHITE);
			map.fill();
			bar = new Texture(map);
			TextureRegistry.registerSprite("upgradeSystemBar", bar);
		}
	}
	
	public void render(SpriteBatch batch)
	{
		int mouseX = Gdx.input.getX();
		int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
		
		boolean oldHovering = hover;
		hover = containsPoint(mouseX, mouseY);
		if(hover == true && oldHovering == false)
		{
			Sounds.playSound("buttonHover");
		}

		if(hover)
		{
			if(system.getMaxPower() + upgradeAmount == system.getMaxUpgradeLevel())
				batch.draw(image_max_select, imageX, imageY);
			else
				batch.draw(image_hover, imageX, imageY);
			
			infoRenderer.render(batch, system, upgradeAmount, 940, 195);
		}
		else
		{
			if(system.getMaxPower() + upgradeAmount == system.getMaxUpgradeLevel())
				batch.draw(image_max_up, imageX, imageY);
			else
				batch.draw(image_up, imageX, imageY);
		}
		
		batch.draw(icon, imageX, imageY + 20);
		for(int i = 0; i < system.getMaxUpgradeLevel(); i++)
		{
			if(i < system.getMaxPower())
				batch.setColor(100/256f, 1, 100/256f, 1);
			else if(i < system.getMaxPower() + upgradeAmount)
				batch.setColor(1, 1, 100/256f, 1);
			else
			{
				batch.setColor(104/256f, 97/256f, 58/256f, 1);
				if(isHovering())
					batch.setColor(164/256f, 146/256f, 108/256f, 1);
			}
			batch.draw(bar, imageX + 24, imageY + 70 + 8*i);
			batch.setColor(Color.WHITE);
		}

		if(system.getMaxPower() + upgradeAmount < system.getMaxUpgradeLevel())
		{
			float width = Fonts.numFont.getBounds("" + system.getNextUpgradeCost()).width;
			Fonts.numFont.draw(batch, "" + system.getUpgradeCost(system.getMaxPower() + upgradeAmount), imageX + 42 - width/2, imageY + 22);
		}
	}
	
	public void leftClick()
	{
		if(system.getMaxPower() + upgradeAmount < system.getMaxUpgradeLevel())
		{
			if(system.getUpgradeCost(system.getMaxPower() + upgradeAmount) <= gui.getAvaliableScrap())
			{
				scrapCost += system.getUpgradeCost(system.getMaxPower() + upgradeAmount);
				upgradeAmount++;
				
				Sounds.playSound("systemUpgrade");
			}
		}
	}
	
	public void rightClick()
	{
		if(upgradeAmount > 0)
		{
			upgradeAmount--;
			scrapCost -= system.getUpgradeCost(system.getMaxPower() + upgradeAmount);
			
			Sounds.playSound("buttonOff");
		}
	}

	public void cancel() 
	{
		scrapCost = 0;
		upgradeAmount = 0;
	}

	public int cost() 
	{
		return scrapCost;
	}

	public void apply() 
	{
		for(int i = 0; i < upgradeAmount; i++)
		{
			system.upgrade();
		}
	}
	
	

}

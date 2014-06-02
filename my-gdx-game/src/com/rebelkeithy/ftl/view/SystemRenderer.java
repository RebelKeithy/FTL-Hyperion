package com.rebelkeithy.ftl.view;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rebelkeithy.ftl.systems.AbstractShipSystem;

public class SystemRenderer 
{
	private static Map<String, SystemRenderer> renderers = new HashMap<String, SystemRenderer>();
	
	protected AbstractShipSystem system;
	protected int systemX;
	protected int systemY = 24;
	protected SystemButton button = null;

	protected Texture unmanned;
	protected Texture manned1;
	protected Texture manned2;
	protected Texture manned3;
	
	protected Texture powerSlotPowered;
	protected Texture powerSlotEmpty;
	protected Texture powerSlotGlow;
	
	public static void RegisterSystemRenderer(String name, SystemRenderer renderer)
	{
		renderers.put(name, renderer);
	}
	
	public SystemRenderer(AbstractShipSystem system, int systemX)
	{
		this.system = system;
		this.systemX = systemX;
		
		createButton();
		
		unmanned = TextureRegistry.registerSprite("unmanned", "systemUI/manning_outline");
		manned1 = TextureRegistry.registerSprite("unmanned", "systemUI/manning_white");
		manned2 = TextureRegistry.registerSprite("unmanned", "systemUI/manning_green");
		manned3 = TextureRegistry.registerSprite("unmanned", "systemUI/manning_yellow");
		
		powerSlotEmpty = TextureRegistry.getTexture("powerSlotEmpty");
		powerSlotPowered = TextureRegistry.getTexture("powerSlotPowered");
		powerSlotGlow = TextureRegistry.registerSprite("powerSlotGlow", "icons/bar_backgroundglow");
	}
	
	protected void createButton()
	{
		Texture systemIconOn = TextureRegistry.getTexture("system_" + system.getName());
		Texture systemIconOff = TextureRegistry.getTexture("system_" + system.getName() + "_off");
		Texture systemIconOver = TextureRegistry.getTexture("system_" + system.getName() + "_over");
		Texture systemIconOffOver = TextureRegistry.getTexture("system_" + system.getName() + "_off_over");
		Texture systemIconGlow = TextureRegistry.getTexture("system_" + system.getName() + "_glow");
		button = new SystemButton(system, systemX - 19, systemY - 19, systemX + 1, systemY + 1, 26, 26, systemIconOn);
		button.setHoverImage(systemIconOver);
		button.setGlowTexture(systemIconGlow);
		button.setOffTextures(systemIconOff, systemIconOffOver);
		FTLView.inputHandler.registerButton(button);
	}
	
	public void render(SpriteBatch batch)
	{
		if(!system.alwaysPowered())
		{
			button.render(batch);
		}
		else
		{
			Texture systemIcon = TextureRegistry.getTexture("system_" + system.getName());
			if(system.getPower() == 0)
				systemIcon = TextureRegistry.getTexture("system_" + system.getName() + "_off");
			batch.draw(systemIcon, systemX - 19, systemY - 19);
		}
		
		
		
		for(int i = 0; i < system.getMaxPower(); i++)
		{
			drawPowerBar(batch, i);
			
			if(i == system.getMaxPower() - 1)
			{
				drawMannIcon(batch);
			}
			if(button != null && button.isHovering())
			{
				batch.draw(powerSlotGlow, systemX-19 + 15, systemY + 19 + i*8);
			}
		}
	}
	
	public void drawPowerBar(SpriteBatch batch, int i)
	{
		if(system.getPower() > i)
			batch.draw(powerSlotPowered, systemX-19 + 24, systemY + 31 + i*8);
		else
			batch.draw(powerSlotEmpty, systemX-19 + 24, systemY + 31 + i*8);
	}
	
	public void drawMannIcon(SpriteBatch batch)
	{
		if(system.canMann() && system.getPower() > 0)
		{
			if(!system.isManned())
				batch.draw(unmanned, systemX-19 + 25, systemY + 29 + system.getMaxPower()*8);
			else
				batch.draw(manned1, systemX-19 + 25, systemY + 29 + system.getMaxPower()*8);
		}
	}
	
	public int getX()
	{
		return systemX;
	}
	
	public void setX(int newX)
	{
		if(button != null)
			button.move(newX - systemX, 0);
		this.systemX = newX;
	}
	
	public int getWidth()
	{
		return 36;
	}

	public void setY(int newY)
	{
		if(button != null)
			button.move(0, newY - systemY);
		systemY = newY;
	}

	public boolean click(int screenX, int screenY, int mouseButton) 
	{	
		if(button != null && button.containsPoint(screenX, screenY))
		{
			if(mouseButton == 0)
				button.leftClick();
			if(mouseButton == 1)
				button.rightClick();
			return true;
		}
		
		return false;
	}
	
}

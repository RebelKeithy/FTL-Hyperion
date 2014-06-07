package com.rebelkeithy.ftl.crew;

import java.util.HashMap;
import java.util.Map;

import com.rebelkeithy.ftl.view.TextureRegistry;

public class Race
{
	public String texture;
	public String glowTexture;
	public Map<String, Integer[]>animations = new HashMap<String, Integer[]>();
	public int health;
	public double attack;
	public double repair;
	public double speed;
	
	public void loadTextures() 
	{
		TextureRegistry.registerSprite(texture, texture);
		TextureRegistry.registerSprite(glowTexture, glowTexture);
	}
}
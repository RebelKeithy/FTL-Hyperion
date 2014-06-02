package com.rebelkeithy.ftl.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Window 
{
	private static TextureRegion top_left;
	private static TextureRegion top_right;
	private static TextureRegion bot_left;
	private static TextureRegion bot_right;
	private static TextureRegion top;
	private static TextureRegion bot;
	private static TextureRegion left;
	private static TextureRegion right;
	private static TextureRegion middle;
	
	static
	{
		init();
	}
	
	public static void init()
	{
		Texture window = TextureRegistry.registerSprite("window_alpha", "window_base_alpha");
		
		top_left = new TextureRegion(window, 0, 0, 28, 28);
		top_right = new TextureRegion(window, 38, 0, 28, 28);
		bot_left = new TextureRegion(window, 0, 45, 28, 27);
		bot_right = new TextureRegion(window, 38, 45, 28, 27);
		
		top = new TextureRegion(window, 28, 0, 10, 28);
		bot = new TextureRegion(window, 28, 45, 10, 27);
		left = new TextureRegion(window, 0, 28, 28, 17);
		right = new TextureRegion(window, 38, 28, 28, 17);
		
		middle = new TextureRegion(window, 28, 28, 10, 17);
	}
	
	// for window to draw correctly, width must be 56 + 10a and height must be 55 + 17b for some integers a and b.
	public static void drawWindow(SpriteBatch batch, int x, int y, int width, int height)
	{
		for(int i = x + 28; i < x + width - 28; i += 10)
		{
			for(int j = y + 27; j < y + height - 28; j += 17)
			{
				batch.draw(middle, i, j);
			}
		}
		
		for(int i = x + 28; i < x + width - 28; i += 10)
		{
			batch.draw(top, i, y + height - 28);
			batch.draw(bot, i, y);
		}
		for(int i = y + 27; i < y + height - 28; i += 17)
		{
			batch.draw(left, x, i);
			batch.draw(right, x + width - 28, i);
		}
		
		batch.draw(bot_left, x, y);
		batch.draw(bot_right, x + width - 28, y);
		batch.draw(top_left, x, y + height - 28);
		batch.draw(top_right, x + width - 28, y + height - 28);
	}
}

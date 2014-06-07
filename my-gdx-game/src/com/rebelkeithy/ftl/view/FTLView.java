package com.rebelkeithy.ftl.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.google.common.eventbus.EventBus;
import com.rebelkeithy.ftl.FTLGame;
import com.rebelkeithy.ftl.view.scene.FTLScreen;
import com.rebelkeithy.ftl.view.scene.MainMenu;
import com.rebelkeithy.ftl.view.upgrade.Sounds;

public class FTLView extends Game 
{
	
	
	public static InputHandler inputHandler;
	
	public EventBus RENDER_BUS;
	private static FTLView instance;

	@Override
	public void create()
	{		
		ResourceExtractor.extract();

		FTLGame gameClient = new FTLGame();
		gameClient.init();
		gameClient.generate();
		
		instance = this;
		RENDER_BUS = new EventBus();
		//ResourceExtractor.extract();
		
		inputHandler = new InputHandler();
		Gdx.input.setInputProcessor(inputHandler);

		Sounds.registerSound("buttonHover", "ui/select_light1.wav");
		Sounds.registerSound("buttonOn", "ui/select_up1.wav");
		Sounds.registerSound("buttonOff", "ui/select_down2.wav");
		Sounds.registerSound("buttonFail", "ui/select_b_fail1.wav");
		Sounds.registerSound("systemUpgrade", "ui/bp_upgrade_1.ogg");
		
		TextureRegistry.registerSprite("background", "stars/bg_lonelystar");
		TextureRegistry.registerSprite("doors", "effects/door_sheet");
		TextureRegistry.registerSprite("door_highlight", "effects/door_highlight");
		
		Fonts.init();
		
		registerNumbers();
		registerTextures();
		
		this.setScreen(new MainMenu());
	}
	
	public static FTLView instance()
	{
		return instance;
	}
	
	public void registerNumbers()
	{
		for(int i = 0; i < 10; i++)
			TextureRegistry.registerSprite("" + i, "numbers/Text_" + i + "_L");
	}
	
	public void registerTextures()
	{
		/*
		String[] systems = new String[] {"pilot", "engines", "oxygen", "weapons", "medbay", "shields", "doors", "sensors" };
		String[] names = new String[] {"pilot", "engines", "oxygen", "weapons", "medbay", "shields", "doors", "sensors" };
		
		for(int i = 0; i < systems.length; i++)
		{
			TextureRegistry.registerSprite("room_system_icon_" + systems[i], "icons/s_" + names[i] + "_overlay");
			TextureRegistry.registerSprite("system_" + systems[i], "icons/s_" + names[i] + "_green1");
			TextureRegistry.registerSprite("system_" + systems[i] + "_over", "icons/s_" + names[i] + "_green2");
			TextureRegistry.registerSprite("system_" + systems[i] + "_off", "icons/s_" + names[i] + "_grey1");
			TextureRegistry.registerSprite("system_" + systems[i] + "_off_over", "icons/s_" + names[i] + "_grey2");
			TextureRegistry.registerSprite("system_" + systems[i] + "_glow", "icons/s_" + names[i]);
		}
		*/

		
		Pixmap pixmap = new Pixmap(28, 7, Format.RGBA8888);
		pixmap.setColor(1, 1, 1, 1);
		pixmap.drawRectangle(0, 0, 28, 7 );
		TextureRegistry.registerSprite("powerSlotEmptyLarge", new Texture(pixmap));
		pixmap.dispose();
		
		pixmap = new Pixmap(28, 7, Format.RGBA8888);
		pixmap.setColor(100/256f, 1, 100/256f, 1);
		pixmap.fill(); 
		TextureRegistry.registerSprite("powerSlotPoweredLarge", new Texture(pixmap));
		pixmap.dispose();
		
		pixmap = new Pixmap(16, 6, Format.RGBA8888);
		pixmap.setColor(1, 1, 1, 1);
		pixmap.drawRectangle(0, 0, 16, 6 );
		TextureRegistry.registerSprite("powerSlotEmpty", new Texture(pixmap));
		pixmap.dispose();
		
		pixmap = new Pixmap(16, 6, Format.RGBA8888);
		pixmap.setColor(100/256f, 1, 100/256f, 1);
		pixmap.fill(); 
		TextureRegistry.registerSprite("powerSlotPowered", new Texture(pixmap));
		pixmap.dispose();

		pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.BLACK);
		pixmap.fill(); 
		TextureRegistry.registerSprite("black", new Texture(pixmap));
		pixmap.dispose();

		pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill(); 
		TextureRegistry.registerSprite("white", new Texture(pixmap));
		pixmap.dispose();
	}

	public void setTooltipText(String text, int x, int y, int width, int height) 
	{
		((FTLScreen)getScreen()).setTooltipText(text, x, y, width, height);
	}
}

package com.rebelkeithy.ftl.view.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rebelkeithy.ftl.view.Button;
import com.rebelkeithy.ftl.view.TextureRegistry;
import com.rebelkeithy.ftl.view.scene.mainmenu.NewGameButton;
import com.rebelkeithy.ftl.view.scene.mainmenu.QuitButton;

public class MainMenu implements FTLScreen
{
	public OrthographicCamera camera;
	private SpriteBatch batch;
	
	private Texture background;
	private Button bContinue;
	private Button bNew;
	private Button bTutorial;
	private Button bStats;
	private Button bOptions;
	private Button bCredits;
	private Button bQuit;
	
	
	public MainMenu()
	{
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera(w, h);
		camera.position.x += w/2;
		camera.position.y += h/2;
		camera.update();
		batch = new SpriteBatch();
		
		background = TextureRegistry.registerSprite("main_base2", "main_menus/main_base2");
		
		Texture continueOn = TextureRegistry.registerSprite("continue_on", "main_menus/continue_on");
		Texture continueOff = TextureRegistry.registerSprite("continue_off", "main_menus/continue_off");
		Texture continueSelected = TextureRegistry.registerSprite("continue_select2", "main_menus/continue_select2");
		bContinue = new Button(1202 - continueOn.getWidth(), 395, continueOn);
		bContinue.setHoverImage(continueSelected);
		bContinue.setDownImage(continueSelected);
		bContinue.setDisabledImage(continueOff);
		bContinue.setDisabled(true);
		
		Texture newOn = TextureRegistry.registerSprite("start_on", "main_menus/start_on");
		Texture newSelected = TextureRegistry.registerSprite("start_select2", "main_menus/start_select2");
		bNew = new NewGameButton(1201 - newOn.getWidth(), 337, newOn);
		bNew.setHoverImage(newSelected);
		bNew.setDownImage(newSelected);
		
		Texture tutorialOn = TextureRegistry.registerSprite("tutorial_on", "main_menus/tutorial_on");
		Texture tutorialOff = TextureRegistry.registerSprite("tutorial_off", "main_menus/tutorial_off");
		Texture tutorialSelect = TextureRegistry.registerSprite("tutorial_select2", "main_menus/tutorial_select2");
		bTutorial = new Button(500, 300, tutorialOn);
		bTutorial.setHoverImage(tutorialSelect);
		bTutorial.setDownImage(tutorialSelect);
		bTutorial.setDisabledImage(tutorialOff);
		
		Texture statsOn = TextureRegistry.registerSprite("stats_on", "main_menus/stats_on");
		Texture statsOff = TextureRegistry.registerSprite("stats_off", "main_menus/stats_off");
		Texture statsSelected = TextureRegistry.registerSprite("stats_select2", "main_menus/stats_select2");
		bStats = new Button(500, 300, statsOn);
		bStats.setHoverImage(statsSelected);
		bStats.setDownImage(statsSelected);
		bStats.setDisabledImage(statsOff);
		
		Texture optionsOn = TextureRegistry.registerSprite("options_on", "main_menus/options_on");
		Texture optionsOff = TextureRegistry.registerSprite("options_off", "main_menus/options_off");
		Texture optionsSelected = TextureRegistry.registerSprite("options_select2", "main_menus/options_select2");
		bOptions = new Button(500, 300, optionsOn);
		bOptions.setHoverImage(optionsSelected);
		bOptions.setDownImage(optionsSelected);
		bOptions.setDisabledImage(optionsOff);
		
		Texture creditsOn = TextureRegistry.registerSprite("credits_on", "main_menus/credits_on");
		Texture creditsOff = TextureRegistry.registerSprite("credits_off", "main_menus/credits_off");
		Texture creditsSelected = TextureRegistry.registerSprite("credits_select2", "main_menus/credits_select2");
		bCredits = new Button(500, 300, creditsOn);
		bCredits.setHoverImage(creditsSelected);
		bCredits.setDownImage(creditsSelected);
		bCredits.setDisabledImage(creditsOff);
		
		Texture quitOn = TextureRegistry.registerSprite("quit_on", "main_menus/quit_on");
		Texture quitOff = TextureRegistry.registerSprite("quit_off", "main_menus/quit_off");
		Texture quitSelected = TextureRegistry.registerSprite("quit_select2", "main_menus/quit_select2");
		bQuit = new QuitButton(500, 300, quitOn);
		bQuit.setHoverImage(quitSelected);
		bQuit.setDownImage(quitSelected);
		bQuit.setDisabledImage(quitOff);
	}

	@Override
	public void render(float delta) 
	{
		Gdx.gl.glClearColor(31/256f, 34/256f, 38/256f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		bTutorial.setPosition(1201 - bTutorial.getWidth(), 279);
		bStats.setPosition(1201 - bStats.getWidth(), 221);
		bOptions.setPosition(1201 - bOptions.getWidth(), 163);
		bCredits.setPosition(1201 - bCredits.getWidth(), 105);
		bQuit.setPosition(1201 - bQuit.getWidth(), 47);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		batch.draw(background, 1, 0);
		bContinue.render(batch);
		bNew.render(batch);
		bTutorial.render(batch);
		bStats.render(batch);
		bOptions.render(batch);
		bCredits.render(batch);
		bQuit.render(batch);
		
		batch.end();
	}

	@Override
	public void resize(int width, int height) 
	{
		
	}

	@Override
	public void show() 
	{
		
	}

	@Override
	public void hide() 
	{
		
	}

	@Override
	public void pause() 
	{
		
	}

	@Override
	public void resume() 
	{
		
	}

	@Override
	public void dispose() 
	{
		
	}

	@Override
	public boolean click(int screenX, int screenY, int button) 
	{
		if(bContinue.click(screenX, screenY, button))
			return true;
		if(bNew.click(screenX, screenY, button))
			return true;
		if(bTutorial.click(screenX, screenY, button))
			return true;
		if(bStats.click(screenX, screenY, button))
			return true;
		if(bOptions.click(screenX, screenY, button))
			return true;
		if(bCredits.click(screenX, screenY, button))
			return true;
		if(bQuit.click(screenX, screenY, button))
			return true;
		
		return false;
	}
	
	public void setTooltipText(String text, int x, int y, int width, int height)
	{
		
	}
	
}

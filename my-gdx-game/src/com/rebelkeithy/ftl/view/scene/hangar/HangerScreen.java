package com.rebelkeithy.ftl.view.scene.hangar;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rebelkeithy.ftl.view.Button;
import com.rebelkeithy.ftl.view.TextureRegistry;
import com.rebelkeithy.ftl.view.scene.FTLScreen;

public class HangerScreen implements FTLScreen
{
	public OrthographicCamera camera;
	private SpriteBatch batch;
	
	private Texture background;
	
	private List<Button> buttons;
	private Button bEasy;
	private Button bNormal;
	private Button bHard;
	private Button bStart;
	
	private Button bLeft;
	private Button bRight;
	private Button bList;
	private Button bRandomShip;
	
	public HangerScreen()
	{
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera(w, h);
		camera.position.x += w/2;
		camera.position.y += h/2;
		camera.update();
		batch = new SpriteBatch();
		
		background = TextureRegistry.registerSprite("custom_main", "customizeUI/custom_main");
		
		Texture easyOn = TextureRegistry.registerSprite("button_easy_on", "customizeUI/button_easy_on");
		Texture easyOff = TextureRegistry.registerSprite("button_easy_off", "customizeUI/button_easy_off");
		bEasy = new Button(977, 682, easyOn);
		bEasy.setDownImage(easyOff);
		bEasy.setHoverImage(easyOff);
		
		Texture normalOn = TextureRegistry.registerSprite("button_normal_on", "customizeUI/button_normal_on");
		Texture normalOff = TextureRegistry.registerSprite("button_normal_off", "customizeUI/button_normal_off");
		bNormal = new Button(977, 656, normalOn);
		bNormal.setDownImage(normalOff);
		bNormal.setHoverImage(normalOff);
		
		Texture hardOn = TextureRegistry.registerSprite("button_hard_on", "customizeUI/button_hard_on");
		Texture hardOff = TextureRegistry.registerSprite("button_hard_off", "customizeUI/button_hard_off");
		bHard = new Button(977, 630, hardOn);
		bHard.setDownImage(hardOff);
		bHard.setHoverImage(hardOff);
		
		Texture startOn = TextureRegistry.registerSprite("button_start_on", "customizeUI/button_start_on");
		Texture startSelect = TextureRegistry.registerSprite("button_start_select2", "customizeUI/button_start_select2");
		bStart = new Button(1000, 650, startOn);
		bStart.setDownImage(startSelect);
		bStart.setHoverImage(startSelect);
		
		//Texture listOn = TextureRegistry.registerSprite(name, image)
	}

	@Override
	public void render(float delta) 
	{
		Gdx.gl.glClearColor(31/256f, 34/256f, 38/256f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		bStart.setPosition(1082, 644);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		batch.draw(background, 0, 0);
		bEasy.render(batch);
		bNormal.render(batch);
		bHard.render(batch);
		bStart.render(batch);
		
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
		return false;
	}

	@Override
	public void setTooltipText(String text, int x, int y, int width, int height) 
	{
		
	}

}

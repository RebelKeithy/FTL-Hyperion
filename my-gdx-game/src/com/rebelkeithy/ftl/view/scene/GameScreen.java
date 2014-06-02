package com.rebelkeithy.ftl.view.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rebelkeithy.ftl.Clock;
import com.rebelkeithy.ftl.FTLGame;
import com.rebelkeithy.ftl.ship.Ship;
import com.rebelkeithy.ftl.ship.ShipRegistry;
import com.rebelkeithy.ftl.view.GUI;
import com.rebelkeithy.ftl.view.ShipRenderer;
import com.rebelkeithy.ftl.view.ShipUIRenderer;
import com.rebelkeithy.ftl.view.TextureRegistry;
import com.rebelkeithy.ftl.view.ToolTip;
import com.rebelkeithy.ftl.view.upgrade.UpgradeUI;

public class GameScreen implements FTLScreen
{
	public OrthographicCamera camera;
	private SpriteBatch batch;
	
	public ShipRenderer playerRenderer;
	public ShipUIRenderer playerUIRenderer;
	
	private GUI gui;
	private ToolTip tooltip;
	private Texture black;
	private Texture white;

	public static boolean stop = false;
	
	public GameScreen()
	{
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(w, h);
		camera.position.x += w/2;
		camera.position.y += h/2;
		camera.update();
		batch = new SpriteBatch();
		
		tooltip = new ToolTip();
		
		black = TextureRegistry.getTexture("black");
		if(black == null)
		{
			Pixmap map = new Pixmap(1, 1, Format.RGBA8888);
			map.setColor(Color.BLACK);
			map.fill();
			black = new Texture(map);
			map.dispose();
			TextureRegistry.registerSprite("black", black);
		}
		
		white = TextureRegistry.getTexture("white");
		if(white == null)
		{
			Pixmap map = new Pixmap(1, 1, Format.RGBA8888);
			map.setColor(Color.WHITE);
			map.fill();
			white = new Texture(map);
			map.dispose();
			TextureRegistry.registerSprite("white", white);
		}
	}

	@Override
	public void render(float delta) 
	{
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		if(FTLGame.instance() != null)
		{
			Texture background = TextureRegistry.getTexture("background");
			batch.draw(background, 0, 0);
			
			Ship ship = FTLGame.instance().getPlayer();
			
			playerRenderer.render(batch, ship, 350, 270);
			playerUIRenderer.renderFirstLayer(batch, ship);

			batch.setColor(1, 1, 1, 0.8f);
			if(gui != null)
				batch.draw(black, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			batch.setColor(1, 1, 1, 1);
			
			playerUIRenderer.renderSecondLayer(batch, ship);
			
			if(gui != null)
			{
				gui.render(batch);
			}
			
			tooltip.render(batch);
			
		}
		batch.end();
	}

	@Override
	public void resize(int width, int height) 
	{
		
	}

	@Override
	public void show() 
	{				
		//Ship player = ShipRegistry.build("The Kestrel", "Player");
		Ship player = ShipRegistry.build("Red-Tail", "The Kestrel");
		player.setPlayer(true);
		
		FTLGame.instance().addShip(player, 0, 0);	
		
		player.getSystem("shields").addPower(2);
		player.getSystem("health").addPower(0);
		player.getSystem("engines").addPower(1);
		player.getSystem("oxygen").addPower(1);
		
		playerRenderer = new ShipRenderer(player);
		playerRenderer.setInteractive(true);
		playerUIRenderer = new ShipUIRenderer();
		playerUIRenderer.init();

		GameScreen.stop = false;
		Thread thread = new Thread() 
		{	
			public void run()
			{
				while(!GameScreen.stop)
				{
					float tps = 60;
					Clock.instance().update(1/tps);
					FTLGame.instance().update(1/tps);
					
					try {
						sleep((long) (1000/tps));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		
		thread.start();
		
		FTLGame.instance().action("Event", FTLGame.instance().getPlayer().getName(), "0");
	}

	@Override
	public void hide() 
	{
		GameScreen.stop = true;
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
		if(gui != null)
		{
			if(gui.click(screenX, screenY, button))
				return true;
		}
		
		if(playerRenderer.click(screenX, screenY, button))
			return true;
		if(playerUIRenderer.click(screenX, screenY, button))
			return true;
		
		return false;
	}

	public void openUpgradeGUI() 
	{
		gui = new UpgradeUI(FTLGame.instance().getPlayer());
	}

	public void closeGui() 
	{
		gui.close();
		gui = null;
	}
	
	public void setTooltipText(String text, int x, int y, int width, int height)
	{
		tooltip.setToolTip(text, x, y, width, height);
	}

	public void openGUI(GUI gui) 
	{
		if(this.gui != null)
			this.gui.close();
		
		this.gui = gui;
	}

}

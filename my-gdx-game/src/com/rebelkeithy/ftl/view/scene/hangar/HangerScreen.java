package com.rebelkeithy.ftl.view.scene.hangar;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rebelkeithy.ftl.crew.Crew;
import com.rebelkeithy.ftl.ship.Ship;
import com.rebelkeithy.ftl.ship.ShipRegistry;
import com.rebelkeithy.ftl.view.Button;
import com.rebelkeithy.ftl.view.CrewBox;
import com.rebelkeithy.ftl.view.ShipRenderer;
import com.rebelkeithy.ftl.view.TextureRegistry;
import com.rebelkeithy.ftl.view.scene.FTLScreen;

public class HangerScreen implements FTLScreen
{
	public OrthographicCamera camera;
	private SpriteBatch batch;
	
	private Texture background;
	private ShipRenderer shipRenderer;
	private List<HangerSystemRenderer> systemRenderers;
	private CrewBox crewBox;
	private Ship ship;
	
	private int difficulty = 1;
	
	private List<Button> buttons;
	private DifficultyButton bEasy;
	private DifficultyButton bNormal;
	private DifficultyButton bHard;
	private Button bStart;
	
	private Button bLeft;
	private Button bRight;
	private Button bList;
	private Button bRandomShip;
	
	private Button bA;
	private Button bB;
	private Button bC;
	private Button bHideRooms;
	
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
		ship = ShipRegistry.build("The Kestrel", "Player");
		ship.getSystem("oxygen").addPower(1);
		ship.getSystem("engines").addPower(1);
		
		shipRenderer = new ShipRenderer(ship);
		shipRenderer.setInteractive(false);
		
		systemRenderers = new ArrayList<HangerSystemRenderer>();
		String[] systemNames = new String[] {"shields", "engines", "oxygen", "weapons", "health", "command", "sensors", "doors"};
		for(String system : systemNames)
		{
			systemRenderers.add(new HangerSystemRenderer(ship.getSystem(system)));
		}
		
		crewBox = new CrewBox();
		
		buttons = new ArrayList<Button>();
		
		Texture easyOn = TextureRegistry.registerSprite("button_easy_on", "customizeUI/button_easy_on");
		Texture easyOff = TextureRegistry.registerSprite("button_easy_off", "customizeUI/button_easy_off");
		bEasy = new DifficultyButton(this, 0, 977, 682, easyOn);
		bEasy.setDownImage(easyOff);
		bEasy.setHoverImage(easyOff);
		bEasy.setSelected(true);
		buttons.add(bEasy);
		
		Texture normalOn = TextureRegistry.registerSprite("button_normal_on", "customizeUI/button_normal_on");
		Texture normalOff = TextureRegistry.registerSprite("button_normal_off", "customizeUI/button_normal_off");
		bNormal = new DifficultyButton(this, 1, 977, 656, normalOn);
		bNormal.setDownImage(normalOff);
		bNormal.setHoverImage(normalOff);
		buttons.add(bNormal);
		
		Texture hardOn = TextureRegistry.registerSprite("button_hard_on", "customizeUI/button_hard_on");
		Texture hardOff = TextureRegistry.registerSprite("button_hard_off", "customizeUI/button_hard_off");
		bHard = new DifficultyButton(this, 2, 977, 630, hardOn);
		bHard.setDownImage(hardOff);
		bHard.setHoverImage(hardOff);
		buttons.add(bHard);
		
		Texture startOn = TextureRegistry.registerSprite("button_start_on", "customizeUI/button_start_on");
		Texture startSelect = TextureRegistry.registerSprite("button_start_select2", "customizeUI/button_start_select2");
		bStart = new StartButton(1082, 644, startOn);
		bStart.setDownImage(startSelect);
		bStart.setHoverImage(startSelect);
		buttons.add(bStart);
		
		Texture leftOn = TextureRegistry.registerSprite("button_arrow_on", "customizeUI/button_arrow_on");
		Texture leftOff = TextureRegistry.registerSprite("button_arrow_off", "customizeUI/button_arrow_off");
		Texture leftSelect = TextureRegistry.registerSprite("button_arrow_select2", "customizeUI/button_arrow_select2");
		bLeft = new Button(27, 547, leftOn);
		bLeft.setHoverImage(leftSelect);
		bLeft.setDownImage(leftSelect);
		bLeft.setDisabledImage(leftOff);
		buttons.add(bLeft);
		
		bRight = new ButtonRight(131, 547, leftOn);
		bRight.setHoverImage(leftSelect);
		bRight.setDownImage(leftSelect);
		bRight.setDisabledImage(leftOff);
		buttons.add(bRight);
		
		Texture listOn = TextureRegistry.registerSprite("button_list_on", "customizeUI/button_list_on");
		Texture listSelect = TextureRegistry.registerSprite("button_list_select2", "customizeUI/button_list_select2");
		bList = new Button(64, 547, listOn);
		bList.setHoverImage(listSelect);
		bList.setDownImage(listSelect);
		buttons.add(bList);
		
		Texture randomOn = TextureRegistry.registerSprite("button_random_ship_on", "customizeUI/button_random_ship_on");
		Texture randomOff = TextureRegistry.registerSprite("button_random_ship_off", "customizeUI/button_random_ship_off");
		Texture randomSelect = TextureRegistry.registerSprite("button_random_ship_select2", "customizeUI/button_random_ship_select2");
		bRandomShip = new Button(19, 510, randomOn);
		bRandomShip.setHoverImage(randomSelect);
		bRandomShip.setDownImage(randomSelect);
		bRandomShip.setDisabledImage(randomOff);
		buttons.add(bRandomShip);
		
		Texture aOn = TextureRegistry.registerSprite("button_layouta_on", "customizeUI/button_layouta_on");
		Texture aOff = TextureRegistry.registerSprite("button_layouta_off", "customizeUI/button_layouta_off");
		Texture aSelect = TextureRegistry.registerSprite("button_layouta_select2", "customizeUI/button_layouta_select2");
		bA = new Button(32, 438, aOn);
		bA.setHoverImage(aSelect);
		bA.setDownImage(aSelect);
		bA.setDisabledImage(aOff);
		buttons.add(bA);
		
		Texture bOn = TextureRegistry.registerSprite("button_layoutb_on", "customizeUI/button_layoutb_on");
		Texture bOff = TextureRegistry.registerSprite("button_layoutb_off", "customizeUI/button_layoutb_off");
		Texture bSelect = TextureRegistry.registerSprite("button_layoutb_select2", "customizeUI/button_layoutb_select2");
		bB = new Button(77, 438, bOn);
		bB.setHoverImage(bSelect);
		bB.setDownImage(bSelect);
		bB.setDisabledImage(bOff);
		buttons.add(bB);
		
		Texture cOn = TextureRegistry.registerSprite("button_layoutc_on", "customizeUI/button_layoutc_on");
		Texture cOff = TextureRegistry.registerSprite("button_layoutc_off", "customizeUI/button_layoutc_off");
		Texture cSelect = TextureRegistry.registerSprite("button_layoutc_select2", "customizeUI/button_layoutc_select2");
		bC = new Button(122, 438, cOn);
		bC.setHoverImage(cSelect);
		bC.setDownImage(cSelect);
		bC.setDisabledImage(cOff);
		buttons.add(bC);
		
		Texture hideRoomsOn = TextureRegistry.registerSprite("button_hide_on", "customizeUI/button_hide_on");
		Texture hideRoomsSelect = TextureRegistry.registerSprite("button_hide_select2", "customizeUI/button_hide_select2");
		bHideRooms = new Button(20, 391, hideRoomsOn);
		bHideRooms.setHoverImage(hideRoomsSelect);
		bHideRooms.setDownImage(hideRoomsSelect);
		buttons.add(bHideRooms);
		
		
	}

	@Override
	public void render(float delta) 
	{
		Gdx.gl.glClearColor(31/256f, 34/256f, 38/256f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		batch.draw(background, 0, 0);
		shipRenderer.render(batch, ship, 365, 410);

		int i = 0;
		for(HangerSystemRenderer renderer : systemRenderers)
		{
			renderer.render(batch, 373 + i * 38, 244);
			i++;
		}
		
		for(Button button : buttons)
			button.render(batch);
		
		for(i = 0; i < 4; i++)
		{
			if(i < ship.getCrew().size())
			{
				Crew crew = ship.getCrew().get(i);
				crewBox.render(batch, crew, 70 + ((i%2) * 160), 136 - ((i/2) * 93));
			}
			else
			{
				crewBox.render(batch, null, 70 + ((i%2) * 160), 136 - ((i/2) * 93));
			}
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
		for(Button b : buttons)
			if(b.click(screenX, screenY, button))
				return true;
		
		return false;
	}

	@Override
	public void setTooltipText(String text, int x, int y, int width, int height) 
	{
		
	}

	public void setDifficulty(int difficulty) 
	{
		bEasy.setSelected(false);
		bNormal.setSelected(false);
		bHard.setSelected(false);
		this.difficulty = difficulty;
		if(difficulty == 0)
			bEasy.setSelected(true);
		if(difficulty == 1)
			bNormal.setSelected(true);
		if(difficulty == 2)
			bHard.setSelected(true);
	}

}

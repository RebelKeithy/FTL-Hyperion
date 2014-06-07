package com.rebelkeithy.ftl.view;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.rebelkeithy.ftl.crew.Crew;
import com.rebelkeithy.ftl.ship.Resource;
import com.rebelkeithy.ftl.ship.Room;
import com.rebelkeithy.ftl.ship.Ship;
import com.rebelkeithy.ftl.systems.AbstractShipSystem;
import com.rebelkeithy.ftl.systems.CommandSystem;
import com.rebelkeithy.ftl.systems.ShieldSystem;
import com.rebelkeithy.ftl.view.events.RenderScrap;
import com.rebelkeithy.ftl.view.scene.SpaceScreen;

public class ShipUIRenderer 
{
	private Texture hull;
	private Texture hullBar;
	private Texture fuel;
	private Texture missiles;
	private Texture drones;
	private Texture scrap;
	private Texture shieldBar;
	private Texture evadeOxygen;
	
	private Texture subsystems;
	
	private Texture powerSlotEmptyLarge;
	private Texture powerSlotPoweredLarge;	
	
	private Texture ftlBase;
	private Texture ftlCharge;
	private Texture ftlChargeOff;
	private Texture ftlCharging;
	private Texture jumpOn;
	private Texture jumpOff;
	private Texture jumpReady;
	
	private Texture ftlPullout;
	private Texture pilotOff;
	private Texture pilotOff2;
	private Texture pilotOn;
	private Texture engineOff;
	private Texture engineOff2;
	private Texture engineOn;
	
	private Texture ship;
	private Texture shipOver;
	private Texture shipDisabled;
	
	private Texture wireFull;
	private Texture wireMask;
	private Texture wire36;
	private Texture wire36Cap;
	private Texture crewButtonsBase;
	
	private Texture shieldChargeBar;
	
	private List<SystemRenderer> systemRenderers;
	private List<SystemRenderer> subsystemRenderers;
	
	private int ftlPulloutTimer;
	
	private Button shipButton;
	private Button saveCrewPositionsButton;
	private Button sendCrewPositionsButton;
	
	public void init()
	{
		hull = TextureRegistry.registerSprite("hull", "statusUI/top_hull");
		hullBar = TextureRegistry.registerSprite("hull_mask", "statusUI/top_hull_bar_mask");
		TextureRegistry.registerSprite("shields_on", "statusUI/top_shields4_on");
		TextureRegistry.registerSprite("shields_off", "statusUI/top_shields4_off");
		TextureRegistry.registerSprite("shield_dot_on", "statusUI/top_shieldsquare1_on");
		TextureRegistry.registerSprite("shield_dot_off", "statusUI/top_shieldsquare1_off");
		shieldBar = TextureRegistry.registerSprite("shield_bar", "statusUI/energy_shield_box");
		fuel = TextureRegistry.registerSprite("statusUI/top_fuel_on", "statusUI/top_fuel_on");
		missiles = TextureRegistry.registerSprite("statusUI/top_missiles_on", "statusUI/top_missiles_on");
		drones = TextureRegistry.registerSprite("statusUI/top_drones_on", "statusUI/top_drones_on");
		scrap = TextureRegistry.registerSprite("scrap", "statusUI/top_scrap");
		evadeOxygen = TextureRegistry.registerSprite("evadeOxygen", "statusUI/top_evade_oxygen");
		subsystems = TextureRegistry.registerSprite("subsystems", "box_subsystems4");
		
		ftlBase = TextureRegistry.registerSprite("FTL_base", "buttons/FTL/FTL_base");
		ftlCharge = TextureRegistry.registerSprite("FTL_loadingbars", "buttons/FTL/FTL_loadingbars");
		ftlChargeOff = TextureRegistry.registerSprite("FTL_loadingbars_off", "buttons/FTL/FTL_loadingbars_off");
		ftlCharging = TextureRegistry.registerSprite("FTL_CHARGING", "buttons/FTL/FTL_CHARGING");
		jumpOn = TextureRegistry.registerSprite("FTL_JUMP", "buttons/FTL/FTL_JUMP");
		jumpOff = TextureRegistry.registerSprite("FTL_JUMP_off", "buttons/FTL/FTL_JUMP_off");
		jumpReady = TextureRegistry.registerSprite("FTL_READY", "buttons/FTL/FTL_READY");
		
		ship = TextureRegistry.registerSprite("shipButton", "statusUI/top_ship_on");
		shipOver = TextureRegistry.registerSprite("shipButtonOver", "statusUI/top_ship_select2");
		
		ftlPullout = TextureRegistry.registerSprite("FTL_pullout", "buttons/FTL/FTL_pullout");
		pilotOff = TextureRegistry.registerSprite("FTL_pilot_off1", "buttons/FTL/FTL_pilot_off1");
		pilotOff2 = TextureRegistry.registerSprite("FTL_pilot_off2", "buttons/FTL/FTL_pilot_off2");
		pilotOn = TextureRegistry.registerSprite("FTL_pilot_on", "buttons/FTL/FTL_pilot_on");
		engineOff = TextureRegistry.registerSprite("FTL_engine_off1", "buttons/FTL/FTL_engine_off1");
		engineOff2 = TextureRegistry.registerSprite("FTL_engine_off2", "buttons/FTL/FTL_engine_off2");
		engineOn = TextureRegistry.registerSprite("FTL_engine_on", "buttons/FTL/FTL_engine_on");
		
		wireFull = TextureRegistry.registerSprite("wire_full", "wireUI/wire_full");
		wireMask = TextureRegistry.registerSprite("wire_mask", "wire_left_mask");
		wire36 = TextureRegistry.registerSprite("wire36", "wireUI/wire_36");
		wire36Cap = TextureRegistry.registerSprite("wire36Cap", "wireUI/wire_36_cap");
		
		powerSlotEmptyLarge = TextureRegistry.getTexture("powerSlotEmptyLarge");
		powerSlotPoweredLarge = TextureRegistry.getTexture("powerSlotPoweredLarge");
		
		Pixmap shieldChargeBarMap = new Pixmap(6, 6, Format.RGBA8888);
		shieldChargeBarMap.setColor(27/256f, 132/256f, 1, 1);
		shieldChargeBarMap.fill();
		shieldChargeBar = TextureRegistry.registerSprite("shieldBar", new Texture(shieldChargeBarMap));
		shieldChargeBarMap.dispose();
		
		shipButton = new ShipButton(625, 648, 632, 655, 61, 32, ship);
		shipButton.setHoverImage(shipOver);
		shipButton.setDownImage(shipOver);
		
		crewButtonsBase = TextureRegistry.registerSprite("button_station_base", "statusUI/button_station_base");
		Texture crewButtonSave = TextureRegistry.registerSprite("button_station_assign_on", "statusUI/button_station_assign_on");
		Texture crewButtonSaveHover = TextureRegistry.registerSprite("button_station_assign_select2", "statusUI/button_station_assign_select2");
		Texture crewButtonSend = TextureRegistry.registerSprite("button_station_return_on", "statusUI/button_station_return_on");
		Texture crewButtonSendHover = TextureRegistry.registerSprite("button_station_return_select2", "statusUI/button_station_return_select2");
		
		saveCrewPositionsButton = new Button(10, 500, 10, 500, 20, 20, crewButtonSave);
		saveCrewPositionsButton.setHoverImage(crewButtonSaveHover);
		saveCrewPositionsButton.setDownImage(crewButtonSaveHover);
		sendCrewPositionsButton = new Button(10, 500, 10, 500, 20, 20, crewButtonSend);
		sendCrewPositionsButton.setHoverImage(crewButtonSendHover);
		sendCrewPositionsButton.setDownImage(crewButtonSendHover);
	}
	
	public void initSystemRenderers(Ship ship)
	{
		systemRenderers = new ArrayList<SystemRenderer>();
		
		// TODO: get list of systems from central location
		String[] systemNames = new String[] {"shields", "engines", "health", "oxygen", "weapons"};
		
		int systemX = 77;
		for(String name : systemNames)
		{
			AbstractShipSystem system = ship.getSystem(name);
			if(system != null)
			{
				SystemRenderer renderer = system.getSystemRenderer();
				renderer.setX(systemX);
				systemRenderers.add(renderer);
				systemX += renderer.getWidth();
			}
		}
	}
	
	public void renderFirstLayer(SpriteBatch batch, Ship ship)
	{		
		renderTopButtons(batch, ship);
		renderSystems(batch, ship);
		renderSubsystems(batch, ship);
		renderCrew(batch, ship);
	}
	
	public void renderSecondLayer(SpriteBatch batch, Ship ship)
	{
		renderHull(batch, ship);
		renderShields(batch, ship);
		renderEvadeOxygen(batch, ship);
		renderResources(batch, ship);
		renderScrap(batch, ship);
	}
	
	private void renderHull(SpriteBatch batch, Ship ship)
	{
		TextureRegion health = new TextureRegion(hullBar, 0, 0, 12*30, hullBar.getHeight());

		batch.setColor(120/256f, 1, 120/256f, 1);
		batch.draw(health, 11, 655);
		batch.setColor(1, 1, 1, 1);

		batch.draw(hull, 0, 655);
	}
	
	private void renderScrap(SpriteBatch batch, Ship ship)
	{
		int numscrap = Resource.getResource("scrap").getResourceAmount(ship);
		RenderScrap event = new RenderScrap(batch, numscrap);
		FTLView.instance().RENDER_BUS.post(event);
		numscrap = event.scrap;
		TextBounds bounds = Fonts.numFont.getBounds("" + numscrap);
		Fonts.numFont.draw(batch, ""+numscrap, 460 - bounds.width/2, 699);
	}
	
	private void renderResources(SpriteBatch batch, Ship ship)
	{
		// Resource spacing is not uniform
		/*List<String> resources = Resource.getResources();
		int i = 0;
		for(String name : resources)
		{
			if(name.equals("scrap"))
				continue;
			
			Resource resource = Resource.getResource(name);
			if(resource.getName().equals("scrap"))
				continue;
			Texture resourceTexture = TextureRegistry.getTexture(resource.getIcon());
			batch.draw(resourceTexture, 117 + 65*i, 637);
			i++;
		}*/
		
		batch.draw(fuel, 117, 637);
		FTLView.instance().setTooltipText(Resource.getResource("fuel").getTooltip(), 127, 637 + 7, fuel.getWidth() - 21, fuel.getHeight() - 14);
		batch.draw(missiles, 182, 637);
		FTLView.instance().setTooltipText(Resource.getResource("missiles").getTooltip(), 182 + 10, 637 + 7, missiles.getWidth() - 21, missiles.getHeight() - 14);
		batch.draw(drones, 252, 637);
		FTLView.instance().setTooltipText(Resource.getResource("drones").getTooltip(), 252 + 10, 637 + 7, drones.getWidth() - 21, drones.getHeight() - 14);
		batch.draw(scrap, 377, 665);
		FTLView.instance().setTooltipText(Resource.getResource("scrap").getTooltip(), 377 + 10, 665 + 7, scrap.getWidth() - 21, scrap.getHeight() - 14);
		
		int numfuel = Resource.getResource("fuel").getResourceAmount(ship);
		TextBounds bounds = Fonts.numFont.getBounds(""+numfuel);
		Fonts.numFont.draw(batch, ""+numfuel, 168 - bounds.width/2, 664);

		int nummissiles = Resource.getResource("missiles").getResourceAmount(ship);
		bounds = Fonts.numFont.getBounds(""+nummissiles);
		Fonts.numFont.draw(batch, ""+nummissiles, 235 - bounds.width/2, 664);

		int numdrones = Resource.getResource("drones").getResourceAmount(ship);
		bounds = Fonts.numFont.getBounds(""+numdrones);
		Fonts.numFont.draw(batch, ""+numdrones, 302 - bounds.width/2, 664);
	}
	
	private void renderShields(SpriteBatch batch, Ship ship)
	{
		ShieldSystem system = (ShieldSystem) ship.getSystem("shields");
		Texture shields = TextureRegistry.getTexture("shields_off");
		if(system != null && system.getPower() > 0)
			shields = TextureRegistry.getTexture("shields_on");
		batch.draw(shields, 0, 612);
		batch.draw(shieldBar, 12, 609);
		
		if(system != null)
		{
			float charge = (float) system.getChargePercent();
			batch.draw(shieldChargeBar, 15, 612, charge*92, 6);
		}
		
		if(system != null && system.getPower() > 0)
		{
			for(int i = 0; i < system.maxShields(); i++)
			{
				Texture shieldDot = TextureRegistry.getTexture("shield_dot_off");
				if(system.getShieldLvl() > i)
				{
					shieldDot = TextureRegistry.getTexture("shield_dot_on");
				}
				
				batch.draw(shieldDot, 12 + i*23, 622);
			}
		}
	}
	
	private void renderEvadeOxygen(SpriteBatch batch, Ship ship)
	{
		batch.draw(evadeOxygen, 0, 565);
		int dodge = (int) ((CommandSystem)(ship.getSystem("pilot"))).getDodge();
		
		TextBounds bounds = Fonts.ccFont.getBounds("" + dodge);
		Fonts.ccFont.draw(batch, "" + dodge, 82 - bounds.width, 598);
		int totalOxygen = 0;
		for(Room room : ship.getRooms().values())
		{
			totalOxygen += (int)(room.getProperties().getDouble("oxygen"));
		}
		int oxygen = totalOxygen/ship.getRooms().size();
		bounds = Fonts.ccFont.getBounds(String.valueOf(oxygen));
		Fonts.ccFont.draw(batch, String.valueOf(oxygen), 82 - bounds.width, 584);
	}
	
	private void renderCrew(SpriteBatch batch, Ship ship)
	{
		List<Crew> crew = ship.getCrew();
		for(int i = 0; i < crew.size(); i++)
		{
			CrewRenderer crewRenderer = CrewRenderer.getCrewRenderer(crew.get(i));
			crewRenderer.renderUI(batch, crew.get(i), i);
		}
		batch.draw(crewButtonsBase, 12, 526 - crew.size()*30);
		batch.draw(crewButtonsBase, 50, 526 - crew.size()*30);
		saveCrewPositionsButton.setPosition(24, 538 - crew.size()*30);
		saveCrewPositionsButton.render(batch);
		sendCrewPositionsButton.setPosition(62, 538 - crew.size()*30);
		sendCrewPositionsButton.render(batch);
	}
	
	private void renderTopButtons(SpriteBatch batch, Ship ship)
	{
		FTLView.instance().setTooltipText("Opens star map to\nchoose jump location\nHotkey: J", 521 + 13, 637, ftlBase.getWidth() - 29, ftlBase.getHeight());
		batch.draw(ftlBase, 521, 637);
		float shipFtlCharge = (float) ship.getFTLCharge();

		// Render FTL Pullout
		if(!(ship.getSystem("pilot").isPowered() && ship.getSystem("pilot").isManned() && ship.getSystem("engines").isPowered()))
		{
			ftlPulloutTimer+=6;
			if(ftlPulloutTimer > 48)
				ftlPulloutTimer = 48;
		}
		else
		{
			ftlPulloutTimer-=6;
			if(ftlPulloutTimer < 0)
				ftlPulloutTimer = 0;
		}

		batch.flush();
		Rectangle scissors = new Rectangle();
		Rectangle clipBounds = new Rectangle(536, 603, 74, 46);
		ScissorStack.calculateScissors(((SpaceScreen)FTLView.instance().getScreen()).camera, batch.getTransformMatrix(), clipBounds, scissors);
		ScissorStack.pushScissors(scissors);
		if(ftlPulloutTimer > 0)
		{
			batch.draw(ftlPullout, 529, 602 + 48-ftlPulloutTimer);

			long time = System.currentTimeMillis();
			float alpha = time%2000;
			alpha = (-Math.abs(alpha-1000) + 1000)/1000f;
			
			if(ship.getSystem("pilot").isPowered() && ship.getSystem("pilot").isManned())
			{
				batch.draw(pilotOn, 548, 635 + 48-ftlPulloutTimer);
			}
			else
			{
				//batch.setColor(1, 1, 1, alpha);
				batch.draw(pilotOff2, 548, 635 + 48-ftlPulloutTimer);
				batch.setColor(1, 1, 1, 1-alpha);
				batch.draw(pilotOff, 548, 635 + 48-ftlPulloutTimer);
				batch.setColor(1, 1, 1, 1);
			}
			if(ship.getSystem("engines").isPowered())
			{
				batch.draw(engineOn, 548, 621 + 48-ftlPulloutTimer);
			}
			else
			{
				batch.draw(engineOff2, 548, 621 + 48-ftlPulloutTimer);
				batch.setColor(1, 1, 1, 1-alpha);
				batch.draw(engineOff, 548, 621 + 48-ftlPulloutTimer);
				batch.setColor(1, 1, 1, 1);
			}
		}
		batch.flush();
		ScissorStack.popScissors();
		
		if(shipFtlCharge < 1)
		{
			if(ship.getSystem("pilot").isPowered() && ship.getSystem("pilot").isManned() && ship.getSystem("engines").isPowered())
			{
				TextureRegion chargeRegion = new TextureRegion(ftlCharge, (int) (74*shipFtlCharge), 29);
				batch.draw(chargeRegion, 536, 656);
				
				if(ftlPulloutTimer < 10)
				{
					long time = System.currentTimeMillis();
					float color = time%2000;
					color = (-Math.abs(color-1000) + 1000)/2000f;
					batch.setColor(1, 1, 1 - color, 1);
					batch.draw(ftlCharging, 536, 632);
					batch.setColor(1, 1, 1, 1);
				}
			}
			else
			{
				TextureRegion chargeRegion = new TextureRegion(ftlChargeOff, (int) (74*shipFtlCharge), 29);
				batch.draw(chargeRegion, 536, 656);
			}
		}
		else
		{
			if(ship.getSystem("pilot").isPowered() && ship.getSystem("pilot").isManned() && ship.getSystem("engines").isPowered())
			{
				batch.setColor(1, 1, 0, 1);
				batch.draw(jumpOn, 529, 649);
				batch.setColor(1, 1, 1, 1);
			}
			else
			{
				batch.draw(jumpOff, 529, 649);
			}
			if(ftlPulloutTimer < 10)
			{
				long time = System.currentTimeMillis();
				float color = time%2000;
				color = (-Math.abs(color-1000) + 1000)/2000f;
				batch.setColor(1, 1, 1 - color, 1);
				batch.draw(jumpReady, 545, 632);
				batch.setColor(1, 1, 1, 1);
			}
		}

		shipButton.render(batch);
	}
	
	private void initSubsystemRenderers(Ship ship)
	{
		subsystemRenderers = new ArrayList<SystemRenderer>();
		
		String names[] = {"pilot", "sensors", "doors", "battery"};
		
		int systemX = 1034;
		for(String name : names)
		{
			AbstractShipSystem system = ship.getSystem(name);
			if(system != null)
			{
				SystemRenderer renderer = system.getSystemRenderer();
				renderer.setX(systemX);
				renderer.setY(42);
				subsystemRenderers.add(renderer);
				systemX += renderer.getWidth();
			}
		}
	}
	
	private void renderSubsystems(SpriteBatch batch, Ship ship) 
	{
		if(subsystemRenderers == null)
			initSubsystemRenderers(ship);
		
		batch.draw(subsystems, 1022, 13);
		
		for(SystemRenderer renderer : subsystemRenderers)
		{
			renderer.render(batch);
		}
	}

	public void renderSystems(SpriteBatch batch, Ship ship)
	{
		// Systems
		if(systemRenderers == null)
			initSystemRenderers(ship);
		
		int systemX = 12;
		int systemY = 24;
		
		AbstractShipSystem reactor = ship.getSystem("reactor");
		for(int i = 0; i < reactor.getMaxPower(); i++)
		{
			if(reactor.getPower() > i)
				batch.draw(powerSlotPoweredLarge, systemX, 27 + i*9);
			else
				batch.draw(powerSlotEmptyLarge, systemX, 27 + i*9);
		}
		
		// TODO: alpha mask with this
		//batch.draw(wireMask, 40, 4 + ship.getSystem("reactor").getMaxPower()*9);

		int clip = 33*9 - ship.getSystem("reactor").getMaxPower()*9;
		//clip = 40;
		batch.draw(new TextureRegion(wireFull, 0, clip, wireFull.getWidth(), wireFull.getHeight()), 33, -17 - clip);
		batch.setColor(1, 1, 1, 0.6f);
		batch.draw(wireMask, 40, 4 + ship.getSystem("reactor").getPower()*9);
		batch.setColor(1, 1, 1, 1);

		systemX += 65;
		
		for(SystemRenderer renderer : systemRenderers)
		{
			renderer.render(batch);
			if(renderer == systemRenderers.get(systemRenderers.size()-1))
			{
				batch.draw(wire36Cap, systemX-24, systemY-22);
			}
			else if(renderer != systemRenderers.get(0))
			{
				batch.draw(wire36, systemX-24, systemY-22);
			}
			systemX += renderer.getWidth();
		}
	}
	
	public boolean click(int screenX, int screenY, int button)
	{		
		for(SystemRenderer renderer : subsystemRenderers)
		{
			if(renderer.click(screenX, screenY, button))
			{
				return true;
			}
		}
		
		for(SystemRenderer renderer : systemRenderers)
		{
			if(renderer.click(screenX, screenY, button))
			{
				return true;
			}
		}
		
		if(shipButton.containsPoint(screenX, screenY))
		{
			if(button == 0)
				shipButton.leftClick();
			
			return true;
		}
		
		return false;
	}
}

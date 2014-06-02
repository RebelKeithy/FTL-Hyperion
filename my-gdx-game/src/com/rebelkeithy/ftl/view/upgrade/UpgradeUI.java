package com.rebelkeithy.ftl.view.upgrade;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.google.common.eventbus.Subscribe;
import com.rebelkeithy.ftl.ship.Resource;
import com.rebelkeithy.ftl.ship.Ship;
import com.rebelkeithy.ftl.view.Button;
import com.rebelkeithy.ftl.view.CrewTabButton;
import com.rebelkeithy.ftl.view.FTLView;
import com.rebelkeithy.ftl.view.Fonts;
import com.rebelkeithy.ftl.view.GUI;
import com.rebelkeithy.ftl.view.TextureRegistry;
import com.rebelkeithy.ftl.view.events.RenderScrap;

public class UpgradeUI extends GUI
{
	private Texture background;
	
	private Button crewButton;
	private Button equipmentButton;
	
	private Texture systemMaxOn;
	private Texture systemMaxOff;
	private Texture systemMaxSelect;
	private Texture systemNone;
	private Texture systemOn;
	private Texture systemSelect;
	
	private Texture subsystemMaxOn;
	private Texture subsystemMaxOff;
	private Texture subsystemMaxSelect;
	private Texture subsystemNone;
	private Texture subsystemOn;
	private Texture subsystemSelect;
	
	private Texture acceptBase;
	private Texture undoBase;
	
	private List<Button> systems;
	private List<Button> subsystems;
	private Button reactor;
	private Button accept;
	private Button undo;
	
	private Ship ship;
	private int scrap;
	
	public UpgradeUI(Ship ship)
	{
		this.ship = ship;
		scrap = Resource.getResource("scrap").getResourceAmount(ship);
		
		background = TextureRegistry.registerSprite("UpgradeBackground", "upgradeUI/Equipment/upgrades_main");

		Texture crewTab = TextureRegistry.registerSprite("upgrades_crew_on", "upgradeUI/Equipment/tabButtons/upgrades_crew_on");
		Texture crewTabHover = TextureRegistry.registerSprite("upgrades_crew_select2", "upgradeUI/Equipment/tabButtons/upgrades_crew_select2");
		crewButton = new CrewTabButton(549, 593, crewTab);
		crewButton.setHoverImage(crewTabHover);
		crewButton.setDownImage(crewTabHover);
		this.addButton(crewButton);
		
		Texture equipmentTab = TextureRegistry.registerSprite("upgrades_equipment_on", "upgradeUI/Equipment/tabButtons/upgrades_equipment_on");
		Texture equipmentTabSelect = TextureRegistry.registerSprite("upgrades_equipment_select2", "upgradeUI/Equipment/tabButtons/upgrades_equipment_select2");
		equipmentButton = new Button(682, 593, equipmentTab);
		equipmentButton.setHoverImage(equipmentTabSelect);
		equipmentButton.setDownImage(equipmentTabSelect);
		this.addButton(equipmentButton);
		
		systemMaxOn = TextureRegistry.registerSprite("systemMaxOn", "upgradeUI/upgrade_system_bar_max_on");
		systemMaxOff = TextureRegistry.registerSprite("systemMaxOff", "upgradeUI/upgrade_system_bar_off");
		systemMaxSelect = TextureRegistry.registerSprite("systemMaxSelect", "upgradeUI/upgrade_system_bar_max_select2");
		systemNone = TextureRegistry.registerSprite("systemNone", "upgradeUI/upgrade_system_bar_none");
		systemOn = TextureRegistry.registerSprite("systemOn", "upgradeUI/upgrade_system_bar_on");
		systemSelect = TextureRegistry.registerSprite("systemSelect", "upgradeUI/upgrade_system_bar_select2");
		
		subsystemMaxOn = TextureRegistry.registerSprite("subsystemMaxOn", "upgradeUI/upgrade_subsystem_bar_max_on");
		subsystemMaxOff = TextureRegistry.registerSprite("subsystemMaxOff", "upgradeUI/upgrade_subsystem_bar_off");
		subsystemMaxSelect = TextureRegistry.registerSprite("subsystemMaxSelect", "upgradeUI/upgrade_subsystem_bar_max_select2");
		subsystemNone = TextureRegistry.registerSprite("subsystemNone", "upgradeUI/upgrade_subsystem_bar_none");
		subsystemOn = TextureRegistry.registerSprite("subsystemOn", "upgradeUI/upgrade_subsystem_bar_on");
		subsystemSelect = TextureRegistry.registerSprite("subsystemSelect", "upgradeUI/upgrade_subsystem_bar_select2");

		
		Texture reactorOn = TextureRegistry.registerSprite("equipment_reactor_on", "upgradeUI/Equipment/equipment_reactor_on");
		Texture reactorSelect = TextureRegistry.registerSprite("equipment_reactor_select2", "upgradeUI/Equipment/equipment_reactor_select2");
		Texture reactorMaxOn = TextureRegistry.registerSprite("equipment_reactor_off", "upgradeUI/Equipment/equipment_reactor_off");
		Texture reactorMaxSelect = TextureRegistry.registerSprite("equipment_reactor_off", "upgradeUI/Equipment/equipment_reactor_off");
		
		reactor = new ReactorButton(this, ship.getSystem("reactor"), 645, 195, reactorOn, reactorSelect, reactorMaxOn, reactorMaxSelect);
		reactor.setHoverImage(reactorSelect);
		reactor.setDownImage(reactorSelect);
		this.addButton(reactor);
		
		int i = 0;
		systems = new ArrayList<Button>();
		String[] systemNames = {"shields", "engines", "health", "oxygen", "weapons"};
		for(String systemName : systemNames)
		{
			if(ship.getSystem(systemName) != null)
			{
				SystemButton button = new SystemButton(this, ship.getSystem(systemName), 379 + i*66, 370, systemOn, systemSelect, systemMaxOn, systemMaxSelect);
				systems.add(button);
				this.addButton(button);
				i++;
			}
		}
		
		i = 0;
		subsystems = new ArrayList<Button>();
		String[] subsystemNames = {"command", "sensors", "doors", "battery"};
		for(String systemName : subsystemNames)
		{
			if(ship.getSystem(systemName) != null)
			{
				SystemButton button = new SystemButton(this, ship.getSystem(systemName), 356 + i*66, 192, subsystemOn, subsystemSelect, subsystemMaxOn, subsystemMaxSelect);
				subsystems.add(button);
				this.addButton(button);
				i++;
			}
		}
		
		acceptBase = TextureRegistry.registerSprite("acceptButtonBase", "upgradeUI/buttons_accept_base");
		undoBase = TextureRegistry.registerSprite("undoButtonBase", "upgradeUI/buttons_undo_base");
		
		Texture acceptUp = TextureRegistry.registerSprite("acceptButton", "upgradeUI/buttons_accept_on");
		Texture acceptHover = TextureRegistry.registerSprite("acceptButtonHover", "upgradeUI/buttons_accept_select2");
		accept = new AcceptButton(this, 753, 117, acceptUp);
		accept.setHoverImage(acceptHover);
		accept.setDownImage(acceptHover);
		this.addButton(accept);

		Texture undoUp = TextureRegistry.registerSprite("undoButton", "upgradeUI/buttons_undo_on");
		Texture undoOff = TextureRegistry.registerSprite("undoButtonOff", "upgradeUI/buttons_undo_off");
		Texture undoHover = TextureRegistry.registerSprite("undoButtonHover", "upgradeUI/buttons_undo_select2");
		undo = new UndoButton(this, 350, 117, undoUp);
		undo.setHoverImage(undoHover);
		undo.setDownImage(undoHover);
		undo.setDisabledImage(undoOff);
		this.addButton(undo);
		
		FTLView.instance().RENDER_BUS.register(this);
	}
	
	public int getAvaliableScrap()
	{
		return scrap;
	}
	
	public void render(SpriteBatch batch)
	{
		batch.draw(background, 340, 164);
		crewButton.render(batch);
		equipmentButton.render(batch);
		//Fonts.numFont.draw(batch, ship.getName(), 500, 550);

		int shipNameWidth = (int) Fonts.ccNew64.getBounds(ship.getName()).width;
		Fonts.ccNew64.draw(batch, ship.getName(), 642 - shipNameWidth/2, 579);
		for(int i = systems.size(); i < 8; i++)
		{
			batch.draw(systemNone, 379 + i*66, 370);
		}
		
		for(Button button : systems)
		{
			button.render(batch);
		}
		
		for(Button button : subsystems)
		{
			button.render(batch);
		}
		
		for(int i = subsystems.size(); i < 4; i++)
		{
			batch.draw(subsystemNone, 356 + i*66, 192);
		}
		
		batch.draw(undoBase, 350, 117);
		undo.render(batch);
		
		batch.draw(acceptBase, 753, 117);
		accept.render(batch);

		reactor.render(batch);
	}
	
	/*
	public boolean click(int screenX, int screenY, int button) 
	{
		if(accept.click(screenX, screenY, button))
			return true;
		if(undo.click(screenX, screenY, button))
			return true;
		if(crewButton.click(screenX, screenY, button))
			return true;
		
		for(Button systemButton : systems)
		{
			if(systemButton.click(screenX, screenY, button))
				return true;
		}
		
		for(Button subsystemButton : subsystems)
		{
			if(subsystemButton.click(screenX, screenY, button))
				return true;
		}
		
		if(reactor.click(screenX, screenY, button))
			return true;
		
		return false;
	}
	*/

	public void undo() 
	{
		for(Button button : systems)
		{
			((SystemButton)button).cancel();
		}
		for(Button button : subsystems)
		{
			((SystemButton)button).cancel();
		}
		reactor.cancel();
	}

	public void close() 
	{
		FTLView.instance().RENDER_BUS.unregister(this);
	}
	
	@Subscribe
	public void renderScrap(RenderScrap event)
	{
		int shipScrap = event.scrap;
		for(Button button : systems)
		{
			shipScrap -= ((SystemButton)button).cost();
		}
		for(Button button : subsystems)
		{
			shipScrap -= ((SystemButton)button).cost();
		}
		shipScrap -= ((ReactorButton) reactor).cost();

		if(shipScrap == event.scrap)
			undo.setDisabled(true);
		else
		{
			undo.setDisabled(false);			
		}
		event.scrap = shipScrap;
		scrap = shipScrap;
	}

	public void accept() 
	{
		int shipScrap = 0;
		for(Button button : systems)
		{
			((SystemButton)button).apply();
			shipScrap += ((SystemButton)button).cost();
		}
		for(Button button : subsystems)
		{
			((SystemButton)button).apply();
			shipScrap += ((SystemButton)button).cost();
		}
		
		((ReactorButton) reactor).apply();
		shipScrap += ((ReactorButton) reactor).cost();

		Resource.getResource("scrap").addResource(ship, -shipScrap);
	}
}

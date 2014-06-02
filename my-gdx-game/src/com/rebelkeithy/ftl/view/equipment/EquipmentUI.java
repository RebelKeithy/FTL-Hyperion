package com.rebelkeithy.ftl.view.equipment;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rebelkeithy.ftl.ship.Ship;
import com.rebelkeithy.ftl.view.Button;
import com.rebelkeithy.ftl.view.CrewTabButton;
import com.rebelkeithy.ftl.view.GUI;
import com.rebelkeithy.ftl.view.TextureRegistry;
import com.rebelkeithy.ftl.view.UpgradesTabButton;
import com.rebelkeithy.ftl.view.upgrade.AcceptButton;

public class EquipmentUI extends GUI
{
	private Texture background;
	
	private Button  upgradesButton;
	private Button  crewButton;

	private Texture acceptBase;
	private Button accept;
	
	private Ship ship;
	
	public EquipmentUI(Ship ship)
	{
		this.ship = ship;
		
		background = TextureRegistry.registerSprite("equipment_main", "upgradeUI/Equipment/equipment_main");
		
		Texture upgradesTab = TextureRegistry.registerSprite("equipment_upgrades_on", "upgradeUI/Equipment/tabButtons/equipment_upgrades_on");
		Texture upgradesTabSelect = TextureRegistry.registerSprite("equipment_upgrades_select2", "upgradeUI/Equipment/tabButtons/equipment_upgrades_select2");
		Texture crewTab = TextureRegistry.registerSprite("equipment_crew_on", "upgradeUI/Equipment/tabButtons/equipment_crew_on");
		Texture crewTabSelect = TextureRegistry.registerSprite("equipment_crew_select2", "upgradeUI/Equipment/tabButtons/equipment_crew_select2");
		
		upgradesButton = new UpgradesTabButton(340, 593, upgradesTab);
		upgradesButton.setHoverImage(upgradesTabSelect);
		upgradesButton.setDownImage(upgradesTabSelect);
		this.addButton(upgradesButton);
		
		crewButton = new CrewTabButton(549, 593, crewTab);
		crewButton.setHoverImage(crewTabSelect);
		crewButton.setDownImage(crewTabSelect);
		this.addButton(crewButton);		

		acceptBase = TextureRegistry.registerSprite("acceptButtonBase", "upgradeUI/buttons_accept_base");
		Texture acceptUp = TextureRegistry.registerSprite("acceptButton", "upgradeUI/buttons_accept_on");
		Texture acceptHover = TextureRegistry.registerSprite("acceptButtonHover", "upgradeUI/buttons_accept_select2");
		accept = new AcceptButton(this, 753, 117, acceptUp);
		accept.setHoverImage(acceptHover);
		accept.setDownImage(acceptHover);
		this.addButton(accept);
	}

	public void render(SpriteBatch batch)
	{
		batch.draw(background, 340, 164);
		batch.draw(acceptBase, 753, 117);
		accept.render(batch);
		upgradesButton.render(batch);
		crewButton.render(batch);
	}
}

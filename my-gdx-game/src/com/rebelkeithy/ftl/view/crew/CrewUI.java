package com.rebelkeithy.ftl.view.crew;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rebelkeithy.ftl.crew.Crew;
import com.rebelkeithy.ftl.ship.Ship;
import com.rebelkeithy.ftl.view.Button;
import com.rebelkeithy.ftl.view.CrewBox;
import com.rebelkeithy.ftl.view.EquipmentTabButton;
import com.rebelkeithy.ftl.view.Fonts;
import com.rebelkeithy.ftl.view.GUI;
import com.rebelkeithy.ftl.view.TextureRegistry;
import com.rebelkeithy.ftl.view.UpgradesTabButton;
import com.rebelkeithy.ftl.view.upgrade.AcceptButton;

public class CrewUI extends GUI
{
	private Texture background;
	
	private Texture acceptBase;
	private Button accept;
	
	private Button  upgradesButton;
	private Button  equipmentButton;
	private Button[] dismissButtons;
	
	private Ship ship;
	private CrewBox crewBox;
	
	public CrewUI(Ship ship)
	{
		this.ship = ship;
		crewBox = new CrewBox();
		
		background = TextureRegistry.registerSprite("crew_main", "upgradeUI/Equipment/crew_main");
		
		Texture upgradesTab = TextureRegistry.registerSprite("crew_upgrades_on", "upgradeUI/Equipment/tabButtons/crew_upgrades_on");
		Texture upgradesTabSelect = TextureRegistry.registerSprite("crew_upgrades_select2", "upgradeUI/Equipment/tabButtons/crew_upgrades_select2");
		Texture equipmentTab = TextureRegistry.registerSprite("crew_equipment_on", "upgradeUI/Equipment/tabButtons/crew_equipment_on");
		Texture equipmentTabSelect = TextureRegistry.registerSprite("crew_equipment_select2", "upgradeUI/Equipment/tabButtons/crew_equipment_select2");
		
		upgradesButton = new UpgradesTabButton(340, 593, upgradesTab);
		upgradesButton.setHoverImage(upgradesTabSelect);
		upgradesButton.setDownImage(upgradesTabSelect);
		this.addButton(upgradesButton);
		
		equipmentButton = new EquipmentTabButton(682, 593, equipmentTab);
		equipmentButton.setHoverImage(equipmentTabSelect);
		equipmentButton.setDownImage(equipmentTabSelect);
		this.addButton(equipmentButton);
		
		Texture acceptUp = TextureRegistry.registerSprite("acceptButton", "upgradeUI/buttons_accept_on");
		Texture acceptHover = TextureRegistry.registerSprite("acceptButtonHover", "upgradeUI/buttons_accept_select2");
		accept = new AcceptButton(this, 753, 117, acceptUp);
		accept.setHoverImage(acceptHover);
		accept.setDownImage(acceptHover);
		this.addButton(accept);
		
		Texture dismiss = TextureRegistry.registerSprite("button_dismiss_on", "upgradeUI/Equipment/button_dismiss_on");
		Texture dismissSelect = TextureRegistry.registerSprite("button_dismiss_select2", "upgradeUI/Equipment/button_dismiss_select2");
		dismissButtons = new Button[8];
		for(int i = 0; i < ship.getCrew().size(); i++)
		{
			int offsetX = 415 + (i%3) * 170 + (i > 5 ? 86 : 0);
			int offsetY = 461 - (i/3) * 133;
			dismissButtons[i] = new CrewDismissButton(i, offsetX, offsetY, dismiss);
			dismissButtons[i].setHoverImage(dismissSelect);
			dismissButtons[i].setDownImage(dismissSelect);
			this.addButton(dismissButtons[i]);
		}

		acceptBase = TextureRegistry.registerSprite("acceptButtonBase", "upgradeUI/buttons_accept_base");
	}

	public void render(SpriteBatch batch)
	{
		batch.draw(background, 340, 164);
		upgradesButton.render(batch);
		equipmentButton.render(batch);
		
		for(int i = 0; i < 8; i++)
		{
			int offsetX = 415 + (i%3) * 170 + (i > 5 ? 86 : 0);
			int offsetY = 480 - (i/3) * 133;
			int mouseX = Gdx.input.getX();
			int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
			boolean hover = false;
			if(mouseX > offsetX && mouseX < offsetX + 100 && mouseY > offsetY && mouseY < offsetY + 67)
				hover = true;

			if(i < ship.getCrew().size())
			{
				Crew crew = ship.getCrew().get(i);
				
				// Render CrewBox here
				crewBox.render(batch, crew, offsetX, offsetY);
				
				dismissButtons[i].render(batch);
			}
			else
			{
				crewBox.render(batch, null, offsetX, offsetY);
			}
		}
		for(int i = ship.getCrew().size(); i < 8; i++)
		{
			
		}
		
		batch.draw(acceptBase, 753, 117);
		accept.render(batch);
	}
}

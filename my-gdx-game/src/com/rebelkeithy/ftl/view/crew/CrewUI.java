package com.rebelkeithy.ftl.view.crew;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rebelkeithy.ftl.crew.Crew;
import com.rebelkeithy.ftl.ship.Ship;
import com.rebelkeithy.ftl.view.Button;
import com.rebelkeithy.ftl.view.EquipmentTabButton;
import com.rebelkeithy.ftl.view.Fonts;
import com.rebelkeithy.ftl.view.GUI;
import com.rebelkeithy.ftl.view.TextureRegistry;
import com.rebelkeithy.ftl.view.UpgradesTabButton;
import com.rebelkeithy.ftl.view.upgrade.AcceptButton;

public class CrewUI extends GUI
{
	private Texture background;
	private Texture crewSlot;
	private Texture crewSlotSelected;
	private Texture crewSlotEmpty;
	
	private Texture acceptBase;
	private Button accept;
	
	private Button  upgradesButton;
	private Button  equipmentButton;
	private Button[] dismissButtons;
	
	private Ship ship;
	
	public CrewUI(Ship ship)
	{
		this.ship = ship;
		
		background = TextureRegistry.registerSprite("crew_main", "upgradeUI/Equipment/crew_main");
		crewSlot = TextureRegistry.registerSprite("box_crew_on", "upgradeUI/Equipment/box_crew_on");
		crewSlotSelected = TextureRegistry.registerSprite("box_crew_selected", "upgradeUI/Equipment/box_crew_selected");
		crewSlotEmpty = TextureRegistry.registerSprite("box_crew_off", "upgradeUI/Equipment/box_crew_off");
		
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
				
				if(hover)
				{
					batch.draw(new TextureRegion(crewSlotSelected, 0, 45, 100, 22), offsetX, offsetY-19);
					batch.draw(crewSlotSelected, offsetX, offsetY);
				}
				else
				{
					batch.draw(new TextureRegion(crewSlot, 0, 45, 100, 22), offsetX, offsetY-19);
					batch.draw(crewSlot, offsetX, offsetY);
				}
				dismissButtons[i].render(batch);
				
				Color color = Fonts.font8.getColor();
				if(mouseX > offsetX && mouseX < offsetX + 100 && mouseY > offsetY + 4 && mouseY < offsetY + 18)
					Fonts.font8.setColor(245/256f, 50/256f, 50/256f, 1);
				else
					Fonts.font8.setColor(Color.WHITE);
				int crewNameWidth = (int) Fonts.font8.getBounds(crew.getName()).width;
				Fonts.font8.draw(batch, crew.getName(), offsetX + 48 - crewNameWidth/2, offsetY + 15);
				Fonts.font8.setColor(color);
			}
			else
			{
				batch.draw(crewSlotEmpty, offsetX, offsetY);
			}
		}
		for(int i = ship.getCrew().size(); i < 8; i++)
		{
			
		}
		
		batch.draw(acceptBase, 753, 117);
		accept.render(batch);
	}
}

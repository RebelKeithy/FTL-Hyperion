package com.rebelkeithy.ftl.view.scene.hangar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rebelkeithy.ftl.ship.Ship;
import com.rebelkeithy.ftl.view.Button;
import com.rebelkeithy.ftl.view.TextureRegistry;

public class ShipButton extends Button
{
	private ListGUI gui;
	private Ship ship;
	private Texture miniShip;
	
	private Texture buttonsOn;
	private Texture buttonsSelect;
	
	private Texture challenge1On;
	private Texture challenge1Off;
	private Texture challenge2On;
	private Texture challenge2Off;
	private Texture challenge3On;
	private Texture challenge3Off;
	
	public ShipButton(ListGUI gui, Ship ship, int imageX, int imageY) 
	{
		super(imageX, imageY, TextureRegistry.registerSprite("ship_list_button_on", "customizeUI/ship_list_button_on"));
		
		this.gui = gui;
		this.ship = ship;
		this.image_down = TextureRegistry.registerSprite("ship_list_button_select2", "customizeUI/ship_list_button_select2");
		this.image_hover = TextureRegistry.registerSprite("ship_list_button_select2", "customizeUI/ship_list_button_select2");
		this.image_disabled = TextureRegistry.registerSprite("ship_list_button_off", "customizeUI/ship_list_button_off");
		buttonsOn = TextureRegistry.registerSprite("ship_list_button_lower_on", "customizeUI/ship_list_button_lower_on");
		buttonsSelect = TextureRegistry.registerSprite("ship_list_button_lower_select2", "customizeUI/ship_list_button_lower_select2");
		this.height += 40;
		this.screenY -= 40;
		
		challenge1On = TextureRegistry.registerSprite("S_1_on", "achievements/S_1_on");
		challenge1Off = TextureRegistry.registerSprite("S_1_off", "achievements/S_1_off");
		challenge2On = TextureRegistry.registerSprite("S_2_on", "achievements/S_2_on");
		challenge2Off = TextureRegistry.registerSprite("S_2_off", "achievements/S_2_off");
		challenge3On = TextureRegistry.registerSprite("S_3_on", "achievements/S_3_on");
		challenge3Off = TextureRegistry.registerSprite("S_3_off", "achievements/S_3_off");
		
		if(ship != null)
		{
			miniShip = TextureRegistry.getTexture(ship.renderData.miniShipTexture);
			this.setDisabled(false);
		}
		else
		{
			this.setDisabled(true);
		}
	}
	
	public void render(SpriteBatch batch)
	{
		
		if(ship != null)
		{
			super.render(batch);
			
			batch.draw(miniShip, imageX, imageY);
			
			if(hover)
			{
				batch.draw(buttonsSelect, imageX, imageY - 40);
			}
			else
			{
				batch.draw(buttonsOn, imageX, imageY - 40);
			}
			
			batch.draw(challenge1Off, imageX + 6, imageY - 34);
			batch.draw(challenge2Off, imageX + 43, imageY - 34);
			batch.draw(challenge3Off, imageX + 80, imageY - 34);
		}
		else
		{
			batch.draw(image_disabled, imageX, imageY - 17);
		}
	}
	
	public void leftClick()
	{
		//gui.chooseShip(ship);
	}

	public Ship getShip() 
	{
		return ship;
	}
}

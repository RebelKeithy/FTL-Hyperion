package com.rebelkeithy.ftl.view.scene.hangar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rebelkeithy.ftl.ship.Ship;
import com.rebelkeithy.ftl.ship.ShipRegistry;
import com.rebelkeithy.ftl.view.Button;
import com.rebelkeithy.ftl.view.GUI;
import com.rebelkeithy.ftl.view.TextureRegistry;

public class ListGUI extends GUI
{
	private HangerScreen hanger;
	private int offsetX;
	private int offsetY;
	private Texture background;

	private ListLayoutButton bTypeA;
	private ListLayoutButton bTypeB;
	private ListLayoutButton bTypeC;
	
	private int page = 0;
	private Button[][] shipButtons;
	
	
	public ListGUI(HangerScreen hanger)
	{
		super();
		
		this.hanger = hanger;
		background = TextureRegistry.registerSprite("ship_list_main_2", "customizeUI/ship_list_main_2");
		offsetX = (Gdx.graphics.getWidth() - background.getWidth())/2;
		offsetY = (Gdx.graphics.getHeight() - background.getHeight())/2;
		
		
		Texture bTypeAOn = TextureRegistry.registerSprite("button_typea_on", "customizeUI/button_typea_on");
		Texture bTypeAOff = TextureRegistry.registerSprite("button_typea_off", "customizeUI/button_typea_off");
		Texture bTypeASelect = TextureRegistry.registerSprite("button_typea_select2", "customizeUI/button_typea_select2");
		bTypeA = new ListLayoutButton(this, 0, offsetX + 700, offsetY + 378, bTypeAOn);
		bTypeA.setDownImage(bTypeASelect);
		bTypeA.setHoverImage(bTypeASelect);
		bTypeA.setDisabledImage(bTypeAOff);
		bTypeA.setSelected(true);
		addButton(bTypeA);

		Texture bTypeBOn = TextureRegistry.registerSprite("button_typeb_on", "customizeUI/button_typeb_on");
		Texture bTypeBOff = TextureRegistry.registerSprite("button_typeb_off", "customizeUI/button_typeb_off");
		Texture bTypeBSelect = TextureRegistry.registerSprite("button_typeb_select2", "customizeUI/button_typeb_select2");
		bTypeB = new ListLayoutButton(this, 1, offsetX + 784, offsetY + 378, bTypeBOn);
		bTypeB.setDownImage(bTypeBSelect);
		bTypeB.setHoverImage(bTypeBSelect);
		bTypeB.setDisabledImage(bTypeBOff);
		addButton(bTypeB);

		Texture bTypeCOn = TextureRegistry.registerSprite("button_typec_on", "customizeUI/button_typec_on");
		Texture bTypeCOff = TextureRegistry.registerSprite("button_typec_off", "customizeUI/button_typec_off");
		Texture bTypeCSelect = TextureRegistry.registerSprite("button_typec_select2", "customizeUI/button_typec_select2");
		bTypeC = new ListLayoutButton(this, 2, offsetX + 868, offsetY + 378, bTypeCOn);
		bTypeC.setDownImage(bTypeCSelect);
		bTypeC.setHoverImage(bTypeCSelect);
		bTypeC.setDisabledImage(bTypeCOff);
		addButton(bTypeC);
		
		shipButtons = new Button[3][10];
		
		Ship kestrel = ShipRegistry.build("The Kestrel", "The Kestrel");
		shipButtons[0][0] = new ShipButton(this, kestrel, offsetX + 24, offsetY + 240);
		this.addButton(shipButtons[0][0]);
		
		Ship engi = ShipRegistry.build("The Torus", "The Torus");
		shipButtons[0][1] = new ShipButton(this, engi, offsetX + 24 + 205, offsetY + 240);
		this.addButton(shipButtons[0][1]);
		
		Ship engiB = ShipRegistry.build("The Vortex", "The Vortex");
		shipButtons[0][2] = new ShipButton(this, engiB, offsetX + 24 + 205*2, offsetY + 240);
		this.addButton(shipButtons[0][2]);
	}
	
	public void render(SpriteBatch batch)
	{
		bTypeB.setPosition(offsetX + 784, offsetY + 378);
		if(show)
		{
			batch.draw(background, offsetX, offsetY);
			bTypeA.render(batch);
			bTypeB.render(batch);
			bTypeC.render(batch);
			
			shipButtons[0][0].render(batch);
			shipButtons[0][1].render(batch);
			shipButtons[0][2].render(batch);
		}
	}

	public void setPage(int page) 
	{
		bTypeA.setSelected(false);
		bTypeB.setSelected(false);
		bTypeC.setSelected(false);
		
		this.page = page;
	}

	public void chooseShip(Ship ship) 
	{
		hanger.choostFromList(ship);
	}
}

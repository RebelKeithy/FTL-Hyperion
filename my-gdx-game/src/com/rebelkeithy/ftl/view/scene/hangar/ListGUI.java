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
	private String[][] shipNames;
	private ShipButton[][] shipButtons;
	
	
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
		
		shipNames = new String[3][10];
		
		shipNames[0][0] = "The Kestrel";
		shipNames[1][0] = "Red-Tail";
		shipNames[0][1] = "The Torus";
		shipNames[1][1] = "The Vortex";
		
		shipButtons = new ShipButton[3][10];
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 10; j++)
			{
				Ship ship = null;
				if(shipNames[i][j] != null)
				{
					ship = ShipRegistry.build(shipNames[i][j], shipNames[i][j]);
				}
				
				shipButtons[i][j] = new ShipButton(this, ship, offsetX + 24 + 205 * (j%5), offsetY + 240 - 200 * (j/5));
			}
		}
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
			
			for(int j = 0; j < 10; j++)
			{
				shipButtons[page][j].render(batch);
			}
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

	@Override
	public boolean click(int screenX, int screenY, int button) 
	{
		super.click(screenX, screenY, button);
		
		if(!show)
			return false;
		
		
		for(int j = 0; j < 10; j++)
		{
			if(shipButtons[page][j].click(screenX, screenY, button))
			{
				hanger.setLayout(shipButtons[0][j].getShip(), shipButtons[1][j].getShip(), shipButtons[2][j].getShip(), page);
				return true;
			}
		}
		
		return false;
	}
}

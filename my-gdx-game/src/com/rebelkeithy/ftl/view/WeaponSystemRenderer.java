package com.rebelkeithy.ftl.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rebelkeithy.ftl.systems.AbstractShipSystem;
import com.rebelkeithy.ftl.systems.WeaponSystem;
import com.rebelkeithy.ftl.view.upgrade.Sounds;
import com.rebelkeithy.ftl.weapons.Weapon;

public class WeaponSystemRenderer extends SystemRenderer
{
	
	private Texture weapons1;
	private Texture weapons2;
	private Texture weapons3;
	
	private Texture weaponBoxEmpty;
	
	private Button autofire;

	private boolean dragging = false;
	private boolean readyToDrag = false;
	private int dragX;
	private int dragY;
	private int draggingSlot;
	
	public WeaponSystemRenderer(AbstractShipSystem system, int systemX) 
	{
		super(system, systemX);
		
		weapons1 = TextureRegistry.registerSprite("weapons_box_1", "box_weapons_bottom");
		weapons2 = TextureRegistry.registerSprite("weapons_box_1", "box_weapons_bottom2");
		weapons3 = TextureRegistry.registerSprite("weapons_box_1", "box_weapons_bottom3");
		
		Pixmap weaponBoxEmptyMap = new Pixmap(95, 39, Format.RGBA8888);
		weaponBoxEmptyMap.setColor(60/256f, 60/256f, 60/256f, 0.5f);
		weaponBoxEmptyMap.fill();
		weaponBoxEmptyMap.setColor(190/256f, 190/256f, 190/256f, 1);
		weaponBoxEmptyMap.drawRectangle(8, 0, 87, 39);
		weaponBoxEmptyMap.drawRectangle(9, 1, 85, 37);
		weaponBoxEmpty = new Texture(weaponBoxEmptyMap);
		weaponBoxEmptyMap.dispose();
		
		Texture autofire_up = TextureRegistry.registerSprite("autofire_up", "box_weapons_autofire_off");
		Texture autofire_down = TextureRegistry.registerSprite("autofire_down", "box_weapons_autofire_select");
		Texture autofire_hover = TextureRegistry.registerSprite("autofire_hover", "box_weapons_autofire_on");
		autofire = new ButtonAutofire(511, 10, 522, 23, 120, 29, autofire_up);
		autofire.setDownImage(autofire_down);
		autofire.setHoverImage(autofire_hover);
		FTLView.inputHandler.registerButton(autofire);
	}

	public void render(SpriteBatch batch)
	{
		super.render(batch);
		int screenX = Gdx.input.getX();
		int screenY = Gdx.graphics.getHeight() - Gdx.input.getY();
		
		batch.draw(weapons1, systemX + 13, systemY - 1);
		autofire.render(batch);

		float maxChargeTime = 0;
		for(int i = 0; i < 4; i++)
		{
			Weapon weapon = ((WeaponSystem)system).getWeapon(i);
			if(weapon != null)
			{
				if(weapon.getMaxCharge() > maxChargeTime)
				{
					maxChargeTime = (float) weapon.getMaxCharge();
				}
			}
		}
		for(int i = 0; i < 4; i++)
		{
			if(((WeaponSystem)system).getWeapon(i) != null)
			{
				WeaponBox boxRenderer = new WeaponBox(((WeaponSystem)system).getWeapon(i));
				if(dragging && draggingSlot == i)
				{
					boxRenderer.render(batch, systemX + 29 + i*97 + (screenX - dragX), systemY + 34 + (screenY - dragY), i+1, maxChargeTime);
				}
				else
				{
					boxRenderer.render(batch, systemX + 29 + i*97, systemY + 34, i+1, maxChargeTime);
				}
			}
			else
			{
				batch.draw(weaponBoxEmpty, systemX + 29 + i*97, systemY + 34);
			}
		}
		
		if(Gdx.input.isButtonPressed(0))
		{
		
			if(readyToDrag == false)
			{
				draggingSlot = -1;
				for(int i = 0; i < 4; i++)
				{
					if(screenX > systemX + 29 + i*97 && screenX < systemX + 29 + i*97 + 95 && screenY > systemY + 34 && screenY < systemY + 34 + 39)
					{
						draggingSlot = i;
						dragX = screenX;
						dragY = screenY;
						break;
					}
				}
				readyToDrag = true;
			}
			else
			{
				if(draggingSlot != -1 && (screenX - dragX) * (screenX - dragX) + (screenY - dragY) * (screenY - dragY) > 55)
				{
					dragging = true;
				}
			}
		}
		else
		{
			dragging = false;
			readyToDrag = false;
			draggingSlot = -1;
		}

		if(dragging)
		{
			for(int i = 0; i < 4; i++)
			{
				if(screenX > systemX + 29 + i*97 && screenX < systemX + 29 + i*97 + 95 && screenY > systemY + 34 && screenY < systemY + 34 + 39)
				{
					if(((WeaponSystem)system).getWeapon(i) != null && draggingSlot != i)
					{
						Weapon swap = ((WeaponSystem)system).getWeapon(i);
						Weapon temp = ((WeaponSystem)system).getWeapon(draggingSlot);
						((WeaponSystem)system).setWeapon(swap, draggingSlot);
						((WeaponSystem)system).setWeapon(temp, i);
						dragX -= (draggingSlot - i) * 97;
						draggingSlot = i;
					}
				}
			}
		}
	}

	public boolean click(int screenX, int screenY, int mouseButton) 
	{	
		if(super.click(screenX, screenY, mouseButton))
			return true;

		if(autofire != null && autofire.containsPoint(screenX, screenY))
		{
			if(mouseButton == 0)
				autofire.leftClick();
			if(mouseButton == 1)
				autofire.rightClick();
			return true;
		}


		for(int i = 0; i < 4; i++)
		{
			if(((WeaponSystem)system).getWeapon(i) != null)
			{
				if(screenX > systemX + 29 + i*97 && screenX < systemX + 29 + i*97 + 95 && screenY > systemY + 34 && screenY < systemY + 34 + 39)
				{
					if(mouseButton == 0)
					{
						
						if(!((WeaponSystem)system).getWeapon(i).isPowered())
						{
							((WeaponSystem)system).powerOnWeapon(i);
							if(((WeaponSystem)system).getWeapon(i).isPowered())
							{
								Sounds.playSound("buttonOn");
							}
						}
					}
					if(mouseButton == 1)
					{
						if(((WeaponSystem)system).getWeapon(i).isPowered())
						{
							((WeaponSystem)system).powerDownWeapon(i);
							if(!((WeaponSystem)system).getWeapon(i).isPowered())
							{
								Sounds.playSound("buttonOff");
							}
						}
					}
				}
			}
		}
		
		return false;
	}
}

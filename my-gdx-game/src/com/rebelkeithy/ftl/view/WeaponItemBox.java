package com.rebelkeithy.ftl.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rebelkeithy.ftl.weapons.Weapon;

public class WeaponItemBox 
{
	private Weapon weapon;
	
	private Texture backgroundOn;
	private Texture backgroundSelected;
	private Texture backgroundOff;
	private Texture weaponTexture;
	
	public WeaponItemBox(Weapon weapon)
	{
		this.weapon = weapon;
		
		backgroundOn = TextureRegistry.registerSprite("box_weapons_on", "upgradeUI/Equipment/box_weapons_on");
		backgroundOff = TextureRegistry.registerSprite("box_weapons_off", "upgradeUI/Equipment/box_weapons_off");
		backgroundSelected = TextureRegistry.registerSprite("box_weapons_selected", "upgradeUI/Equipment/box_weapons_selected");
		
		if(weapon != null)
		{
			//weaponTexture = TextureRegistry.registerSprite("", "");
		}
	}
	
	public void render(SpriteBatch batch, int offsetX, int offsetY)
	{
		if(weapon == null)
		{
			batch.draw(backgroundOff, offsetX, offsetY);
			return;
		}
		
		batch.draw(backgroundOn, offsetX, offsetY);
		
		int weaponNameWidth = (int) Fonts.font8.getBounds(weapon.getName()).width;
		Fonts.font8.draw(batch, weapon.getName(), offsetX + 58 - weaponNameWidth/2, offsetY + 14);
	}
}

package com.rebelkeithy.ftl.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rebelkeithy.ftl.weapons.Weapon;

public class WeaponBox 
{
	private Weapon weapon;
	
	private static boolean init = false;
	private static Texture weaponBoxEmpty;
	private static Texture weaponBox;
	private static Texture weaponBoxHover;
	
	private static Texture weaponPowerEmpty;
	private static Texture white;
	
	public WeaponBox(Weapon weapon)
	{
		this.weapon = weapon;
		
		if(!init)
			init();
	}
	
	public static void init()
	{
		weaponBox = TextureRegistry.getTexture("weaponBox");
		weaponBoxEmpty = TextureRegistry.getTexture("weaponBoxEmpty");
		weaponBoxHover = TextureRegistry.registerSprite("weaponBoxHover", "bar_weapon_minibox_glow");
		weaponPowerEmpty = TextureRegistry.getTexture("weaponPowerEmpty");
		white = TextureRegistry.getTexture("white");
		
		if(weaponBoxEmpty == null)
		{
			Pixmap weaponBoxEmptyMap = new Pixmap(95, 39, Format.RGBA8888);
			weaponBoxEmptyMap.setColor(60/256f, 60/256f, 60/256f, 0.5f);
			weaponBoxEmptyMap.fill();
			weaponBoxEmptyMap.setColor(190/256f, 190/256f, 190/256f, 1);
			weaponBoxEmptyMap.drawRectangle(8, 0, 87, 39);
			weaponBoxEmptyMap.drawRectangle(9, 1, 85, 37);
			weaponBoxEmpty = new Texture(weaponBoxEmptyMap);
			weaponBoxEmptyMap.dispose();
			TextureRegistry.registerSprite("weaponBoxEmpty", weaponBoxEmpty);
		}
		
		if(weaponBox == null)
		{
			Pixmap weaponBoxMap = new Pixmap(87, 39, Format.RGBA8888);
			weaponBoxMap.setColor(1, 1, 1, 1);
			weaponBoxMap.drawRectangle(0, 0, 87, 39);
			weaponBoxMap.drawRectangle(1, 1, 85, 37);
			weaponBoxMap.drawRectangle(75, 24, 12, 15);
			weaponBoxMap.drawRectangle(76, 25, 10, 13);
			weaponBox = new Texture(weaponBoxMap);
			weaponBoxMap.dispose();
			TextureRegistry.registerSprite("weaponBox", weaponBox);
		}
		
		if(weaponPowerEmpty == null)
		{
			Pixmap weaponPowerEmptyMap = new Pixmap(16, 7, Format.RGBA8888);
			weaponPowerEmptyMap.setColor(1, 1, 1, 1f);
			weaponPowerEmptyMap.drawRectangle(0, 0, 16, 7);
			weaponPowerEmpty = new Texture(weaponPowerEmptyMap);
			weaponPowerEmptyMap.dispose();
			TextureRegistry.registerSprite("weaponPowerEmpty", weaponPowerEmpty);
		}
		
		if(white == null)
		{
			Pixmap whiteMap = new Pixmap(1, 1, Format.RGBA8888);
			whiteMap.setColor(1, 1, 1, 1);
			whiteMap.fill();
			white = new Texture(whiteMap);
			whiteMap.dispose();
			TextureRegistry.registerSprite("white", white);
		}
		
		init = true;
	}
	
	public void render(SpriteBatch batch, int offsetX, int offsetY, int slot, float maxCharge)
	{
		int mouseX = Gdx.input.getX();
		int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
		boolean hover = mouseX > offsetX && mouseX < (offsetX + 95) && mouseY > offsetY && mouseY < (offsetY + 39);
		
		String weaponName = weapon.getName();
		String name1 = weaponName;
		String name2 = "";
		if(weaponName.contains(" "))
		{
			name1 = weaponName.substring(0,weaponName.indexOf(' ')); // "72"
			name2 = weaponName.substring(weaponName.indexOf(' ')+1);
		}

		Color color = new Color(1, 1, 1, 1);
		if(weapon.isPowered())
		{
			if(weapon.isCharged())
				color = new Color(120/256f, 1, 120/256f, 1);

			batch.setColor(color);
			for(int i = 0; i < weapon.requiredPower(); i++)
				batch.draw(new TextureRegion(white, 16, 7), offsetX + 12, offsetY + 4 + i*8);
		}
		else
		{
			color = new Color(1, 1, 1, 0.5f);
			batch.setColor(color);
			for(int i = 0; i < weapon.requiredPower(); i++)
				batch.draw(weaponPowerEmpty, offsetX + 12, offsetY + 4 + 8*i);
		}

		Fonts.font8.setColor(color);
		Fonts.ccFont.setColor(color);
		
		int height = (int) (33 * weapon.getMaxCharge()/maxCharge);
		float charge = (float) (weapon.getChargePercentage());
		
		batch.draw(new TextureRegion(white, 4, (int)(height*charge)), offsetX + 3, offsetY + 3);
		batch.draw(new TextureRegion(white, 6, 2), offsetX + 2, offsetY);
		batch.draw(new TextureRegion(white, 2, height + 4), offsetX, offsetY);
		batch.draw(new TextureRegion(white, 8, 2), offsetX, offsetY + height + 4);
		batch.draw(weaponBox, offsetX + 8, offsetY);
		if(hover)
			batch.draw(weaponBoxHover, offsetX - 10, offsetY - 15);
		Fonts.font8.draw(batch, name1, offsetX + 34, offsetY + 32);
		Fonts.font8.draw(batch, name2, offsetX + 34, offsetY + 17);
		Fonts.ccFont.draw(batch, String.valueOf(slot), offsetX + 88 + (slot < 2 ? 0 : -1), offsetY + 11);
		batch.setColor(1, 1, 1, 1);
		Fonts.font8.setColor(1, 1, 1, 1);
		Fonts.ccFont.setColor(1, 1, 1, 1);
		
	}
}

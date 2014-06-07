package com.rebelkeithy.ftl.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rebelkeithy.ftl.crew.Crew;
import com.rebelkeithy.ftl.crew.CrewRegistry;
import com.rebelkeithy.ftl.crew.Race;

public class CrewBox 
{
	private Texture crewSlot;
	private Texture crewSlotSelected;
	private Texture crewSlotEmpty;
	
	public CrewBox()
	{
		crewSlot = TextureRegistry.registerSprite("box_crew_on", "upgradeUI/Equipment/box_crew_on");
		crewSlotSelected = TextureRegistry.registerSprite("box_crew_selected", "upgradeUI/Equipment/box_crew_selected");
		crewSlotEmpty = TextureRegistry.registerSprite("box_crew_off", "upgradeUI/Equipment/box_crew_off");
	}
	
	public void render(SpriteBatch batch, Crew crew, int offsetX, int offsetY)
	{
		if(crew == null)
		{
			batch.draw(crewSlotEmpty, offsetX, offsetY);
			return;
		}
		
		int mouseX = Gdx.input.getX();
		int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
		
		boolean hover = mouseX > offsetX && mouseX < offsetX + 100 && mouseY > offsetY && mouseY < offsetY + 67;
		
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
		
		Race race = CrewRegistry.getRace(crew.getRace());
		Texture texture = TextureRegistry.registerSprite(race.texture, race.texture);
		batch.draw(new TextureRegion(texture,  0,  0, 35, 35), offsetX + 16, offsetY + 4, 70, 70);

		Color color = Fonts.font8.getColor();
		if(mouseX > offsetX && mouseX < offsetX + 100 && mouseY > offsetY + 4 && mouseY < offsetY + 18)
			Fonts.font8.setColor(245/256f, 50/256f, 50/256f, 1);
		else
			Fonts.font8.setColor(Color.WHITE);
		int crewNameWidth = (int) Fonts.font8.getBounds(crew.getName()).width;
		Fonts.font8.draw(batch, crew.getName(), offsetX + 48 - crewNameWidth/2, offsetY + 15);
		Fonts.font8.setColor(color);
	}
}

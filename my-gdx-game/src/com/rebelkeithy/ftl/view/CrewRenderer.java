package com.rebelkeithy.ftl.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Blending;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rebelkeithy.ftl.FTLGame;
import com.rebelkeithy.ftl.crew.Crew;
import com.rebelkeithy.ftl.crew.CrewRegistry;
import com.rebelkeithy.ftl.crew.CrewRegistry.Race;
import com.rebelkeithy.ftl.crew.CrewStates;
import com.rebelkeithy.ftl.crew.Skill;
import com.rebelkeithy.ftl.ship.Direction;

public class CrewRenderer 
{
	private static Map<String, CrewRenderer>renderers = new HashMap<String, CrewRenderer>();
	
	public static void registerCrewRenderer(Crew crew, CrewRenderer renderer)
	{
		renderers.put(crew.getName(), renderer);
	}
	
	public static CrewRenderer getCrewRenderer(Crew crew)
	{
		return renderers.get(crew.getName());
	}
	
	private Texture texture;
	private Texture textureGlow;
	
	private Texture healthBarBg;
	private Texture healthLowBarBg;
	private Texture healthBar;
	
	private int timer;
	private int spriteState;
	private int oldDirection;
	private double crewOldX;
	private double crewOldY;
	
	public CrewRenderer(Crew crew, String glow)
	{
		Race race = CrewRegistry.getRace(crew.getRace());
		this.texture = TextureRegistry.registerSprite(race.texture, race.texture);
		this.textureGlow = TextureRegistry.registerSprite(glow, glow);
		
		healthBarBg = TextureRegistry.registerSprite("healthBarBg", "people/health_box");
		healthLowBarBg = TextureRegistry.registerSprite("healthLowBarBg", "people/health_box_red");
		healthBar = TextureRegistry.getTexture("healthBar");
		lowHealth = TextureRegistry.registerSprite("lowHealth", "warnings/health_low");
		
		crewBox = TextureRegistry.getTexture("crewBox");
		crewBoxHover = TextureRegistry.getTexture("crewBoxHover");
		crewBoxSelected = TextureRegistry.getTexture("crewBoxSelected");
		
		if(crewBox == null)
		{
			Pixmap crewBoxMap = new Pixmap(86, 27, Format.RGBA8888);
			crewBoxMap.setColor(50/256f, 50/256f, 50/256f, 0.5f);
			crewBoxMap.fill();
			crewBoxMap.setColor(100/256f, 100/256f, 100/256f, 1);
			crewBoxMap.drawRectangle(0, 0, 86, 27);
			crewBoxMap.drawRectangle(1, 1, 84, 25);
			crewBox = new Texture(crewBoxMap);
			crewBoxMap.dispose();
			TextureRegistry.registerSprite("crewBox", crewBox);
		}
		
		if(crewBoxSelected == null)
		{
			Pixmap crewBoxMap = new Pixmap(86, 27, Format.RGBA8888);
			crewBoxMap.setColor(120/256f, 120/256f, 120/256f, 0.5f);
			crewBoxMap.fill();
			crewBoxMap.setColor(235/256f, 235/256f, 235/256f, 1);
			crewBoxMap.drawRectangle(0, 0, 86, 27);
			crewBoxMap.drawRectangle(1, 1, 84, 25);
			crewBoxSelected = new Texture(crewBoxMap);
			crewBoxMap.dispose();
			TextureRegistry.registerSprite("crewBoxSelected", crewBoxSelected);
		}
		
		if(crewBoxHover == null)
		{
			int height = 26 + 24*(Skill.getSkills().size()-1);
			//height = 26 + 24*5;
			Blending old = Pixmap.getBlending();
			Pixmap.setBlending(Blending.None);
			Pixmap crewBoxMap = new Pixmap(169, height, Format.RGBA8888);
			crewBoxMap.setColor(120/256f, 120/256f, 120/256f, 0.5f);
			crewBoxMap.fillRectangle(0, 0, 91, 27);
			crewBoxMap.fillRectangle(89, 0, 80, height);
			crewBoxMap.setColor(235/256f, 235/256f, 235/256f, 1);
			crewBoxMap.drawRectangle(0, 0, 91, 27);
			crewBoxMap.drawRectangle(1, 1, 89, 25);
			crewBoxMap.drawRectangle(89, 0, 80, height);
			crewBoxMap.drawRectangle(90, 1, 78, height-2);
			for(int i = 0; i < Skill.getSkills().size(); i++)
			{
				crewBoxMap.drawRectangle(123, 9 + i * 24, 40, 8);
			}
			crewBoxMap.setColor(120/256f, 120/256f, 120/256f, 0.5f);
			crewBoxMap.drawRectangle(89, 2, 2, 23);
			Pixmap.setBlending(old);
			
			crewBoxHover = new Texture(crewBoxMap);
			crewBoxMap.dispose();
			TextureRegistry.registerSprite("crewBoxHover", crewBoxHover);
		}
		
		if(healthBar == null)
		{
			Pixmap healthMap = new Pixmap(3, 3, Format.RGBA8888);
			healthMap.setColor(1, 1, 1, 1);
			healthMap.fill();
			healthBar = new Texture(healthMap);
			healthMap.dispose();
			TextureRegistry.registerSprite("healthBar", healthBar);
		}
		
		skillIcons = new HashMap<String, Texture>();
		List<String> skillNames = Skill.getSkills();
		for(String skill : skillNames)
		{
			Texture skillTexture = TextureRegistry.registerSprite(skill.toLowerCase() + "SkillIcon", Skill.getSkill(skill).getIcon());
			skillIcons.put(skill, skillTexture);
		}
	}
	
	public void render(SpriteBatch batch, Crew crew, int shipOffsetX, int shipOffsetY)
	{
		timer++;
		
		double crewY = crew.getY();
		double crewX = crew.getX();
		
		int direction = oldDirection;
		if(crewY > crewOldY)
		{
			direction = Direction.UP;
		}
		else if(crewY < crewOldY)
		{
			direction = Direction.DOWN;
		}
		else if(crewX > crewOldX)
		{
			direction = Direction.RIGHT;
		}
		else if(crewX < crewOldX)
		{
			direction = Direction.LEFT;
		}
		oldDirection = direction;
		
		crewOldX = crewX;
		crewOldY = crewY;
		
		int u = 0;
		int v = 0;
		float alpha = 1;
		
		Race race = CrewRegistry.getRace(crew.getRace());
		if(crew.state == CrewStates.WALKING)
		{
			if(direction == Direction.UP)
			{
				if(spriteState >= race.animations.get("walking_up").length)
					spriteState = 0;
				
				int frame = race.animations.get("walking_up")[spriteState];
				v = (frame / (texture.getWidth()/35)) * 35;
				u = (frame % (texture.getWidth()/35)) * 35;
			}
			if(direction == Direction.LEFT)
			{
				if(spriteState >= race.animations.get("walking_left").length)
					spriteState = 0;
				
				int frame = race.animations.get("walking_left")[spriteState];
				v = (frame / (texture.getWidth()/35)) * 35;
				u = (frame % (texture.getWidth()/35)) * 35;
			}
			if(direction == Direction.RIGHT)
			{
				if(spriteState >= race.animations.get("walking_right").length)
					spriteState = 0;
				
				int frame = race.animations.get("walking_right")[spriteState];
				v = (frame / (texture.getWidth()/35)) * 35;
				u = (frame % (texture.getWidth()/35)) * 35;
			}
			
			if(timer%20 == 0)
			{
				if(crew.isMoving())
				{
					spriteState++;
				}
				else
				{
					spriteState = 0;
				}
			}
		}
		if(crew.state == CrewStates.MANNING)
		{
			u = 35*4;
			v = 35*8;
			
			int stationDir = crew.getRoom().getSystem().getStationDir();
			
			if(stationDir == Direction.UP)
			{
				if(spriteState >= race.animations.get("manning_up").length)
					spriteState = 0;
				
				int frame = race.animations.get("manning_up")[spriteState];
				v = (frame / (texture.getWidth()/35)) * 35;
				u = (frame % (texture.getWidth()/35)) * 35;
			}
			if(stationDir == Direction.LEFT)
			{
				if(spriteState >= race.animations.get("manning_left").length)
					spriteState = 0;
				
				int frame = race.animations.get("manning_left")[spriteState];
				v = (frame / (texture.getWidth()/35)) * 35;
				u = (frame % (texture.getWidth()/35)) * 35;
			}
			if(stationDir == Direction.RIGHT)
			{
				if(spriteState >= race.animations.get("manning_right").length)
					spriteState = 0;
				
				int frame = race.animations.get("manning_right")[spriteState];
				v = (frame / (texture.getWidth()/35)) * 35;
				u = (frame % (texture.getWidth()/35)) * 35;
			}
			if(stationDir == Direction.DOWN)
			{
				if(spriteState >= race.animations.get("manning_down").length)
					spriteState = 0;
				
				int frame = race.animations.get("manning_down")[spriteState];
				v = (frame / (texture.getWidth()/35)) * 35;
				u = (frame % (texture.getWidth()/35)) * 35;
			}

			
			if(timer%10 == 0)
			{
				Random rand = new Random(System.nanoTime());
				int oldstate = spriteState;
				while(oldstate == spriteState)
				{
					spriteState = rand.nextInt(4);
				}
			}
			
		}
		if(crew.state == CrewStates.DYING)
		{
			u = 3*35;
			v = 12*35;
			spriteState = (int)(crew.getTimeDead()/200);
			

			if(spriteState >= race.animations.get("death").length)
				spriteState = race.animations.get("death").length - 1;
			
			int frame = race.animations.get("death")[spriteState];
			v = (frame / (texture.getWidth()/35)) * 35;
			u = (frame % (texture.getWidth()/35)) * 35;
			
			if(spriteState == race.animations.get("death").length - 1)
			{
				alpha = 1 - (crew.getTimeDead() - (200 * race.animations.get("death").length))/(200 * race.animations.get("death").length);
				System.out.println(alpha);
				if(alpha > 1)
					alpha = 1;
				if(alpha < 0)
					alpha = 0;
				spriteState = 4;
				
			}
					
		}
		if(crew.state == CrewStates.IDLE)
		{
			/* DANCING!
			if(timer%15 == 0)
			{
				Random rand = new Random(System.nanoTime());
				int oldstate = spriteState;
				while(oldstate == spriteState)
				{
					spriteState = rand.nextInt(4);
				}
			}
			*/
			
			spriteState = 0;
		}
		
		//u += spriteState*35;

		//TODO: wont work in battle because of extra 150 shift left
		int mouseX = Gdx.input.getX() - shipOffsetX;
		int mouseY = (Gdx.graphics.getHeight() - Gdx.input.getY()) - shipOffsetY;
		
		
		boolean hover = false;
		if(FTLGame.instance().getPlayer() != null && FTLGame.instance().getPlayer().getName().equals(crew.getHomeShip()))
		{
			if(mouseX > crew.getX()*35 && mouseX < crew.getX()*35 + 35 && mouseY > crew.getY()*35 && mouseY < crew.getY()*35 + 35)
			{
				hover = true;
			}
		}
		
		if(hover == true)
		{
			FTLView.instance().setTooltipText(crew.getName() + "\nHealth: " + (int)crew.getHealth() + "/" + (int)crew.getMaxHealth(), shipOffsetX + (int)(crew.getX()*35), shipOffsetY + (int)(crew.getY()*35), 35, 35);
		}
		
		float health = (float) (crew.getHealth()/crew.getMaxHealth());
		boolean lowHealthTimer = (System.currentTimeMillis()/500)%2 == 0 && !hover && health < 0.25;
		
		// Draw health bar
		if(FTLView.inputHandler.selected == crew || hover || health < 0.25 && crew.state != CrewStates.DYING)
		{
			if(lowHealthTimer)
				batch.draw(healthLowBarBg, shipOffsetX + (float)crew.getX()*35 - 1, shipOffsetY + (float)crew.getY()*35 + 25);
			else
				batch.draw(healthBarBg, shipOffsetX + (float)crew.getX()*35 - 1, shipOffsetY + (float)crew.getY()*35 + 25);
			batch.setColor(0, 1, 0, 1);
			batch.draw(healthBar, shipOffsetX + (float)crew.getX()*35 + 4, shipOffsetY + (float)crew.getY()*35 + 29, health*25, 3);
			batch.setColor(1, 1, 1, 1);
		}
		
		// Draw crew glow
		if(FTLView.inputHandler.selected == crew || hover)
		{
			TextureRegion regionGlow = new TextureRegion(textureGlow, u, v, 35, 35);
			batch.setColor(0, 0.8f, 0, 0.5f);
			batch.draw(regionGlow, shipOffsetX + (float)crew.getX()*35, shipOffsetY + (float)crew.getY()*35);
			batch.setColor(1, 1, 1, 1);
		}
		else// if(crew.state != CrewStates.DYING)
		{
			TextureRegion regionGlow = new TextureRegion(textureGlow, u, v, 35, 35);
			batch.setColor(1, 1, 0/156f, 1);
			batch.draw(regionGlow, shipOffsetX + (float)crew.getX()*35, shipOffsetY + (float)crew.getY()*35);
			batch.setColor(1, 1, 1, 1);
		}

		TextureRegion region = new TextureRegion(texture, u, v, 35, 35);
		batch.setColor(1, 1, 1, alpha);
		if(FTLView.inputHandler.selected == crew || hover)
			batch.setColor(120/256f, 1, 120/256f, alpha);
		batch.draw(region, shipOffsetX + (float)crew.getX()*35, shipOffsetY + (float)crew.getY()*35);
		batch.setColor(1, 1, 1, 1);
	}
	
	private Texture crewBox;
	private Texture crewBoxHover;
	private Texture crewBoxSelected;
	private Texture lowHealth;
	private Map<String, Texture> skillIcons;
	boolean hover;
	
	public void renderUI(SpriteBatch batch, Crew crew, int crewNum)
	{
		int offsetY = -crewNum * 30;
		
		int mouseX = Gdx.input.getX();
		int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
		float health = (float) (crew.getHealth()/crew.getMaxHealth());
		
		if(hover)
			hover = mouseX >= 10 + crewBox.getWidth() && mouseX < 10 + crewBoxHover.getWidth() && mouseY > 538+offsetY - (crewBoxHover.getHeight() - crewBox.getHeight()) && mouseY < 538+offsetY+crewBox.getHeight();
		if(!hover)
			hover = mouseX > 10 && mouseX < 10 + crewBox.getWidth() && mouseY > 538+offsetY && mouseY < 538+offsetY+crewBox.getHeight();
		boolean lowHealthTimer = (System.currentTimeMillis()/500)%2 == 0 && !hover && health < 0.25;
		
		if(hover)
		{
			if(FTLView.inputHandler.selected == crew)
				batch.setColor(140/256f, 1, 140/256f, 1);
			batch.draw(crewBoxHover, 10, 538 + offsetY - (crewBoxHover.getHeight() - crewBox.getHeight()));
			int i = 0;
			batch.setColor(1, 1, 1, 1);
			for(String skillName : Skill.getSkills())
			{
				Texture skillTexture = skillIcons.get(skillName);
				batch.draw(skillTexture, 103, 538 + offsetY - (i * 24 - 1));
				
				if(mouseX >= 10 + crewBox.getWidth() && mouseX < 10 + crewBoxHover.getWidth() && mouseY > 538 + offsetY - (i * 24 - 1) && mouseY < 538 + offsetY - (i * 24 - 1) + 24)
				{
					FTLView.instance().setTooltipText(Skill.getSkill(skillName).getTooltip(crew), 10 + crewBox.getWidth(), 538 + offsetY - (i * 24 - 1), crewBoxHover.getWidth() - crewBox.getWidth(), 24);
				}
				i++;
			}
		}
		else if(FTLView.inputHandler.selected == crew)
		{
			batch.setColor(140/256f, 1, 140/256f, 1);
			batch.draw(crewBoxSelected, 10, 538 + offsetY);
			batch.setColor(1, 1, 1, 1);
		}
		else
		{
			if(lowHealthTimer)
			{
				batch.setColor(1, 0.4f, 0.4f, 1);
				batch.draw(crewBoxSelected, 10, 538 + offsetY);
				batch.setColor(1, 1, 1, 1);
			}
			else
			{
				batch.draw(crewBox, 10, 538 + offsetY);
			}
		}
		
		TextureRegion regionGlow = new TextureRegion(textureGlow, 0, 0, 35, 35);
		batch.setColor(1, 1, 0/156f, 1);
		batch.draw(regionGlow, 10, 532 + offsetY);
		batch.setColor(1, 1, 1, 1);

		TextureRegion crewRegion = new TextureRegion(texture, 0, 0 , 35, 35);
		batch.draw(crewRegion, 10, 532 + offsetY);
		
		batch.setColor(getHealthColor(health));
		batch.draw(healthBar, 43, 542 + offsetY, health*49, 4);
		batch.setColor(1, 1, 1, 1);
		
		if(lowHealthTimer)
		{
			batch.draw(lowHealth, 94, 534 + offsetY);
		}
		
		String name = crew.getName();
		Fonts.font8.setColor(200/256f, 200/256f, 200/256f, 1);
		Fonts.font8.draw(batch, name, 43, 559 + offsetY);
	}
	
	public void click(int mouseX, int mouseY, int button, Crew crew, int i)
	{
		int offsetY = -i * 30;

		if(button == 0 && mouseX > 10 && mouseX < 10 + crewBox.getWidth() && mouseY > 538+offsetY && mouseY < 538+offsetY+crewBox.getHeight())
		{
			FTLView.inputHandler.selected = crew;
		}
	}
	
	// healthRatio should be a number between 0 and 1
	private Color getHealthColor(float healthRatio)
	{
		if(healthRatio > 0.99)
		{
			return new Color(0, 1, 0, 1);
		}
		else if(healthRatio >= 0.5)
		{
			return new Color(1, 1, 0, 1);
		}
		else
		{
			return new Color(1, healthRatio*2, 0, 1);
		}
	}
}

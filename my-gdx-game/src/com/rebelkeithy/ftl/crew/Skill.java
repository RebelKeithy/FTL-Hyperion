package com.rebelkeithy.ftl.crew;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rebelkeithy.ftl.properties.Properties;


public class Skill
{
	private static List<String> skillList = new ArrayList<String>();
	private static Map<String, Skill> skills = new HashMap<String, Skill>();
	private static Skill nullSkill = new Skill("NULL");
	
	private static void registerSkill(String name, Skill skill)
	{
		if(!skills.containsKey(name))
		{
			skills.put(name, skill);
			skillList.add(name);
			System.out.println("registering " + name);
		}
	}
	
	public static Skill getSkill(String name)
	{
		if(skills.containsKey(name))
			return skills.get(name);
		
		return nullSkill;
	}
	
	public static List<String>getSkills()
	{
		return skillList;
	}
	
	private String name;
	private String icon;
	private List<Integer> levels;
	private String description;
	
	private Skill(String name)
	{
		this.name = name;
		description = name;
		levels = new ArrayList<Integer>();
	}
	
	public Skill(String name, String icon, String desctiption, int xpForLevel1)
	{
		this.name = name;
		this.icon = icon;
		this.description = desctiption;
		
		levels = new ArrayList<Integer>(); 
		levels.add(xpForLevel1);
		
		registerSkill(name, this);
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public String getTooltip(Crew crew)
	{
		return getName() + " Skill:\n" + description + "\nNext Level: " + getXPForCurrentLevel(crew) + "/" + getXPRequiredForLevel((getCurrentLevel(crew) + 1));
	}
	
	public String getIcon()
	{
		return icon;
	}
	
	public void addLevel(int xpForNextLevel)
	{
		levels.add(xpForNextLevel);
	}
	
	public void addXP(Crew crew, int amount)
	{
		Properties properties = crew.getProperties();
		int oldLevel = getCurrentLevel(crew);
		int xp = properties.getInteger(name + "_xp");
		properties.setInteger(name + "_xp", xp + amount);
		int currLevel = getCurrentLevel(crew);
		if(oldLevel != currLevel)
		{
			// TODO: Level up event (used for rendering)
		}
	}
	
	public int getCurrentLevel(Crew crew)
	{
		Properties properties = crew.getProperties();
		int xp = properties.getInteger(name + "_xp");
		int level = 0;
		while(level < levels.size() && xp > levels.get(level))
		{
			xp -= levels.get(level);
			level++;
		}
		
		return level;
	}
	
	public int getXPForCurrentLevel(Crew crew)
	{
		Properties properties = crew.getProperties();
		int xp = properties.getInteger(name + "_xp");
		int level = 0;
		while(xp > levels.get(level) && level < levels.size())
		{
			if(xp - levels.get(level) > 0)
				xp -= levels.get(level);
			level++;
		}
		
		return xp;// + levels.get(level - 1);
	}
	
	public int getXPRequiredForLevel(int level)
	{
		if(level - 1 < levels.size())	
			return levels.get(level - 1);
		
		return -1;
	}
}

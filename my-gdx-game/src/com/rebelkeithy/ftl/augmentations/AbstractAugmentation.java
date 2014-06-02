package com.rebelkeithy.ftl.augmentations;

import java.util.Set;

import com.rebelkeithy.ftl.FTLGame;
import com.rebelkeithy.ftl.properties.Properties;
import com.rebelkeithy.ftl.ship.Ship;

public class AbstractAugmentation 
{
	protected String name;
	
	// TODO: make this a ship variable (property variable)
	private static int maxAugs = 3;
	
	public AbstractAugmentation(String name)
	{
		this.name = name;
	}
	
	public void install(String shipName)
	{
		if(getNumAugs(shipName) < maxAugs)
		{
			addNumInstalled(shipName, 1);
			Ship ship = FTLGame.instance().getShip(shipName);
			ship.EVENT_BUS.register(this);
		}
	}
	
	public void remove(String shipName)
	{
		int numInstalled = getNumInstalled(shipName);
		
		if(numInstalled == 1)
		{
			Ship ship = FTLGame.instance().getShip(shipName);
			ship.EVENT_BUS.unregister(this);
		}
		
		addNumInstalled(shipName, -1);		
	}
	
	public int getNumInstalled(String shipName)
	{
		Ship ship = FTLGame.instance().getShip(shipName);
		if(ship == null)
			return 0;
		
		Properties prop = ship.getProperties();
		Properties augmentations = prop.getProperty("augmentations");
		if(augmentations == null)
		{
			return 0;
		}
		
		return augmentations.getInteger(name);
	}
	
	public void addNumInstalled(String shipName, int num)
	{
		Ship ship = FTLGame.instance().getShip(shipName);
		if(ship == null)
			return;
		
		String augName = name;
		AbstractAugmentation aug = AugmentationRegistry.getAugmentation(augName);
		if(aug == null)
			return;
		
		Properties prop = ship.getProperties();
		Properties augmentations = prop.getProperty("augmentations");
		if(augmentations == null)
		{
			augmentations = new Properties();
			prop.setProperty("augmentations", augmentations);
		}
		
		Set<String> augs = augmentations.getKeys();
		int numAugs = 0;
		for(String i : augs)
		{
			numAugs += augmentations.getInteger(i);
		}

		int numThisAug = augmentations.getInteger(augName);
		if(numAugs + num <= maxAugs  && numThisAug + num > 0)
		{
			augmentations.setInteger(augName, numThisAug + num);
		}
	}
	
	public int getNumAugs(String shipName)
	{
		Ship ship = FTLGame.instance().getShip(shipName);
		if(ship == null)
			return 0;
		
		String augName = name;
		AbstractAugmentation aug = AugmentationRegistry.getAugmentation(augName);
		if(aug == null)
			return 0;
		
		Properties prop = ship.getProperties();
		Properties augmentations = prop.getProperty("augmentations");
		if(augmentations == null)
		{
			augmentations = new Properties();
			prop.setProperty("augmentations", augmentations);
		}
		
		Set<String> augs = augmentations.getKeys();
		int numAugs = 0;
		for(String i : augs)
		{
			numAugs += augmentations.getInteger(i);
		}
		
		return numAugs;
	}
}

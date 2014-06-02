package com.rebelkeithy.ftl.actions;

import java.util.Set;

import com.rebelkeithy.ftl.augmentations.AbstractAugmentation;
import com.rebelkeithy.ftl.augmentations.AugmentationRegistry;
import com.rebelkeithy.ftl.properties.Properties;
import com.rebelkeithy.ftl.ship.Ship;

public class InstallAugmentationAction extends Action
{
	public InstallAugmentationAction(String action) 
	{
		super(action);
	}

	@Override
	public void preform(String action, Object... params) 
	{
		//TODO: Check for augmentation in inventory

		Ship ship = game.getShip((String) params[0]);
		if(ship == null)
			return;
		
		String augName = (String) params[1];
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
		
		if(numAugs < 3)
		{
			int numThisAug = augmentations.getInteger(augName);
			augmentations.setInteger(augName, numThisAug + 1);
			aug.install(ship.getName());
		}
		
	}
	
	
}

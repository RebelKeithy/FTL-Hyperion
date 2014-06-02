package com.rebelkeithy.ftl.augmentations;

import java.util.HashMap;
import java.util.Map;

public class AugmentationRegistry 
{
	private static Map<String, AbstractAugmentation> augmentations = new HashMap<String, AbstractAugmentation>();
	
	public static void register(String name, AbstractAugmentation augmentation)
	{
		augmentations.put(name, augmentation);
	}
	
	public static AbstractAugmentation getAugmentation(String name)
	{
		return augmentations.get(name);
	}
}

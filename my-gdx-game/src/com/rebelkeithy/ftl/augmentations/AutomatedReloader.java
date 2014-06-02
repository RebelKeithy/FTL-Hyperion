package com.rebelkeithy.ftl.augmentations;

import com.google.common.eventbus.Subscribe;
import com.rebelkeithy.ftl.event.WeaponChargeEvent;

public class AutomatedReloader extends AbstractAugmentation
{
	public AutomatedReloader(String name) 
	{
		super(name);
	}

	@Subscribe
	public void weaponChargeEvent(WeaponChargeEvent event)
	{
		int numInstalled = getNumInstalled(event.getShip());

		event.chargeRate *= 1/(1 - (0.10 * (double)numInstalled));
	}
}

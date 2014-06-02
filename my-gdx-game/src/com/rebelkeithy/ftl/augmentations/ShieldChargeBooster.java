package com.rebelkeithy.ftl.augmentations;

import com.google.common.eventbus.Subscribe;
import com.rebelkeithy.ftl.event.ShieldChargeEvent;

public class ShieldChargeBooster extends AbstractAugmentation
{
	public ShieldChargeBooster(String name) 
	{
		super(name);
	}

	@Subscribe
	public void shieldChargeEvent(ShieldChargeEvent event)
	{
		int numInstalled = getNumInstalled(event.getShip());

		event.chargeRate *= 1/(1 - (0.15 * (double)numInstalled));
	}
}

package com.rebelkeithy.ftl.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rebelkeithy.ftl.systems.AbstractShipSystem;

public class SubSystemRenderer extends SystemRenderer 
{
	public SubSystemRenderer(AbstractShipSystem system, int systemX) 
	{
		super(system, systemX);
		unmanned = TextureRegistry.registerSprite("unmannedSubsystem", "systemUI/manning_bar_off");
		manned1 = TextureRegistry.registerSprite("mannedSubsystem", "systemUI/manning_bar_on");
	}

	@Override
	protected void createButton()
	{
		
	}
	
	public void drawMannIcon(SpriteBatch batch)
	{
		if(system.canMann() && system.getPower() > 0)
		{
			if(!system.isManned())
				batch.draw(unmanned, systemX-19 + 24, systemY + 30 + system.getMaxPower()*8);
			else
				batch.draw(manned1, systemX-19 + 24, systemY + 30 + system.getMaxPower()*8);
		}
	}
}

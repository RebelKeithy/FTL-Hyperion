package com.rebelkeithy.ftl.event;

import com.rebelkeithy.ftl.projectile.Projectile;
import com.rebelkeithy.ftl.ship.Ship;

public class HitShieldsEvent extends Event
{
	public Projectile projectile;
	public Ship target;
	public Ship source;
	
	public int shieldDamage;
	
	public HitShieldsEvent(Projectile projectile, Ship target, Ship source, int shieldDamage) 
	{
		this.projectile = projectile;
		this.target = target;
		this.source = source;
		this.shieldDamage = shieldDamage;
	}
}

package com.rebelkeithy.ftl.weapons;

import java.util.HashMap;
import java.util.Map;

import com.rebelkeithy.ftl.FTLGame;
import com.rebelkeithy.ftl.projectile.Bomb;
import com.rebelkeithy.ftl.ship.Resource;

public class BombWeapon extends Weapon
{
	private Map<String, Integer> damages;
	private String ammo = null;

	private String targetedShip;
	private String targetedRoom;
	
	public BombWeapon(String name, int requiredPower, double chargeTime) 
	{
		super(name, requiredPower, chargeTime);
		
		damages = new HashMap<String, Integer>();
	}
	
	public BombWeapon setDamage(String damageType, int amount)
	{
		damages.put(damageType, amount);
		return this;
	}
	
	public BombWeapon setAmmo(String ammo)
	{
		this.ammo = ammo;
		return this;
	}
	
	@Override 
	public void update(double dt)
	{
		super.update(dt);
	}

	@Override
	public void setTarget(String ship, String room)
	{
		this.targetedShip = ship;
		this.targetedRoom = room;
	}

	@Override
	public void activate() 
	{
		if(targetedShip == null || targetedRoom == null)
		{
			return;
		}
		
		if(isCharged())
		{
			if(ammo != null)
			{
				if(Resource.getResource(ammo).getResourceAmount(getShip()) > 0)
				{
					Resource.getResource(ammo).addResource(getShip(), -1);
				}
				else
				{
					return;
				}
			}
			
			fire(targetedShip, targetedRoom);
			
			removeCharge();
		}
	}
	
	public void fire(String ship, String room)
	{
		Bomb bomb = new Bomb(ship, room, getShip().getName());
		for(String damage : damages.keySet())
		{
			bomb.addDamage(damage, damages.get(damage));
		}
		
		FTLGame.instance().addShot(bomb);
	}

}

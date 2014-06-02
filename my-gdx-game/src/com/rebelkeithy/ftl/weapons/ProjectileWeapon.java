package com.rebelkeithy.ftl.weapons;

import java.util.HashMap;
import java.util.Map;

import com.rebelkeithy.ftl.Clock;
import com.rebelkeithy.ftl.projectile.Projectile;
import com.rebelkeithy.ftl.ship.Resource;

public class ProjectileWeapon extends Weapon
{
	private int numShots = 1;
	private int shieldPiercing = 0;
	private Map<String, Integer> damages;
	private String ammo = null;
	
	private int currentShot;
	private double sequentialShotDelay = 0.2;
	private double shotWait = 0;
	
	private String targetedShip;
	private String targetedRoom;
	
	Projectile[] projectileBuffer;

	public ProjectileWeapon(String name, int requiredPower, double chargeTime) 
	{
		super(name, requiredPower, chargeTime);
		
		damages = new HashMap<String, Integer>();
		projectileBuffer = new Projectile[0];
	}
	
	public ProjectileWeapon setNumShots(int numShots)
	{
		this.numShots = numShots;
		projectileBuffer = new Projectile[numShots - 1];
		return this;
	}
	
	public ProjectileWeapon setPiece(int pierce)
	{
		shieldPiercing = pierce;
		return this;
	}
	
	public ProjectileWeapon setDamage(String name, int damage)
	{
		damages.put(name, damage);
		return this;
	}
	
	public ProjectileWeapon setAmmo(String ammo)
	{
		this.ammo = ammo;
		return this;
	}
	
	@Override
	public void update(double dt)
	{
		super.update(dt);
		
		if(currentShot > 0)
		{
			if(currentShot == numShots)
			{
				currentShot = 0;
			}
			else
			{
				shotWait += dt;
				if(shotWait >= sequentialShotDelay)
				{
					shotWait -= sequentialShotDelay;

					Clock.log(getShip().getName() + " " + getName() + " fire!");
					getShip().getGame().addShot(projectileBuffer[currentShot-1]);
					currentShot++;
				}
			}
		}
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
			targetedShip = null;
			targetedRoom = null;
			
			removeCharge();
		}
	}

	public void fire(String ship, String room)
	{
		Clock.log(getShip().getName() + " " + getName() + " fire! " + damages);
		Projectile projectile = getProjectile(ship, room);
		getShip().getGame().addShot(projectile);
		
		for(int i = 0; i < numShots-1; i++)
		{
			projectileBuffer[i] = getProjectile(ship, room);
		}
		
		currentShot = 1;
	}
	
	// Returns an instance of the type of projectile this weapon fires
	private Projectile getProjectile(String ship, String room)
	{
		Projectile projectile = new Projectile(ship, room, getShip().getName(), 1000, 500);
		projectile.setShieldPiercing(shieldPiercing);
		for(String name : damages.keySet())
			projectile.addDamage(name, damages.get(name));
		return projectile;
	}
}

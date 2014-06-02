package com.rebelkeithy.ftl.systems;

import com.rebelkeithy.ftl.Clock;
import com.rebelkeithy.ftl.ship.Ship;
import com.rebelkeithy.ftl.view.SystemRenderer;
import com.rebelkeithy.ftl.view.WeaponSystemRenderer;
import com.rebelkeithy.ftl.weapons.Weapon;

public class WeaponSystem extends AbstractShipSystem
{
	Weapon[] weapons;

	public WeaponSystem(Ship ship, String name, int maxPower) 
	{
		super(ship, name, maxPower);
		
		
		weapons = new Weapon[4];
	}
	
	@Override
	public void damage(int amount)
	{
		super.damage(amount);
		
		int count = weapons.length - 1;
		Clock.log(getShip().getName() + " weapon reserver power " + unusedPower());
		while(unusedPower() < 0 && count >= 0)
		{
			powerDownWeapon(count);
			count--;
		}
	}
	
	public String getDisplayName() { return "Weapon Control"; }
	
	public String getDescription() 
	{ 
		return "Powers all of the ship's weapons. Upgrading\nlets you power more weapons"; 
	}
	
	public String getUpgradeDescription(int level)
	{
		return "More System Power";
	}
	
	@Override
	public void update(double dt)
	{
		super.update(dt);
		
		for(Weapon weapon : weapons)
		{
			if(weapon != null)
			{
				weapon.update(dt);
			}
		}
	}
	
	public void powerOnWeapon(int slot)
	{
		if(weapons[slot] != null)
		{
			int neededPower = weapons[slot].requiredPower() - unusedPower();
			if(neededPower <= getMaxPower() - getPower())
			{
				if(neededPower > 0)
				{
					ReactorSystem reactor = (ReactorSystem) getShip().getSystem("reactor");
					if(reactor.getPower() >= neededPower)
					{
						this.addPower(neededPower);
						Clock.log(getShip().getName() + " powering on weapon " + slot);
						weapons[slot].setPowered(true);
					}
					else
					{
						Clock.log(getShip().getName() + " not enough power to power on weapon " + slot);
					}
				}
				else
				{
					Clock.log(getShip().getName() + " powering on weapon " + slot);
					weapons[slot].setPowered(true);
				}
			}
		}
	}
	
	public void powerDownWeapon(int slot)
	{
		if(weapons[slot] != null && weapons[slot].isPowered())
		{
			Clock.log(getShip().getName() + " powering down weapon " + slot);
			weapons[slot].setPowered(false);
			this.addPower(-weapons[slot].requiredPower());
		}
	}
	
	public int unusedPower()
	{
		int power = getPower();
		
		for(int i = 0; i < weapons.length; i++)
		{
			if(weapons[i] != null && weapons[i].isPowered())
			{
				power -= weapons[i].requiredPower();
			}
		}
		
		return power;
	}
	
	public void setWeapon(Weapon weapon, int slot)
	{
		this.weapons[slot] = weapon;
		weapon.setShip(getShip());
	}
	
	public Weapon getWeapon(int slot)
	{
		return weapons[slot];
	}
	
	public Weapon removeWeapon(int slot)
	{
		Weapon weapon = weapons[slot];
		weapons[slot] = null;
		return weapon;
	}
	
	public void setTarget(int slot, String ship, String room)
	{
		if(weapons[slot] != null)
		{
			weapons[slot].setTarget(ship, room);
		}
	}
	
	public void activate(int slot, String ship, String room)
	{
		if(weapons[slot] != null)
		{
			weapons[slot].activate();
		}
	}
	
	public SystemRenderer getSystemRenderer()
	{
		return new WeaponSystemRenderer(this, 0);
	}
}

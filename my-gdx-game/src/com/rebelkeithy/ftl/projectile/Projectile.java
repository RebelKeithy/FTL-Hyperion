package com.rebelkeithy.ftl.projectile;

import java.util.HashMap;
import java.util.Map;

import com.rebelkeithy.ftl.FTLGame;
import com.rebelkeithy.ftl.ship.Ship;

public class Projectile extends Shot
{
	private String target;
	private String source;
	private String room;
	
	private int shieldPiercing = 0;
	private Map<String, Integer> damages;
	
	public double distance;
	public double speed;
	
	private boolean canDodge = true;
	private boolean dodged = false;
	private boolean dead = false;
	
	private boolean pastShields;
	
	public Projectile(String target, String room, String source, double distance, double speed)
	{
		this.target = target;
		this.room = room;
		this.source = source;
		this.distance = distance;
		this.speed = speed;
		damages = new HashMap<String, Integer>();
		
		pastShields = false;
	}
	
	public void addDamage(String name, int amount)
	{
		damages.put(name, amount);
	}
	
	public void update(double dt)
	{
		distance -= dt*speed;
		
		//System.out.println(Clock.instance().time() + ": distance " + distance);
		if(distance < 500 && !pastShields)
		{
			Ship targetShip = FTLGame.instance().getShip(target);
			if(targetShip.getShieldLvl() > shieldPiercing)
				targetShip.hitShields(this);
			
			pastShields = true;
			//System.out.println("proj " + distance);
		}
		if(distance <= 0 && !dodged)
		{
			Ship targetShip = FTLGame.instance().getShip(target);
			targetShip.getRoom(room).hit(this);
		}
		if(distance < -100)
		{
			this.kill();
		}
	}
	
	public int getShieldPiercing()
	{
		return shieldPiercing;
	}
	
	public void setShieldPiercing(int shieldPiercing)
	{
		this.shieldPiercing = shieldPiercing;
	}
	
	public int getDamage(String name)
	{
		if(damages.containsKey(name))
		{
			return damages.get(name);
		}
		
		return 0;
	}

	public Map<String, Integer> getDamages() 
	{
		return damages;
	}
	
	public boolean causesDamage(String name)
	{
		return damages.containsKey(name);
	}
	
	public boolean canDodge()
	{
		return canDodge;
	}
	
	public void dodge()
	{
		dodged = true;
	}

	public String getSource() 
	{
		return source;
	}

	public void kill() 
	{
		dead = true;
	}
	
	public boolean isDead()
	{
		return dead;
	}
}

package com.rebelkeithy.ftl.projectile;

import java.util.HashMap;
import java.util.Map;

import com.rebelkeithy.ftl.FTLGame;

public class Bomb extends Shot
{
	private String target;
	private String room;
	private String source;
	
	private Map<String, Integer> damages;
	
	private double countdown = 1;
	private boolean dead = false;
	
	public Bomb(String target, String room, String source)
	{
		this.target = target;
		this.room = room;
		this.source = source;
		
		damages = new HashMap<String, Integer>();
	}
	
	public Bomb addDamage(String damage, int amount)
	{
		damages.put(damage, amount);
		return this;
	}
	
	public boolean causesDamage(String damage)
	{
		return damages.containsKey(damage);
	}
	
	public int getDamage(String damage)
	{
		return damages.get(damage);
	}

	@Override
	public void update(double dt) 
	{
		countdown -= dt;
		
		if(countdown < 0)
			explode();
	}
	
	public void explode()
	{
		System.out.println("Explosion!");
		
		FTLGame.instance().getShip(target).getRoom(room).damage(this);
		
		kill();
	}

	@Override
	public boolean isDead() 
	{
		return dead;
	}

	public void kill() 
	{
		dead = true;
	}
	
	public String getSource()
	{
		return source;
	}
	
}

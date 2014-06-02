package com.rebelkeithy.ftl.map.events;

import java.util.Random;

import com.google.common.eventbus.Subscribe;
import com.rebelkeithy.ftl.FTLGame;
import com.rebelkeithy.ftl.event.GetSystemPowerEvent;
import com.rebelkeithy.ftl.event.ShipDestroyedEvent;
import com.rebelkeithy.ftl.ship.Resource;
import com.rebelkeithy.ftl.ship.Ship;
import com.rebelkeithy.ftl.ship.ShipRegistry;

public class BattleEvent extends DialogEvent
{
	public Ship enemy;
	
	public EventState state2;
	
	public BattleEvent(FTLGame game, int sector, int star)
	{
		super(game, sector, star);
		
		enemy = ShipRegistry.build("The Kestrel", "Enemy");
		enemy.setName("Enemy");
		enemy.EVENT_BUS.register(this);
		
		EventState state1 = new EventState(0);
		state1.dialog = "Once you arrive, your screen lights up with warnings. A nearby pirate seems to have advanced hacking tools and they have tried to shut down our engines. Your crew manages to keep them operational and you move in to attack.";
		
		EventState stateEnd = new EventState(1);
		stateEnd.endingState = true;
		
		state2 = new EventState(2);
		state2.dialog = "With the pirate ship destroyed, your ship's system is restored to full functionality. You salvage what you can from the debris.";
		
		state1.addAction("Continue...", stateEnd, 1);	
		state2.addAction("Continue...", stateEnd, 1);
		
		state = state1;
		
		game.addShip(enemy, sector, star);
	}
	
	@Override
	public void update(double dt) 
	{
		if(!state.endingState)
			dt = 0;
		
		super.update(dt);
		
		enemy.update(dt);
	}
	
	@Override
	public void activate()
	{	
		super.activate();
		
		if(state.id == 2)
		{
			// TODO: Rewards should be made more generic, make a separate rewards class for reward distribution
			Random rand = new Random();
			int scrap = rand.nextInt(10);
			int fuel = rand.nextInt(3);
			int missiles = rand.nextInt(3);
			
			Resource.getResource("scrap").addResource(ship, scrap);
			Resource.getResource("fuel").addResource(ship, fuel);
			Resource.getResource("missiles").addResource(ship, missiles);
		}
	}
	
	public void enter(Ship ship)
	{
		super.enter(ship);
		ship.EVENT_BUS.register(this);
		
	}

	public void leave(Ship ship) 
	{
		super.leave(ship);
		ship.EVENT_BUS.unregister(this);
	}
	
	@Subscribe
	public void getEnginePowerEvent(GetSystemPowerEvent event)
	{
		if(event.ship == ship && event.system.equals("engines"))
		{
			//ShipSystem system = ship.getSystem("engines");
			// TODO: fix this, need to stop players from being adding more power that will be ignored. Should be able to lock power bars on systems
			//if(event.power > 1)
			//	event.power = 1;
		}
	}
	
	@Subscribe
	public void shipDestroyedEvent(ShipDestroyedEvent event)
	{
		if(event.ship == enemy)
		{
			setState(state2);
		}
	}
}

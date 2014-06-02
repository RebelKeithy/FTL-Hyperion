package com.rebelkeithy.ftl;

import com.google.common.eventbus.EventBus;
import com.rebelkeithy.ftl.ship.Ship;

public class Main 
{
	public static FTLGame gameClient;
	public static EventBus GLOBAL_BUS = new EventBus();
	
	public static Ship player;
	
	public static boolean stop = false;
	
	/*
	public static void main(String[] args)
	{
		
		
		gameClient = new Game();
		
		gameClient.init();
		
		gameClient.generate();
		
		//gameClient.connectToServer();
		
		player = ShipRegistry.build("The Kestrel", "Player");
		player.setName("Player");
		player.setPlayer(true);
		
		gameClient.addShip(player, 0, 0);
		
		AbstractAugmentation reloader = AugmentationRegistry.getAugmentation("AutomatedReloader");
		reloader.install(player.getName());
		reloader.install(player.getName());
		reloader.install(player.getName());		
		
		WeaponSystem weapons1 = (WeaponSystem)player.systems.get("weapons");
		weapons1.addPower(3);
		weapons1.powerOnWeapon(0);
		weapons1.powerOnWeapon(1);
		player.getSystem("shields").addPower(2);
		player.getSystem("health").addPower(1);
		player.getSystem("engines").addPower(1);
		player.getSystem("oxygen").addPower(1);
		
		//player.addCrew(crew1);
		//crew1.setPosition(player.getRoom("room13"), 0, 0);
		
		Thread thread = new Thread() {
			
			public void run()
			{
				while(!Main.stop)
				{
					Clock.instance().update(1/20.0);
					gameClient.update(1/20.0);
					
					try {
						sleep(1000/20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		
		thread.start();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String input = "";
		while(!input.equals("quit"))
		{
			try 
			{
				input = br.readLine();
				//Packet packet = new Packet("Testing Packets");
				//gameClient.sendPacketToServer(packet);
				
				if(input.startsWith("Power"))
				{
					String action[] = input.split(" ");
					gameClient.action(action[0], player.getName(), action[1], Integer.parseInt(action[2]));
				}
				if(input.startsWith("Target"))
				{
					String action[] = input.split(" ");
					gameClient.action(action[0], player.getName(), Integer.parseInt(action[1]), action[2], action[3]);
				}
				if(input.startsWith("Move"))
				{
					String action[] = input.split(" ");
					gameClient.action(action[0], player.getName(), action[1], action[2]);
				}
				if(input.startsWith("ExamineShip"))
				{
					String action[] = input.split(" ");
					gameClient.action(action[0], player.getName());
				}
				if(input.startsWith("ActivateWeapon"))
				{
					String action[] = input.split(" ");
					gameClient.action(action[0], player.getName(), Integer.parseInt(action[1]));
				}
				if(input.startsWith("DeactivateWeapon"))
				{
					String action[] = input.split(" ");
					gameClient.action(action[0], player.getName(), Integer.parseInt(action[1]));
				}
				if(input.startsWith("Event"))
				{
					String action[] = input.split(" ");
					gameClient.action(action[0], player.getName(), action[1]);
				}
				if(input.startsWith("Jump"))
				{
					String action[] = input.split(" ");
					gameClient.action(action[0], player.getName(), Integer.parseInt(action[1]));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		//thread.stop();
		stop = true;
	}
	*/
	
	public void startServer()
	{
		final FTLGame server = new FTLGame();
		server.setupServer();

		Thread thread = new Thread() {
			
			public void run()
			{
				while(!Main.stop)
				{
					Clock.instance().update(1/20.0);
					server.update(1/20.0);
					
					try {
						sleep(1000/20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		
		thread.start();
	}
}

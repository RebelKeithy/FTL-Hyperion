package com.rebelkeithy.ftl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;
import com.rebelkeithy.ftl.actions.Action;
import com.rebelkeithy.ftl.actions.ActivateWeaponAction;
import com.rebelkeithy.ftl.actions.AddResourceAction;
import com.rebelkeithy.ftl.actions.AddSystemAction;
import com.rebelkeithy.ftl.actions.DeactivateWeaponAction;
import com.rebelkeithy.ftl.actions.EventAction;
import com.rebelkeithy.ftl.actions.ExamineAction;
import com.rebelkeithy.ftl.actions.JumpAction;
import com.rebelkeithy.ftl.actions.MoveAction;
import com.rebelkeithy.ftl.actions.PowerAction;
import com.rebelkeithy.ftl.actions.TargetAction;
import com.rebelkeithy.ftl.augmentations.AbstractAugmentation;
import com.rebelkeithy.ftl.augmentations.AugmentationRegistry;
import com.rebelkeithy.ftl.augmentations.AutomatedReloader;
import com.rebelkeithy.ftl.augmentations.ShieldChargeBooster;
import com.rebelkeithy.ftl.crew.CrewEffects;
import com.rebelkeithy.ftl.crew.CrewRegistry;
import com.rebelkeithy.ftl.crew.Skill;
import com.rebelkeithy.ftl.map.Sector;
import com.rebelkeithy.ftl.map.Star;
import com.rebelkeithy.ftl.map.events.BattleEvent;
import com.rebelkeithy.ftl.map.events.IntelligentLifeformDialogEvent;
import com.rebelkeithy.ftl.map.events.MapEvent;
import com.rebelkeithy.ftl.packets.FTLServer;
import com.rebelkeithy.ftl.packets.Network;
import com.rebelkeithy.ftl.packets.Packet;
import com.rebelkeithy.ftl.packets.PacketListener;
import com.rebelkeithy.ftl.projectile.Shot;
import com.rebelkeithy.ftl.ship.Resource;
import com.rebelkeithy.ftl.ship.Ship;
import com.rebelkeithy.ftl.ship.ShipLayoutRegistry;
import com.rebelkeithy.ftl.ship.ShipRegistry;
import com.rebelkeithy.ftl.systems.CommandSystem;
import com.rebelkeithy.ftl.systems.DoorsSystem;
import com.rebelkeithy.ftl.systems.EngineSystem;
import com.rebelkeithy.ftl.systems.HealthSystem;
import com.rebelkeithy.ftl.systems.OxygenSystem;
import com.rebelkeithy.ftl.systems.ReactorSystem;
import com.rebelkeithy.ftl.systems.SensorsSystem;
import com.rebelkeithy.ftl.systems.ShieldSystem;
import com.rebelkeithy.ftl.systems.SystemRegistry;
import com.rebelkeithy.ftl.systems.WeaponSystem;
import com.rebelkeithy.ftl.weapons.ProjectileWeaponBuilder;

public class FTLGame 
{
	private static FTLGame instance;
	
	Map<Ship, MapEvent> events;
	Map<Integer, Sector> sectors;
	List<Ship> ships;
	List<Shot> shots;
	
	// Client Side only
	Ship player;
	
	Map<String, Action> actions;
	
	private Server server;
	private Client client;
	
	private CrewEffects crewEffects;
	
	public FTLGame()
	{
		events = new HashMap<Ship, MapEvent>();
		sectors = new HashMap<Integer, Sector>();
		ships = new ArrayList<Ship>();
		shots = new ArrayList<Shot>();
		
		actions = new HashMap<String, Action>();
		
		instance = this;
	}
	
	public void setupServer()
	{
		server = new FTLServer();
		
		Network.register(server);
		
		server.addListener(new PacketListener());
		try {
			server.bind(Network.port);
			server.start();
		} catch (IOException e) {
			System.out.println("Unable to connect to server");
			e.printStackTrace();
		}
	}
	
	public void connectToServer()
	{
		client = new Client();
		client.start();
		
		Network.register(client);
		
		client.addListener(new PacketListener());
		try {
			client.connect(5000, "localhost", Network.port);
		} catch (IOException e) {
			System.out.println("Could not connect to server");
			e.printStackTrace();
		}
	}

	public void init() 
	{
		registerActions();
		registerResources();
		
		File file = new File("data/ships/layouts/kestrel.json");
		ShipLayoutRegistry.registerLayout(new File("data/ships/layouts/kestrel.json"));
		ShipLayoutRegistry.registerLayout(new File("data/ships/layouts/kestrelB.json"));
		ShipLayoutRegistry.registerLayout(new File("data/ships/layouts/engiA.json"));
		ShipLayoutRegistry.registerLayout(new File("data/ships/layouts/engiB.json"));
		ShipLayoutRegistry.registerLayout(new File("data/ships/layouts/federationA.json"));
		ShipLayoutRegistry.registerLayout(new File("data/ships/layouts/zoltanA.json"));
		
		registerRaces();
		
		ShipRegistry.registerShip(new File("data/ships/ship/the_kestrel.json"));
		ShipRegistry.registerShip(new File("data/ships/ship/red_tail.json"));
		ShipRegistry.registerShip(new File("data/ships/ship/the_torus.json"));
		ShipRegistry.registerShip(new File("data/ships/ship/the_vortex.json"));
		ShipRegistry.registerShip(new File("data/ships/ship/the_osprey.json"));
		ShipRegistry.registerShip(new File("data/ships/ship/the_adjudicator.json"));

		registerSkills();
		registerSystems();
		registerWeapons();
		registerAugmentations();

		new IonHandler();
		crewEffects = new CrewEffects();
		
		ShipLayoutRegistry.loadTextures();
		CrewRegistry.loadTextures();
		SystemRegistry.loadTextures();
	}
	
	public void load()
	{
		
	}
	
	public void save()
	{
		
	}
	
	public void registerResources()
	{
		Resource scrap = new Resource("scrap", "statusUI/top_scrap", "Current scrap total", 3);
		Resource.registerResource("scrap", scrap);
		
		Resource fuel = new Resource("fuel", "statusUI/top_fuel_on", "Amount of fuel. Each jump\nconsumes one fuel.", 3);
		Resource.registerResource("fuel", fuel);

		Resource missiles = new Resource("missiles", "statusUI/top_missiles_on", "Number of Missiles. Some\nweapons consume one Missile when fired", 0);
		Resource.registerResource("missiles", missiles);

		Resource drones = new Resource("drones", "statusUI/top_drones_on", "Number of Drone Parts.\nUsing a Drone consumes one Drone Part.", 0);
		Resource.registerResource("drones", drones);
	}
	
	public void registerSkills()
	{
		Skill piloting = new Skill("Piloting", "people/skill_pilot_white", "Evasion +5", 13);
		piloting.addLevel(13);

		Skill engines = new Skill("Engines", "people/skill_engines_white", "Evasion +5", 13);
		engines.addLevel(13);

		Skill shields = new Skill("Shields", "people/skill_shields_white", "10% faster recharge", 13);
		shields.addLevel(13);

		Skill weapons = new Skill("Weapons", "people/skill_weapons_white", "10% faster charge", 13);
		weapons.addLevel(13);

		Skill repair = new Skill("Repair", "people/skill_repair_white", "Increases repair speed", 13);
		repair.addLevel(13);

		Skill combat = new Skill("Combat", "people/skill_combat_white", "Increases crew damage", 13);
		combat.addLevel(13);
	}
	
	public static void registerActions()
	{
		new AddResourceAction("AddResource");
		new AddSystemAction("AddSystem");
		new TargetAction("Target");
		new MoveAction("Move");
		new PowerAction("Power");
		new JumpAction("Jump");
		new ActivateWeaponAction("ActivateWeapon");
		new DeactivateWeaponAction("DeactivateWeapon");
		
		new ExamineAction("ExamineShip");
		new EventAction("Event");
	}
	
	public static void registerSystems()
	{
		SystemRegistry.register("reactor", ReactorSystem.class);
		SystemRegistry.register("pilot", CommandSystem.class);
		SystemRegistry.register("engines", EngineSystem.class);
		SystemRegistry.register("shields", ShieldSystem.class);
		SystemRegistry.register("weapons", WeaponSystem.class);
		SystemRegistry.register("oxygen", OxygenSystem.class);
		SystemRegistry.register("sensors", SensorsSystem.class);
		SystemRegistry.register("doors", DoorsSystem.class);
		SystemRegistry.register("medbay", HealthSystem.class);
	}
	
	public static void registerWeapons()
	{
		ProjectileWeaponBuilder.registerWeapons(new File("data/weapons/weapons.json"));
	}	
	
	public static void registerAugmentations()
	{
		AbstractAugmentation aug = new AutomatedReloader("AutomatedReloader");
		AugmentationRegistry.register("AutomatedReloader", aug);
		
		aug = new ShieldChargeBooster("ShieldChargeBooster");
		AugmentationRegistry.register("ShieldChargeBooster", aug);
	}
	
	public void registerRaces()
	{
		CrewRegistry.registerRaces(new File("data/races/races.json"));
	}
	
	public boolean isRemote()
	{
		return server != null;
	}
	
	public static FTLGame instance()
	{
		return instance;
	}
	
	public void generate()
	{
		Sector sector1 = new Sector();
		sectors.put(sector1.getSectorID(), sector1);
		
		Star star1 = new Star();
		sector1.addStar(star1);
		
		Star star2 = new Star();
		sector1.addStar(star2);

		star1.addNeighbor(star2.getStarID());
		
		MapEvent event1 = new BattleEvent(this, sector1.getSectorID(), star1.getStarID());
		star1.setEvent(event1);
		
		MapEvent event2 = new IntelligentLifeformDialogEvent(this, sector1.getSectorID(), star2.getStarID());
		star2.setEvent(event2);
		
	}
	
	public void addShip(Ship ship, int sector, int star)
	{
		ships.add(ship);
		ship.setGame(this);
		
		ship.setPosition(sector, star);
		
		if(ship.isPlayer())
		{
			MapEvent event = sectors.get(sector).getStar(star).getEvent();
			events.put(ship, event);
			event.enter(ship);
			event.activate();
		}
	}
	
	public Ship getShip(String name)
	{
		for(Ship ship : ships)
		{
			if(ship.getName().equals(name))
			{
				return ship;
			}
		}
		
		return null;
	}
	
	public void shipJump(Ship ship, int starID)
	{
		Sector sector = sectors.get(ship.getSector());
		Star star = sector.getStar(ship.getStar());
		
		if(ship.isFTLCharged() && star.isConnectedTo(starID))
		{
			ship.setPosition(ship.getSector(), starID);
			MapEvent event = sector.getStar(starID).getEvent();
			
			if(ship.isPlayer())
			{
				Clock.log("jump successful " + event);
				events.put(ship, event);
				event.enter(ship);
				event.activate();
			}
		}
		else
		{
			if(!ship.isFTLCharged())
			{
				Clock.log("FTL drive not yet charged " + ship.getFTLCharge());
			}
		}
	}
	
	public void addShot(Shot shot)
	{
		shots.add(shot);
	}
	
	public void update(double dt)
	{
		for(MapEvent event : events.values())
		{
			event.update(dt);
		}
		
		for(int i = 0; i < shots.size(); i++)
		{
			shots.get(i).update(dt);
			if(shots.get(i).isDead())
			{
				shots.remove(i);
				i--;
			}
		}
	}

	public Map<Integer, Sector> getMap() 
	{
		return sectors;
	}
	
	public void action(String action, Object... params)
	{
		Action handler = actions.get(action);
		if(handler != null)
		{
			handler.preform(action, params);
		}
	}

	public void registerAction(String action, Action handler) 
	{
		actions.put(action, handler);
	}

	public MapEvent getEvent(Ship ship) 
	{
		return events.get(ship);
	}

	public void sendPacketToServer(Packet packet) 
	{
		client.sendTCP(packet);
	}

	public Ship getPlayer() 
	{
		return player;
	}

	public void setPlayer(Ship player) 
	{
		this.player = player;
	}
}

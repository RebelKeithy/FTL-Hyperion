package com.rebelkeithy.ftl.map.events;

import java.util.List;
import java.util.Random;

import com.rebelkeithy.ftl.FTLGame;
import com.rebelkeithy.ftl.crew.Crew;
import com.rebelkeithy.ftl.crew.CrewRegistry;

public class IntelligentLifeformDialogEvent extends DialogEvent
{	
	public IntelligentLifeformDialogEvent(FTLGame game, int sector, int star)
	{
		super(game, sector, star);
		
		EventState state1 = new EventState(1);
		state1.dialog = "Scanners are showing intelligent life forms on a nearby planet. No match for them can be found in the database.";
		
		EventState state2 = new EventState(2);
		state2.dialog = "You land a small shuttle in an enormous field, whose only occupants are small, brightly colored, six-legged, horse-like animals. Could they be what your scans picked up?";
		
		EventState state3 = new EventState(3);
		state3.dialog = "None of your attempts to communicate seem to work: they just stare at you silently. As you prepare to leave, one of the creatures canters forward and forcefully nudges you away from the ship. He seems to want you to follow him. Eventually, the creatures guide you to an old Engi ship's crash site. Inside you are able to find and reactivate an Engi!";
		
		EventState state4 = new EventState(4);
		state4.dialog = "You try to communicate in every possible way you can but they just stand there, silently judging you with their large, expressionless eyes. You prepare to leave.";
		
		EventState state5 = new EventState(5);
		state5.dialog = "The seemingly docile creatures quickly turn violent when you reveal your hostile intentions. Their well-organized stampede forces you to draw weapons and make a rushed and shambolic retreat to the shuttle.";

		EventState state6 = new EventState(6);
		state6.dialog = "The seemingly docile creatures quickly turn violent when you show your hostile intentions. They stampede with terrifying force, trampling one of your crew before you have time to react. You fight your way back to the shuttle and prepare to jump.";
				
		EventState state7 = new EventState(7);
		state7.dialog = "This isn't the time for exobiology. You head back to the ship.";
		
		EventState state8 = new EventState(8);
		state8.dialog = "You ignore the readings and prepare to move on.";
		
		EventState stateEnd = new EventState(9);
		stateEnd.endingState = true;
		
		state1.addAction("Investigate", state2, 1);
		state2.addAction("Try to communicate peacefully", state3, 0.5);
		state2.addAction("Try to communicate peacefully", state4, 0.5);
		state2.addAction("Bring some of the creatures on board to sell", state5, 0.5);
		state2.addAction("Bring some of the creatures on board to sell", state6, 0.5);
		state2.addAction("Leave", state7, 1);
		state1.addAction("Ignore it.", state8, 1);
		
		state3.addAction("Continue...", stateEnd, 1);
		state4.addAction("Continue...", stateEnd, 1);
		state5.addAction("Continue...", stateEnd, 1);
		state6.addAction("Continue...", stateEnd, 1);
		state7.addAction("Continue...", stateEnd, 1);
		state8.addAction("Continue...", stateEnd, 1);
		
		state = state1;
	}
	
	protected void setState(EventState state)
	{
		super.setState(state);
	
		if(state.id == 3)
		{
			Crew crew = CrewRegistry.build("Engi", "Varmint");
			crew.setHomeShip(ship.getName());
			ship.addCrew(crew);
			crew.setPosition(ship.getRoom("Room1"), 0, 0);
		}
		if(state.id == 6)
		{
			List<Crew> crew = ship.getCrew();
			Random rand = new Random();
			int c = rand.nextInt(crew.size());
			crew.get(c).kill("Event");
		}
	}
}

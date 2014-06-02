package com.rebelkeithy.ftl.crew;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import com.rebelkeithy.ftl.Clock;
import com.rebelkeithy.ftl.properties.Properties;
import com.rebelkeithy.ftl.ship.Direction;
import com.rebelkeithy.ftl.ship.Door;
import com.rebelkeithy.ftl.ship.Room;
import com.rebelkeithy.ftl.ship.Tile;

public class CrewPathfinder 
{
	private double crewX;
	private double crewY;
	
	private Tile targetTile;
	private double targetX;
	private double targetY;
	
	//private Room room;
	private int roomX;
	private int roomY;

	private Stack<Door> path;

	// TODO: state implementation is too rigid, trying to change paths in the middle of a path will cause things to quickly become too
	//  complex. Instead this should build a path of waypoints, with waypoints on either side of each door. It should check for the 
	//  crew member to reach the edge of a room and then try to unlock a door, and change the crew member's room when it detects the crew
	//  member moving over the edge of a room
	public final int IDLE = 0;
	public final int MOVING_TO_DOOR = 1;  // crew is moving to the tile in front of the door
	public final int MOVING_IN_DOOR = 2;  // crew is moving up to the door, but is has not opened yet
	public final int MOVING_OUT_DOOR = 3; // the door has opened and crew is moving to the tile on the other side
	public final int ATTACKING_DOOR = 4;  // door has not opened and the crew is trying to break it down
	public final int MOVING_TO_POSITION = 5; // crew has reached the final room and is moving to the correct position
	public final int MOVING_TO_START = 6;  // Used to get crew in position if he was already moving and the path changed
	
	public int state;
	
	public CrewPathfinder()
	{
		state = IDLE;
		path = new Stack<Door>();
	}
	
	// This only updates internal variables, it does not change crew variables.
	public void update(double dt, Crew crew)
	{
		crewX = crew.getX();
		crewY = crew.getY();
		
		if(state == IDLE)
			return;
		
		if(state == MOVING_TO_DOOR)
		{
			moveToTarget(crew.getSpeed() * dt);
		}
		if(state == MOVING_IN_DOOR)
		{
			moveToTarget(crew.getSpeed() * dt);
		}
		if(state == MOVING_OUT_DOOR)
		{
			moveToTarget(crew.getSpeed() * dt);		
			
			Door door = path.peek();
			if(crew.getRoom() == door.room1 && distSqr() < 0.25)
			{
				Clock.log(crew.getName() + " now in room " + door.room2.getName());
				door.room1.removeCrew(crew);
				door.room2.addCrew(crew);
				crew.setRoom(door.room2);
			}
		}
		if(state == MOVING_TO_POSITION)
		{
			moveToTarget(crew.getSpeed() * dt);	
		}
		
		if(crewX == targetX && crewY == targetY)
			nextState(crew);
	}
	
	public double distSqr()
	{
		return (crewX - targetX) * (crewX - targetX) + (crewY - targetY) * (crewY - targetY);
	}
	
	private void moveToTarget(double speed)
	{
		if(Math.abs(crewX - targetX) < 0.1)
			crewX = targetX;
		else if(crewX < targetX)
			crewX += speed;
		else if(crewX > targetX)
			crewX -= speed;

		if(Math.abs(crewY - targetY) < 0.1)
			crewY = targetY;
		else if(crewY < targetY)
			crewY += speed;
		else if(crewY > targetY)
			crewY -= speed;
	}
	
	private void nextState(Crew crew)
	{
		if(state == MOVING_TO_DOOR)
		{
			state = MOVING_IN_DOOR;
			Door door = path.peek();
			setTargetAtDoor(door);
		}
		else if(state == MOVING_IN_DOOR)
		{
			Door door = path.peek();
			if(door.unlock(crew))
			{
				door.addUser(crew);
				state = MOVING_OUT_DOOR;
				setTargetBehindDoor(door);
			}
			else
			{
				state = ATTACKING_DOOR;
			}
		}
		else if(state == MOVING_OUT_DOOR)
		{
			Door door = path.pop();  // Move on to the next door
			door.removeUser(crew);  // Let the door know it can close
			
			if(path.isEmpty())
			{
				state = MOVING_TO_POSITION;
				targetX = roomX;
				targetY = roomY;
			}
			else
			{
				state = MOVING_TO_DOOR;
				setTargetInFrontOfDoor(path.peek());
			}
		}
		else if(state == ATTACKING_DOOR)
		{
			Door door = path.peek();
			door.attack(crew);
			
			if(door.unlock(crew))
			{
				door.addUser(crew);
				state = MOVING_OUT_DOOR;
				setTargetBehindDoor(door);
				
			}
		}
		else if(state == MOVING_TO_POSITION)
		{
			state = IDLE;
		}
	}
	
	private void setTargetAtDoor(Door door)
	{
		setTargetRelativeToDoor(door, -0.25);
	}
	
	private void setTargetBehindDoor(Door door)
	{
		setTargetRelativeToDoor(door, 0.5);
	}
	
	private void setTargetInFrontOfDoor(Door door)
	{
		setTargetRelativeToDoor(door, -0.5);
	}
	
	private void setTargetRelativeToDoor(Door door, double offset)
	{
		if(door.direction == Direction.UP)
		{
			targetX = door.getX();
			targetY = (door.getY() + offset - 0.5);
		}
		if(door.direction == Direction.DOWN)
		{
			targetX = door.getX();
			targetY = (door.getY() - offset - 0.5);
		}
		if(door.direction == Direction.LEFT)
		{
			targetX = (door.getX() - offset - 0.5);
			targetY = door.getY();
		}
		if(door.direction == Direction.RIGHT)
		{
			targetX = (door.getX() + offset - 0.5);
			targetY = door.getY();
		}
	}
	
	public Tile setPath(Crew crew, Room target)
	{
		Tile targetTile = null;
		for(int y = target.getHeight()-1; y >= 0 ; y--)
		{
			for(int x = 0; x < target.getWidth(); x++)
			{
				Properties p = target.getTile(x, y).getProperties();
				if(!p.containsValue("crew") && !p.containsValue("blocked"))
				{
					targetTile = target.getTile(x, y);
					break;
				}
				else if(p.containsValue("crew"))
				{
					if(p.getString("crew").equals(crew.getName()))
					{
						return null;
					}
				}
			}
			if(targetTile != null)
			{
				break;
			}
		}
		
		if(targetTile == null)
		{
			return null;
		}
		
		return setPath(crew, target, targetTile.getX(), targetTile.getY());
	}
	
	public Tile setPath(Crew crew, Room target, int x, int y)
	{
		if(state != IDLE)
		{
			Clock.log("Wait for crew member to finish current path");
			//return null;
		}
		
		//room = target;
		roomX = target.getX() + x;
		roomY = target.getY() + y;

		path = new Stack<Door>();
		
		HashSet<Door> searched = new HashSet<Door>();
		Queue<Node> front = new LinkedList<Node>();
		
		path = bfsSearthStart(target, crew.getRoom(), searched, front);
		
		Clock.log("found path " + path);
		
		if(path != null)
		{			
			if(targetTile != null)
			{
				targetTile.getProperties().removeProperty("crew");
			}
			targetTile = target.getTile(x, y);
			target.getTile(x, y).getProperties().setString("crew", crew.getName());
			if(state != IDLE)
			{
				
			}
			
			if(!path.isEmpty())
			{
				state = MOVING_TO_DOOR;
				setTargetInFrontOfDoor(path.peek());
			}
			else
			{
				state = MOVING_TO_POSITION;
				targetX = roomX;
				targetY = roomY;
			}
		}
		else
		{
			return null;
		}
		
		return target.getTile(x, y);
	}

	public void cancel(Crew crew) 
	{
		targetTile.getProperties().setString("crew", crew.getName());
	}
	
	private class Node
	{
		Node parent;
		Door door;
		
		public Node(Node parent, Door door)
		{
			this.parent = parent;
			this.door = door;
		}
	}
	
	private Stack<Door> bfsSearthStart(Room target, Room start, Set<Door> visited, Queue<Node> front)
	{
		if(target == start)
		{
			return new Stack<Door>();
		}
		
		Stack<Door> best = null;
		int bestLength = Integer.MAX_VALUE;
		for(Door door : start.getDoors())
		{
			if(door.room2 == null)
				continue;
			
			visited.clear();
			front.clear();
			Node node = bfsSearch(target, new Node(null, door), visited, front);
			if(node != null)
			{
				Stack<Door> path = new Stack<Door>();
				while(node != null)
				{
					path.push(node.door);
					node = node.parent;
				}
				float length = path.size();
				if(best == null || length < bestLength)
				{
					best = path;
					bestLength = (int) length;
				}
			}
		}
		
		return best;
	}
	
	private Node bfsSearch(Room target, Node current, Set<Door> visited, Queue<Node> front)
	{
		if(visited.contains(current.door))
		{
			return null;
		}
		else if(current.door.room2 == target)
		{
			return current;
		}
		else
		{
			visited.add(current.door);
			if(current.door.room2 != null)
			{
				for(Door door : current.door.room2.getDoors())
				{
					Node node = new Node(current, door);
					front.add(node);
				}
			}
			while(!front.isEmpty())
			{
				Node node = front.poll();
				Node path = bfsSearch(target, node, visited, front);
				if(path != null)
				{
					return path;
				}
			}
		}
		
		return null;
	}
	
	public double getX()
	{
		return crewX;
	}
	
	public double getY()
	{
		return crewY;
	}

	public int getState() 
	{
		return state;
	}

	public int targetX() 
	{
		return roomX;
	}

	public int targetY() 
	{
		return roomY;
	}
}

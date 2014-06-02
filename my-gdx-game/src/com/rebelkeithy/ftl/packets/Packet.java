package com.rebelkeithy.ftl.packets;

public class Packet 
{
	private String action;
	
	public Packet()
	{
		
	}
	
	public Packet(String action)
	{
		this.action = action;
	}
	
	public String getAction()
	{
		return action;
	}
}

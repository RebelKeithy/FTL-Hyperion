package com.rebelkeithy.ftl.packets;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class PacketListener extends Listener
{
	public void received(Connection c, Object o)
	{
		//FTLConnection connection = (FTLConnection)c;

		if(o instanceof Packet)
		{
			Packet packet = (Packet)o;
			
			System.out.println("packet " + packet.getAction());
		}
	}
	
	public void disconnectied(Connection c)
	{
		FTLConnection connection = (FTLConnection)c;
		
		if(connection.name != null)
		{
			// do something?
		}
	}
}

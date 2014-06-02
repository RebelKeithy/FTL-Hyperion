package com.rebelkeithy.ftl.packets;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class Network 
{
	public static final int port = 54555;
	
	public static void register(EndPoint endPoint)
	{
		Kryo kryo = endPoint.getKryo();
		kryo.register(Packet.class);
	}
	
	
}

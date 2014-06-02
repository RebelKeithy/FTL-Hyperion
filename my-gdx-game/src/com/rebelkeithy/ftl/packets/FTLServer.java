package com.rebelkeithy.ftl.packets;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

public class FTLServer extends Server
{
	protected Connection newConnection()
	{
		return new FTLConnection();
	}
}

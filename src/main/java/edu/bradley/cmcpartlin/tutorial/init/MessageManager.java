package edu.bradley.cmcpartlin.tutorial.init;

import edu.bradley.cmcpartlin.tutorial.init.messages.BlockHitMessage;
import edu.bradley.cmcpartlin.tutorial.init.messages.ExplosionMessage;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class MessageManager {
	
	private static int packetID = 0;
	
	public static SimpleNetworkWrapper INSTANCE = null;
	
	public MessageManager() {
		
	}
	
	public static int nextID() {
		return packetID++;
	}
	
	public static void registerMessages(String channelName) {
		INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);
		registerMessages();
	}
	
	public static void registerMessages() {
		INSTANCE.registerMessage (BlockHitMessage.Handler.class, BlockHitMessage.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage (ExplosionMessage.Handler.class, ExplosionMessage.class, nextID(), Side.SERVER);
	}

}

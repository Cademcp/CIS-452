package edu.bradley.cmcpartlin.tutorial;

import com.example.examplemod.Reference;

import TileEntities.TileEntityDragonTable;
import edu.bradley.cmcpartlin.tutorial.gui.GUIHandler;
import edu.bradley.cmcpartlin.tutorial.init.MessageManager;
import edu.bradley.cmcpartlin.tutorial.init.ModCapabilities;
import edu.bradley.cmcpartlin.tutorial.proxy.IProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION, acceptedMinecraftVersions = Reference.ACCEPTED_VERSIONS)
public class Tutorial {
	
	@Instance
	public static Tutorial instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static IProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ModCapabilities.registerCapabilities();
		MessageManager.registerMessages("tutorial_channel");
		GameRegistry.registerTileEntity(TileEntityDragonTable.class, "dragon_table");
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.registerKeyBindings();
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GUIHandler());
		
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		System.out.println("postinit");
	}

}

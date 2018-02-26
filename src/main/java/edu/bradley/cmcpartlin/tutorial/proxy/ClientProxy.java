package edu.bradley.cmcpartlin.tutorial.proxy;

import com.example.examplemod.Reference;

import edu.bradley.cmcpartlin.tutorial.init.KeyBindings;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ClientProxy implements IProxy {

	@Override
	public void registerItemRenderer(Item item, int metadata, String id) {
		
		ModelResourceLocation location = new ModelResourceLocation(item.getRegistryName(), id);
		
		ModelLoader.setCustomModelResourceLocation(item, metadata, location);
		
	}

	@Override
	public void registerKeyBindings() {
		KeyBindings.init();
	}

	

}

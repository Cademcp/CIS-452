package edu.bradley.cmcpartlin.tutorial.init;

import org.lwjgl.input.Keyboard;
import com.example.examplemod.Reference;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class KeyBindings {
	public static KeyBinding eventKey;
	
	public static void init() {
		eventKey = new KeyBinding("key." + Reference.MOD_ID + ".event", Keyboard.KEY_G, "key.categories" + Reference.MOD_ID);
		ClientRegistry.registerKeyBinding(eventKey);
	}

}

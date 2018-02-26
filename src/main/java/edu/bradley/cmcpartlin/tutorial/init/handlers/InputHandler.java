package edu.bradley.cmcpartlin.tutorial.init.handlers;import com.example.examplemod.Reference;

import edu.bradley.cmcpartlin.tutorial.init.KeyBindings;
import edu.bradley.cmcpartlin.tutorial.init.MessageManager;
import edu.bradley.cmcpartlin.tutorial.init.messages.BlockHitMessage;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

@EventBusSubscriber(modid=Reference.MOD_ID)
public class InputHandler {

	@SubscribeEvent
	public static void onKeyInput(InputEvent.KeyInputEvent event) {
		if (KeyBindings.eventKey.isPressed()) {
			MessageManager.INSTANCE.sendToServer(new BlockHitMessage());
		}
	}
}

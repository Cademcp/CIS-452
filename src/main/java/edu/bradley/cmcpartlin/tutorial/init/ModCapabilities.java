package edu.bradley.cmcpartlin.tutorial.init;

import edu.bradley.cmcpartlin.tutorial.capabilities.CapabilityMaxHealth;
import edu.bradley.cmcpartlin.tutorial.capabilities.CapabilityMaxMovement;

public class ModCapabilities {

	public static void registerCapabilities() {
		CapabilityMaxHealth.register();
		CapabilityMaxMovement.register();
	}
}
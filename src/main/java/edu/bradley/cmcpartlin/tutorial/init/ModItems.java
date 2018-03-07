package edu.bradley.cmcpartlin.tutorial.init;

import java.util.ArrayList;
import java.util.List;

import com.example.examplemod.Reference;

import edu.bradley.cmcpartlin.tutorial.ItemBase;
import edu.bradley.cmcpartlin.tutorial.items.ItemCheese;
import edu.bradley.cmcpartlin.tutorial.items.ItemWand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ModItems {
	
	public static List<Item> ITEMS = new ArrayList<Item>();
	public static Item cheese = new ItemBase("cheese");
	public static Item dragon_blade = new ItemBase("dragon_blade");
	public static Item cracker = new ItemBase("cracker");
	public static Item cheese_cracker = new ItemBase("cheese_cracker");
	public static Item wand = new ItemWand("wand");
	
	
}

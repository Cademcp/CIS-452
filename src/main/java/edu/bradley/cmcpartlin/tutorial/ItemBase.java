package edu.bradley.cmcpartlin.tutorial;

import edu.bradley.cmcpartlin.tutorial.init.IHasModel;
import edu.bradley.cmcpartlin.tutorial.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item implements IHasModel {

	protected String name;
	
	public ItemBase(String name) {
		this.name = name;
		setUnlocalizedName(name);
		setRegistryName(name);
		
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
	}
	
	
	public void registerItemModel() {

	}


	@Override
	public void registerModels() {
		System.out.println("ItemBase requests registration of model" + name);
		Tutorial.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
	
}

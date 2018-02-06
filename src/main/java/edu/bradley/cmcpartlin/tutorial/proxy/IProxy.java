package edu.bradley.cmcpartlin.tutorial.proxy;

import net.minecraft.item.Item;

public interface IProxy {
	public void registerItemRenderer(Item itemBase, int i, String name);
}

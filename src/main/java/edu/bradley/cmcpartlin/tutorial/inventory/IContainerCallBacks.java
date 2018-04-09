package edu.bradley.cmcpartlin.tutorial.inventory;

import net.minecraft.entity.player.EntityPlayer;

public interface IContainerCallBacks {
	void onContainerOpened(EntityPlayer player);	//when container is opened by a player
	void onContainerClosed(EntityPlayer player);	//and closed by the player
	boolean isUsableByPlayer(EntityPlayer player); 	//is usable by the player
}

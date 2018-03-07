package edu.bradley.cmcpartlin.tutorial.items;

import edu.bradley.cmcpartlin.tutorial.ItemBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class ItemWand extends ItemBase {

	public ItemWand(String name) {
		super(name);
		
	}
	
	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target,
			EnumHand hand) {
		if (!playerIn.world.isRemote) {
			//on server side
			playerIn.sendStatusMessage(new TextComponentString(TextFormatting.GREEN + "Hit Target:" + target.getName()), false);
		}
		return false;
	}
	

}

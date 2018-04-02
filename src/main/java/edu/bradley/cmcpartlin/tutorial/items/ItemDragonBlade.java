package edu.bradley.cmcpartlin.tutorial.items;

import edu.bradley.cmcpartlin.tutorial.ItemBase;
import edu.bradley.cmcpartlin.tutorial.capabilities.CapabilityMaxMovement;
import edu.bradley.cmcpartlin.tutorial.capabilities.IMaxMovement;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class ItemDragonBlade extends ItemBase {

	public ItemDragonBlade(String name) {
		super(name);
	}
	
	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target,
			EnumHand hand) {
		
		if (!playerIn.world.isRemote) {
			final IMaxMovement maxMovement = CapabilityMaxMovement.getMaxMovement(target);
			
			if (maxMovement != null) {
				//use sneak to allow us to raise/lower value
				final float movementToAdd = playerIn.isSneaking() ? -0.2f : 0.2f;
				
				maxMovement.addBonusMaxMovement(movementToAdd);
			}
			
			final float adjusted = maxMovement.getBonusMaxMovement();
			System.out.println("MH   Current MM adjustment for " + target.getEntityId() + " is now " + adjusted);
			
			playerIn.sendStatusMessage(new TextComponentString(TextFormatting.DARK_PURPLE + "Current MM adjustment for : " + target.getEntityId() + " is now " + adjusted), false);
		}
		return true;
	}
		
		
}


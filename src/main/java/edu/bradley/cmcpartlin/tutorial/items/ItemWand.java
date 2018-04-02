package edu.bradley.cmcpartlin.tutorial.items;

import edu.bradley.cmcpartlin.tutorial.ItemBase;
import edu.bradley.cmcpartlin.tutorial.capabilities.CapabilityMaxHealth;
import edu.bradley.cmcpartlin.tutorial.capabilities.CapabilityMaxMovement;
import edu.bradley.cmcpartlin.tutorial.capabilities.IMaxHealth;
import edu.bradley.cmcpartlin.tutorial.capabilities.IMaxMovement;
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
		/*if (!playerIn.world.isRemote) {
			final IMaxHealth maxHealth = CapabilityMaxHealth.getMaxHealth(target);
			
			if (maxHealth != null) {
				//use sneak to allow us to raise/lower value
				final float healthToAdd = playerIn.isSneaking() ? -1.0f : 1.0f;
				
				maxHealth.addBonusMaxHealth(healthToAdd);
			}
	
			final float adjusted = maxHealth.getBonusMaxHealth();
			System.out.println("MH   Current MH adjustment for " + target.getEntityId() + " is now " + adjusted);
			
			playerIn.sendStatusMessage(new TextComponentString(TextFormatting.GREEN + "Current MH adjustment for : " + target.getEntityId() + " is now " + adjusted), false);
		}
		*/
		if (!playerIn.world.isRemote) {
			final IMaxMovement maxMovement = CapabilityMaxMovement.getMaxMovement(target);
			
			if (maxMovement != null) {
				//use sneak to allow us to raise/lower value
				final float movementToAdd = playerIn.isSneaking() ? -100.0f : 100.0f;
				
				maxMovement.addBonusMaxMovement(movementToAdd);
			}
			
			final float adjusted = maxMovement.getBonusMaxMovement();
			System.out.println("MH   Current MM adjustment for " + target.getEntityId() + " is now " + adjusted);
			
			playerIn.sendStatusMessage(new TextComponentString(TextFormatting.DARK_PURPLE + "Current MM adjustment for : " + target.getEntityId() + " is now " + adjusted), false);
		}
		return true;
	}


}

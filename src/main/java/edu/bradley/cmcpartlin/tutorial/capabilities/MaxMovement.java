package edu.bradley.cmcpartlin.tutorial.capabilities;

import java.util.Collections;
import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.network.play.server.SPacketEntityProperties;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class MaxMovement implements IMaxMovement {

	private final EntityLivingBase entity;
	private float bonusMaxMovement = 0.0f;
	String modifierName = "Bonus Max Movement";
	UUID modifierID = UUID.randomUUID();
	
	public MaxMovement(@Nullable final EntityLivingBase entity) {
		this.entity = entity;
	}
	
	@Override
	public float getBonusMaxMovement() {
		return bonusMaxMovement;
	}

	@Override
	public void setBonusMaxMovement(float maxMovement) {
		this.bonusMaxMovement = maxMovement;
		onBonusMaxMovementChanged();
		
	}

	@Override
	public void addBonusMaxMovement(float movementToAdd) {
		setBonusMaxMovement(getBonusMaxMovement() + movementToAdd);
		
	}

	@Override
	public void synchronize() {
		World world = entity.getEntityWorld();
		
		if (entity != null && !world.isRemote) {
			final IAttributeInstance entityMaxMovementAttribute = entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
			
			final SPacketEntityProperties packet = new SPacketEntityProperties(
					entity.getEntityId(), Collections.singleton(entityMaxMovementAttribute));
			WorldServer server = (WorldServer) entity.getEntityWorld();
			EntityTracker tracker = server.getEntityTracker();
			tracker.sendToTrackingAndSelf(entity, packet);
		}
		
	}
	
	protected void onBonusMaxMovementChanged() {
			
		if (entity == null) {
			return;
		}
		
		final IAttributeInstance entityMaxMovementAttribute = entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
		
		final float newAmount = getBonusMaxMovement();
		final float oldAmount;
		final AttributeModifier oldModifier;
		final int OPERATION_ADD = 0;
		
		final AttributeModifier newModifier = new AttributeModifier(modifierID, modifierName, getBonusMaxMovement(), OPERATION_ADD);
		
		oldModifier = entityMaxMovementAttribute.getModifier(modifierID);
		
		if (oldModifier != null) {
			entityMaxMovementAttribute.removeModifier(oldModifier);
			
			oldAmount = (float) oldModifier.getAmount();
		} else {
			oldAmount = 0.0f;
		}
		
		entityMaxMovementAttribute.applyModifier(newModifier);
		
		final float amountToMove = newAmount - oldAmount;
		if (amountToMove > 0) {
			entity.heal(amountToMove);
		}
	}

}

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

public class MaxHealth implements IMaxHealth {

	private final EntityLivingBase entity;
	private float bonusMaxHealth = 0.0f;
	String modifierName = "Bonus Max Health";
	UUID modifierID = UUID.randomUUID();
	
	public MaxHealth(@Nullable final EntityLivingBase entity) {
		this.entity = entity;
	}
	
	@Override
	public float getBonusMaxHealth() {
		return bonusMaxHealth;
	}

	@Override
	public void setBonusMaxHealth(float maxHealth) {
		this.bonusMaxHealth = maxHealth;
		onBonusMaxHealthChanged();

	}

	@Override
	public void addBonusMaxHealth(float healthToAdd) {
		setBonusMaxHealth(getBonusMaxHealth() + healthToAdd);

	}

	@Override
	public void synchronize() {
		
		World world = entity.getEntityWorld();
		
		//if we are on the server, update clients
		if (entity != null && !world.isRemote) {
			final IAttributeInstance entityMaxHealthAttribute = entity.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
			
			final SPacketEntityProperties packet = new SPacketEntityProperties(
					entity.getEntityId(), Collections.singleton(entityMaxHealthAttribute));
			WorldServer server = (WorldServer) entity.getEntityWorld();
			EntityTracker tracker = server.getEntityTracker();
			tracker.sendToTrackingAndSelf(entity, packet);
		}

	}
	
	protected void onBonusMaxHealthChanged() {
		
		if (entity == null) {
			return;
		}
		
		final IAttributeInstance entityMaxHealthAttribute = entity.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
		
		final float newAmount = getBonusMaxHealth();
		final float oldAmount;
		final AttributeModifier oldModifier;
		final int OPERATION_ADD = 0;
		
		final AttributeModifier newModifier = new AttributeModifier(modifierID, modifierName, getBonusMaxHealth(), OPERATION_ADD);
		
		oldModifier = entityMaxHealthAttribute.getModifier(modifierID);
		
		if (oldModifier != null) {
			entityMaxHealthAttribute.removeModifier(oldModifier);
			
			oldAmount = (float) oldModifier.getAmount();
		} else {
			oldAmount = 0.0f;
		}
		
		entityMaxHealthAttribute.applyModifier(newModifier);
		
		final float amountToHeal = newAmount - oldAmount;
		if (amountToHeal > 0) {
			entity.heal(amountToHeal);
		}
	}

}

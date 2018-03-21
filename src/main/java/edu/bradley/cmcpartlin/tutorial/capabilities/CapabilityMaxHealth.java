package edu.bradley.cmcpartlin.tutorial.capabilities;

import java.util.concurrent.Callable;

import javax.annotation.Nullable;

import com.example.examplemod.Reference;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;

public class CapabilityMaxHealth {
	@CapabilityInject(IMaxHealth.class)
	public static final Capability<IMaxHealth> MAX_HEALTH_CAPABILITY = null;
	public static final EnumFacing DEFAULT_FACING = null;
	public static final ResourceLocation ID = new ResourceLocation(Reference.MOD_ID, "MaxHealth");
	
	//Capability only used with living entities
	@Nullable
	public static IMaxHealth getMaxHealth(final EntityLivingBase entity) {
		if ((entity == null) || !entity.hasCapability(MAX_HEALTH_CAPABILITY, DEFAULT_FACING)) {
			return null;
		}
		return entity.getCapability(MAX_HEALTH_CAPABILITY, DEFAULT_FACING);
	}
	
	public static ICapabilityProvider createProvider(final IMaxHealth maxHealth) {
		return new CapabilityProvider<>(MAX_HEALTH_CAPABILITY, DEFAULT_FACING, maxHealth);
	}
	
	public static void register() {
		class Storage implements Capability.IStorage<IMaxHealth> {

			@Override
			public NBTBase writeNBT(Capability<IMaxHealth> capability, IMaxHealth instance, EnumFacing side) {
				return new NBTTagFloat(instance.getBonusMaxHealth());
			}

			@Override
			public void readNBT(Capability<IMaxHealth> capability, IMaxHealth instance, EnumFacing side, NBTBase nbt) {
				NBTTagFloat tagValue = (NBTTagFloat) nbt;
				instance.setBonusMaxHealth(tagValue.getFloat());
				
			}
			
		}
		
		class Factory implements Callable<IMaxHealth> {
			@Override
			public IMaxHealth call() throws Exception {
				return new MaxHealth(null);
			}
			
		}
		
		CapabilityManager.INSTANCE.register(IMaxHealth.class, new Storage(), new Factory());
	}
	
	@Mod.EventBusSubscriber(modid=Reference.MOD_ID)
	private static class EventHandler {
		
		//attach to all living entities when they are constructed
		@SubscribeEvent
		public static void attachCapabilities(final AttachCapabilitiesEvent<Entity> event) {
			if (event.getObject() instanceof EntityLivingBase) {
				EntityLivingBase entity = (EntityLivingBase) event.getObject();
				final MaxHealth maxHealth = new MaxHealth(entity);
				ICapabilityProvider provider = createProvider(maxHealth);
				event.addCapability(ID, provider);
			}
		}
		
		@SubscribeEvent
		public static void playerClone(final PlayerEvent.Clone event) {
			
			//copy capability after death (or return from the end)
			final IMaxHealth oldMaxHealth = getMaxHealth(event.getOriginal());
			final IMaxHealth newMaxHealth = getMaxHealth(event.getEntityPlayer());
			
			if (newMaxHealth != null && oldMaxHealth != null) {
				newMaxHealth.setBonusMaxHealth(oldMaxHealth.getBonusMaxHealth());
			}
		}
		
		@SubscribeEvent
		public static void playerChangeDimension(final PlayerChangedDimensionEvent event) {
			
			final IMaxHealth maxHealth = getMaxHealth(event.player);
			
			if (maxHealth != null) {
				maxHealth.synchronize(); //synchronize with watching clients
			}
		}
	}

}

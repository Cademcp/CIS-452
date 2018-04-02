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

public class CapabilityMaxMovement {
	@CapabilityInject(IMaxMovement.class)
	public static final Capability<IMaxMovement> MAX_MOVEMENT_CAPABILITY = null;
	public static final EnumFacing DEFAULT_FACING = null;
	public static final ResourceLocation ID = new ResourceLocation(Reference.MOD_ID, "MaxMovement");
	
	@Nullable
	public static IMaxMovement getMaxMovement(final EntityLivingBase entity) {
		if ((entity == null) || !entity.hasCapability(MAX_MOVEMENT_CAPABILITY, DEFAULT_FACING)) {
			return null;
		}
		return entity.getCapability(MAX_MOVEMENT_CAPABILITY, DEFAULT_FACING);
	}
	
	public static ICapabilityProvider createProvider(final IMaxMovement maxMovement) {
		return new CapabilityProvider<>(MAX_MOVEMENT_CAPABILITY, DEFAULT_FACING, maxMovement);
	}
	
	public static void register() {
		class Storage implements Capability.IStorage<IMaxMovement> {

			@Override
			public NBTBase writeNBT(Capability<IMaxMovement> capability, IMaxMovement instance, EnumFacing side) {
				return new NBTTagFloat(instance.getBonusMaxMovement());
			}

			@Override
			public void readNBT(Capability<IMaxMovement> capability, IMaxMovement instance, EnumFacing side, NBTBase nbt) {
				NBTTagFloat tagValue = (NBTTagFloat) nbt;
				instance.setBonusMaxMovement(tagValue.getFloat());
				
			}
			
		}
		
		class Factory implements Callable<IMaxMovement> {
			@Override
			public IMaxMovement call() throws Exception {
				return new MaxMovement(null);
			}
			
		}
		
		CapabilityManager.INSTANCE.register(IMaxMovement.class, new Storage(), new Factory());
	}
	
	@Mod.EventBusSubscriber(modid=Reference.MOD_ID)
	private static class EventHandler {
		
		//attach to all living entities when they are constructed
		@SubscribeEvent
		public static void attachCapabilities(final AttachCapabilitiesEvent<Entity> event) {
			if (event.getObject() instanceof EntityLivingBase) {
				EntityLivingBase entity = (EntityLivingBase) event.getObject();
				final MaxMovement maxMovement = new MaxMovement(entity);
				ICapabilityProvider provider = createProvider(maxMovement);
				event.addCapability(ID, provider);
			}
		}
		
		@SubscribeEvent
		public static void playerClone(final PlayerEvent.Clone event) {
			
			//copy capability after death (or return from the end)
			final IMaxMovement oldMaxMovement = getMaxMovement(event.getOriginal());
			final IMaxMovement newMaxMovement = getMaxMovement(event.getEntityPlayer());
			
			if (newMaxMovement != null && oldMaxMovement != null) {
				newMaxMovement.setBonusMaxMovement(oldMaxMovement.getBonusMaxMovement());
			}
		}
		
		@SubscribeEvent
		public static void playerChangeDimension(final PlayerChangedDimensionEvent event) {
			
			final IMaxMovement maxMovement = getMaxMovement(event.player);
			
			if (maxMovement != null) {
				maxMovement.synchronize(); //synchronize with watching clients
			}
		}
	}
}

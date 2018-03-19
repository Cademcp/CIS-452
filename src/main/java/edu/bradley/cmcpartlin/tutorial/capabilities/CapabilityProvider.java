package edu.bradley.cmcpartlin.tutorial.capabilities;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

public class CapabilityProvider<INTERFACE> implements ICapabilityProvider, INBTSerializable<NBTBase> {

	protected final Capability<INTERFACE> capability;
	protected final EnumFacing facing;
	protected final INTERFACE instance;
	
	public CapabilityProvider(final Capability<INTERFACE> capability, @Nullable final EnumFacing facing,
							  @Nullable final INTERFACE instance) {
		this.capability = capability;
		this.facing = facing;
		this.instance = instance;
	}
	
	@Nullable
	public EnumFacing getFacing() {
		return facing;
	}
	
	@Nullable
	public final INTERFACE getInstance() {
		return instance;
	}
	
	public final Capability<INTERFACE> getCapability() {
		return capability;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == getCapability();
	}
	
	@Override
	@Nullable
	public <T> T getCapability(final Capability<T> capability, @Nullable final EnumFacing facing) {
		
		if (capability == getCapability()) {
			return getCapability().cast(getInstance());
		}
		return null;
	}
	
	@Override
	public NBTBase serializeNBT() {
		return capability.writeNBT(instance, facing);
	}
	
	@Override
	public void deserializeNBT(NBTBase nbt) {
		capability.readNBT(instance, facing, nbt);
		
	}	
}

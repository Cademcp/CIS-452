package TileEntities;

import javax.annotation.Nullable;

import edu.bradley.cmcpartlin.tutorial.inventory.ContainerJewelryTable;
import edu.bradley.cmcpartlin.tutorial.inventory.IContainerCallBacks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityJewelryTable extends TileEntity implements ITickable, IContainerCallBacks {

	private static final int RING_TICKS = 100;		//5 seconds
	private int timeRemaining = 0;					//ticks to make current item
	private boolean inUse = false;					//keep track if the crafting station is in use
	
	//made public to be used in another class later on 
	public static final int MATERIAL_SLOT = 0;		
	public static final int GEM_SLOT = 1;
	public static final int OUTPUT_SLOT = 2;
	public static final int MAX_SLOTS = 3;
	
	private ItemStackHandler inventory = new ItemStackHandler(MAX_SLOTS);
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
	
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}
	
	@Nullable
	@Override
	public <INTERFACE> INTERFACE getCapability(Capability<INTERFACE> capability, @Nullable EnumFacing facing) {
		if (hasCapability(capability, facing)) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory);
		}
		
		return super.getCapability(capability, facing);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("inventory", inventory.serializeNBT());
		return super.writeToNBT(compound);
	}
	
	public ContainerJewelryTable createContainer(final EntityPlayer player) {
		final IItemHandler playerInventory = (IItemHandler) player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
		
		return new ContainerJewelryTable(playerInventory, inventory, player, this);
	}
	
	
	
	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onContainerOpened(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return this.world.getTileEntity(this.pos) == this &&
				player.getDistanceSq(this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5) <= 64;
	}

}

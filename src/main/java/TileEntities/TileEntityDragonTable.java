package TileEntities;

import java.util.Random;

import javax.annotation.Nullable;

import edu.bradley.cmcpartlin.tutorial.init.ModItems;
import edu.bradley.cmcpartlin.tutorial.inventory.ContainerDragonTable;
import edu.bradley.cmcpartlin.tutorial.inventory.ContainerJewelryTable;
import edu.bradley.cmcpartlin.tutorial.inventory.IContainerCallBacks;
import edu.bradley.cmcpartlin.tutorial.particle.ParticleNumbers;
import edu.bradley.cmcpartlin.tutorial.particle.ParticleStar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.WeightedRandom.Item;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import scala.collection.generic.CanCombineFrom;

public class TileEntityDragonTable extends TileEntity implements ITickable, IContainerCallBacks {

	private static final int RING_TICKS = 100;		//5 seconds
	private int timeRemaining = 0;					//ticks to make current item
	private boolean inUse = false;					//keep track if the crafting station is in use
	
	//made public to be used in another class later on 
	public static final int MATERIAL_SLOT = 0;		
	public static final int SCALE_SLOT = 1;
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
		timeRemaining = compound.getInteger("craftTime");
		if (timeRemaining != 0) {
			inUse = true;
		}
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("inventory", inventory.serializeNBT());
		compound.setInteger("craftTime", timeRemaining);
		return super.writeToNBT(compound);
	}
	
	public ContainerDragonTable createContainer(final EntityPlayer player) {
		final IItemHandler playerInventory = (IItemHandler) player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
		
		return new ContainerDragonTable(playerInventory, inventory, player, this);
	}
	
	
	
	private void produceResult() {
		ItemStack result = new ItemStack(ModItems.dragon_blade);
		ItemStack outputStack = inventory.getStackInSlot(OUTPUT_SLOT);
		ItemStack materialStack = inventory.getStackInSlot(MATERIAL_SLOT);
		ItemStack gemStack = inventory.getStackInSlot(SCALE_SLOT);
		
		//we know that we can combine, so decrement each stack
		materialStack.shrink(1);
		gemStack.shrink(1);
		
		if (outputStack.isEmpty()) {
			System.out.println("JT:    assigning new stack to slot");
			inventory.setStackInSlot(OUTPUT_SLOT, result);
		} else if (outputStack.getItem() == ModItems.dragon_blade) {
			System.out.println("JT:     inserting new item in exisiting stack");
			System.out.println("JT:    count currently " + outputStack.getItem().getUnlocalizedName());
			inventory.insertItem(OUTPUT_SLOT, result, false);
		}
	}
	
	@Override
	public void update() {
		
		generateParticles();
		
		if (inUse) {
			timeRemaining--;
			System.out.println("JT:     tick down crafting timer to " + timeRemaining);
		}
		
		if (!this.world.isRemote) {
			if (!inUse && canCombine()) {
				timeRemaining = 100;
				inUse = true;
			}
			
			if ((timeRemaining == 0) && canCombine()) {
				System.out.println("JT:    time remaining is zero, and contianer can combine slots");
				produceResult();
				inUse = false;
				this.markDirty();
			} else if (! canCombine()) {
				inUse = false;
			}
		}

	}
	
	private boolean canCombine() {
		ItemStack materialItems = inventory.getStackInSlot(MATERIAL_SLOT);
		ItemStack gemItems = inventory.getStackInSlot(SCALE_SLOT);
		
		//make sure we have both slots filled
		if (materialItems.isEmpty() || gemItems.isEmpty()) {
			return false;
		}
		
		net.minecraft.item.Item material = materialItems.getItem();
		net.minecraft.item.Item gem = gemItems.getItem();
		
		//check that an appropriate material is used
		if ((material != Items.STICK)) {
			return false;
		}
		
		//check that an appropriate gem is used
		if ((gem != Items.EMERALD)) {
			return false;
		}
		return true;
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
	
	private void generateParticles() {
		
		//don't run on the server
		if (!this.world.isRemote) {
			return;
		}
		
		
		Random rng = new Random();
		
		if (rng.nextInt(10) == 0) {
			double x = pos.getX() + 0.5;
			double y = pos.getY() + 1.5;
			double z = pos.getZ() + 0.5;
			
			double xSpeed = rng.nextGaussian() * 0.02d;
			double ySpeed = rng.nextGaussian() * 0.02d;
			double zSpeed = rng.nextGaussian() * 0.02d;
			
			ParticleStar newEffect = new ParticleStar(this.world, x, y, z, xSpeed, ySpeed, zSpeed);
			//TextureManager manager = Minecraft.getMinecraft().getTextureManager();
			//ParticleNumbers newEffect = new ParticleNumbers(manager, this.world, x, y, z, xSpeed, ySpeed, zSpeed);
			Minecraft.getMinecraft().effectRenderer.addEffect(newEffect);
		}
	}

}

package edu.bradley.cmcpartlin.tutorial.inventory;

import TileEntities.TileEntityJewelryTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerJewelryTable extends Container {

	private final IContainerCallBacks callbacks;	//object to send callbacks to
	
	private final IItemHandler playerInventory;
	private final IItemHandler tableInventory;
	
	private static final int PLAYER_ROWS = 3;	//3 rows of inventory for the player
	
	private static final int SLOTS_PER_ROW = 9;	//9 slots across each row
	private static final int CELL_SIZE = 18;	//18x18 space for slot icon 
	private static final int PLAYER_SLOT_BASE = 84;		//top of first player inventory slot in template
	private static final int HOTBAR_BASE = 142; 	//top of first hotbar slot
	private static final int LEFT_BORDER_WIDTH = 8; //width of grey area before inventory cells begin
	
	private static final int TOTAL_PLAYER_SLOTS = 4 * SLOTS_PER_ROW;	//4 rows in a player inventory * num of slots in each row
	private static final int TOTAL_CONTAINER_SLOTS = TileEntityJewelryTable.MAX_SLOTS + TOTAL_PLAYER_SLOTS;	//3 slots in jewelry table + 36 slots in player
	
	public ContainerJewelryTable(final IItemHandler playerInventory, final IItemHandler tableInventory, final EntityPlayer player, final IContainerCallBacks callbacks) {
		this.playerInventory = playerInventory;
		this.tableInventory = tableInventory;
		
		this.callbacks = callbacks;
		
		//The container is constructed when the player interacts with the block
		callbacks.onContainerOpened(player);
		
		//using anonymous class to override onSlotChanged
		addSlotToContainer(new SlotItemHandler(tableInventory, TileEntityJewelryTable.GEM_SLOT, 17, 12) 
		{
			
			@Override
			public void onSlotChanged() 
			{
				
			}
		});
		
		addSlotToContainer(new SlotItemHandler(tableInventory, TileEntityJewelryTable.MATERIAL_SLOT, 17, 51) 
		{
			
			@Override
			public void onSlotChanged() 
			{
				
			}
		});
		
		addSlotToContainer(new SlotItemHandler(tableInventory, TileEntityJewelryTable.OUTPUT_SLOT, 115, 34) 
		{
			
			@Override
			public void onSlotChanged() 
			{
				
			}
		});
		
		//27 slots of player inventory, PLAYER_SLOT_BASE is top of first player inventory slot
		for (int row = 0; row < PLAYER_ROWS; row++) {
			int y_position = PLAYER_SLOT_BASE + (row * CELL_SIZE);
			
			for (int col = 0; col < SLOTS_PER_ROW; col++) {
				//index starts after hotbar
				int index = SLOTS_PER_ROW + col + row*SLOTS_PER_ROW;
				
				
				int x_position = LEFT_BORDER_WIDTH + (col * CELL_SIZE);
				
				addSlotToContainer(new SlotItemHandler(playerInventory, index, x_position, y_position));
			}
		}
		
		for (int col = 0; col < SLOTS_PER_ROW; col++) {
			int x_position = LEFT_BORDER_WIDTH + (col * CELL_SIZE);
			int index = col;
			
			addSlotToContainer(new SlotItemHandler(playerInventory, index, x_position, HOTBAR_BASE));
		}
		
	}
	
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		final Slot slot = this.inventorySlots.get(index);
		ItemStack originalStack = ItemStack.EMPTY;
		
		if (slot != null && !slot.getStack().isEmpty()) {
			final ItemStack stack = slot.getStack();
			originalStack = stack.copy();
			
			if (index == TileEntityJewelryTable.OUTPUT_SLOT) {
				//slots are in container order, 0-2 are table, 3-38 are play inventory
				//so attempt to move output into the player inventory
				if (!this.mergeItemStack(stack, TileEntityJewelryTable.MAX_SLOTS, TOTAL_CONTAINER_SLOTS, false)) {
					return ItemStack.EMPTY;		//cannot fit in inventory, abort transfer
				}
			}
			
			else if (index != TileEntityJewelryTable.GEM_SLOT && index != TileEntityJewelryTable.MATERIAL_SLOT) {
				//transfer from player inventory to the table
				
				Item toMove = stack.getItem();
				if (toMove == Items.IRON_INGOT || toMove == Items.GOLD_INGOT) {
					if (!this.mergeItemStack(stack, TileEntityJewelryTable.MATERIAL_SLOT, TileEntityJewelryTable.MATERIAL_SLOT + 1, false)) {
						return ItemStack.EMPTY;
					}
				}
				if (toMove == Items.DIAMOND || toMove == Items.EMERALD) {
					if (!this.mergeItemStack(stack, TileEntityJewelryTable.GEM_SLOT, TileEntityJewelryTable.GEM_SLOT + 1, false)) {
						return ItemStack.EMPTY;
					}
				}
			}
			else {
				//one of the inputs being transferred back to player inventory
				if (!this.mergeItemStack(stack, TileEntityJewelryTable.MAX_SLOTS, TOTAL_CONTAINER_SLOTS, false)) {
					return ItemStack.EMPTY;
				}
			}
			if (stack.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
			if (stack.getCount() == originalStack.getCount()) {
				return ItemStack.EMPTY;
			}
			slot.onTake(playerIn, stack);
		}
		return originalStack;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return callbacks.isUsableByPlayer(playerIn);
	}
	
	@Override
	public void onContainerClosed(EntityPlayer playerIn) {
		super.onContainerClosed(playerIn);
		
		callbacks.onContainerClosed(playerIn);
	}
	
	public IItemHandler getPlayerInventory() {
		return playerInventory;
	}
	
	public IItemHandler getTableInventory() {
		return tableInventory;
	}

}

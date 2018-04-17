package edu.bradley.cmcpartlin.tutorial.gui;

import javax.annotation.Nullable;

import TileEntities.TileEntityDragonTable;
import TileEntities.TileEntityJewelryTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GUIHandler implements IGuiHandler {

	public GUIHandler() {
		System.out.println("JT:    registering GUI Handler");
	}
	
	@Override
	@Nullable
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		
		final TileEntity tileEntity = world.getTileEntity(new BlockPos(x,y,z));
		
		switch(ID) {
		case GuiIDs.DRAGON_TABLE:
			if (tileEntity != null) {
				System.out.println("JT:    server-side container create");
				return ((TileEntityDragonTable) tileEntity).createContainer(player);
			}
			break;
		}
		return null;
	}

	@Override
	@Nullable
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		final TileEntity tileEntity = world.getTileEntity(new BlockPos(x,y,z));
		
		switch(ID) {
		case GuiIDs.DRAGON_TABLE:
			if (tileEntity != null) {
				TileEntityDragonTable table = (TileEntityDragonTable) tileEntity;
				
				System.out.println("JT:    client-side container create");
				return new GUIDragonTable(table.createContainer(player));
			}
		}
		return null;
	}

}

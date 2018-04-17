package edu.bradley.cmcpartlin.tutorial.blocks;

import com.example.examplemod.Reference;

import TileEntities.TileEntityDragonTable;
import TileEntities.TileEntityJewelryTable;
import edu.bradley.cmcpartlin.tutorial.gui.GuiIDs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockDragonTable extends BlockTileEntity<TileEntityDragonTable> {
	
	public BlockDragonTable(String name, Material material) {
		super(name, material);
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		System.out.println("JT  create new tile entity");
		return new TileEntityDragonTable();
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		if (! worldIn.isRemote) {
			playerIn.openGui(Reference.MOD_ID, GuiIDs.DRAGON_TABLE, worldIn, pos.getX(), pos.getY(), pos.getZ());
			
			return true;
		}
		return false;
	}
	
}

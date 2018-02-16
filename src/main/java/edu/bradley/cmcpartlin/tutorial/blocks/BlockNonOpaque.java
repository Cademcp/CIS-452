package edu.bradley.cmcpartlin.tutorial.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public class BlockNonOpaque extends BlockBase {

	public BlockNonOpaque(String name, Material material) {
		super(name, material);
	
	}
	
	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

}

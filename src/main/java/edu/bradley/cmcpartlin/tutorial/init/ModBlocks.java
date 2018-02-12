package edu.bradley.cmcpartlin.tutorial.init;

import java.util.ArrayList;
import java.util.List;

import edu.bradley.cmcpartlin.tutorial.blocks.BlockBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlocks {

	public static List<Block> BLOCKS = new ArrayList<Block>();
	
	public static Block CHEESE_BLOCK = new BlockBase("cheese_block", Material.IRON);
	
	public static Block CHAIR_Block = new BlockBase("chair_block", Material.IRON);
}

package edu.bradley.cmcpartlin.tutorial.init;

import java.util.ArrayList;
import java.util.List;

import edu.bradley.cmcpartlin.tutorial.blocks.BlockBase;
import edu.bradley.cmcpartlin.tutorial.blocks.BlockJewelryTable;
import edu.bradley.cmcpartlin.tutorial.blocks.BlockNonOpaque;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlocks {

	public static List<Block> BLOCKS = new ArrayList<Block>();
	
	public static Block CHEESE_BLOCK = new BlockBase("cheese_block", Material.IRON);
	
	public static Block CHAIR_BLOCK = new BlockNonOpaque("chair_block", Material.IRON);
	
	public static Block DRAGON_SCALE = new BlockNonOpaque("dragon_scale", Material.IRON);
	
	public static Block JEWELRY_TABLE = new BlockJewelryTable("jewelry_table", Material.IRON);
	
	
}

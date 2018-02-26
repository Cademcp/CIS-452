package edu.bradley.cmcpartlin.tutorial.init.messages;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class BlockHitMessage implements IMessage {

	private BlockPos blockPos;
	
	//default constructor required for all messages
	public BlockHitMessage() {
		
		//calculate the position of the block we are looking at 
		RayTraceResult mouseOver = Minecraft.getMinecraft().objectMouseOver;
		blockPos = mouseOver.getBlockPos();
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		blockPos = BlockPos.fromLong(buf.readLong());

	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(blockPos.toLong());

	}
	
	public static class Handler implements IMessageHandler<BlockHitMessage, IMessage> {

		@Override
		public IMessage onMessage(BlockHitMessage message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message,ctx));
			return null;
		}

		private static void handle(BlockHitMessage message, MessageContext ctx) {
			EntityPlayerMP playerEntity = ctx.getServerHandler().player;
			World world = playerEntity.getEntityWorld();
			BlockPos position = message.blockPos;
			
			if (world.isBlockLoaded(position)) {
				Block block = world.getBlockState(position).getBlock();
				playerEntity.sendStatusMessage(new TextComponentString(TextFormatting.GREEN + "Hit Block: " + block.getRegistryName()), false);
			}
		}
		
	}

}

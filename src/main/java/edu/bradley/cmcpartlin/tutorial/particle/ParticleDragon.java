package edu.bradley.cmcpartlin.tutorial.particle;

import java.util.Random;

import com.example.examplemod.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ParticleDragon extends Particle {

	private final ResourceLocation dragonTexture = new ResourceLocation(Reference.MOD_ID, "particles/dragon_wings_down");
	
	public ParticleDragon(World worldIn, double x, double y, double z, double speedX,
			double speedY, double speedZ) {
		super(worldIn, x, y, z, speedX, speedY, speedZ);
		
		final float ALPHA_MAX = 1f;
		
		this.particleRed = ((float) (Math.random() * 0.2) + 0.5f);
		this.particleGreen = ((float) (Math.random() * 0.2) + 0.5f);
		this.particleBlue = ((float) (Math.random() * 0.2) + 0.5f);
		this.particleAlpha = ALPHA_MAX;
		
		this.particleMaxAge = 40;
		
		//the particle's velocity vector
		motionX = speedX;
		motionY = speedY;
		motionZ = speedZ;
		
		TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(dragonTexture.toString());
		setParticleTexture(sprite);
	}

	@Override
	public int getFXLayer() {
		return 1;		//use the blocks & items texture atlas
	}
	
	@Override
	public int getBrightnessForRender(float partialTick) {
		final int FULL_BRIGHTNESS = 0xf000f0;
		return FULL_BRIGHTNESS;
	}
	
	//turn on depthmask so transparent objects render over this particle
	@Override
	public boolean shouldDisableDepth() {
		return false;
	}
	
}

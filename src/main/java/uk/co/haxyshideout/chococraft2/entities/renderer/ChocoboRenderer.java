package uk.co.haxyshideout.chococraft2.entities.renderer;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import uk.co.haxyshideout.chococraft2.config.Constants;
import uk.co.haxyshideout.chococraft2.entities.EntityChocobo;

/**
 * Created by clienthax on 14/4/2015.
 */
public class ChocoboRenderer extends RenderLiving {

	public ChocoboRenderer(RenderManager rendermanagerIn, ModelBase modelbaseIn) {
		super(rendermanagerIn, modelbaseIn, 1.5f);
	}

	@Override
	public void renderLivingLabel(Entity entityIn, String str, double x, double y, double z, int maxDistance) {
		super.renderLivingLabel(entityIn, str, x, y+0.2d, z, maxDistance);
	}

	@Override
	public float handleRotationFloat(EntityLivingBase entityLiving, float f) {//Wing rotation
		EntityChocobo entityChocobo = (EntityChocobo) entityLiving;
		return (MathHelper.sin(entityChocobo.wingRotation) + 1F) * entityChocobo.destPos;
	}

	@Override
	protected void preRenderCallback(EntityLivingBase entityliving, float f) {//TODO big hack because the model is positioned wrong
		GlStateManager.translate(-0.075, 0, -0.45);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		EntityChocobo entityChocobo = (EntityChocobo) entity;
		String path = "textures/entities/Chocobos/"+ (entityChocobo.isTamed() ? "Tamed" : "Untamed") +"/";
		if(entityChocobo.getBagType() == EntityChocobo.BagType.PACK)
			path += "PackBagged/";
		else if(entityChocobo.getBagType() == EntityChocobo.BagType.SADDLE)
			path += "SaddleBagged/";
		else if(entityChocobo.isSaddled())
			path += "Saddled/";

		path +=(entityChocobo.isMale() ? "Male" : "Female") +"/"+entityChocobo.getChocoboColor().name().toLowerCase()+"chocobo.png";
		return new ResourceLocation(Constants.MODID, path);
	}
}

package uk.co.haxyshideout.chococraft2.entities.renderer;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import uk.co.haxyshideout.chococraft2.config.Constants;
import uk.co.haxyshideout.chococraft2.entities.EntityBabyChocobo;

/**
 * Created by clienthax on 14/4/2015.
 */
public class BabyChocoboRenderer extends RenderLiving
{

	public BabyChocoboRenderer(RenderManager rendermanagerIn, ModelBase modelbaseIn)
	{
		super(rendermanagerIn, modelbaseIn, 1.5f);
		this.shadowSize = 0f;
	}

	@Override
	public void renderLivingLabel(Entity entityIn, String str, double x, double y, double z, int maxDistance)
	{
		super.renderLivingLabel(entityIn, str, x, y + 0.2d, z, maxDistance);
	}

	@Override
	protected void preRenderCallback(EntityLivingBase entityliving, float f)
	{
		// TODO big hack because the model is positioned wrong
		GlStateManager.translate(-0.075, 0, -0.45);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		EntityBabyChocobo entityChocobo = (EntityBabyChocobo) entity;
		String path = "textures/entities/Chicobos/" + (entityChocobo.isTamed() ? "Tamed" : "Untamed") + "/";
		path += entityChocobo.getChocoboColor().name().toLowerCase() + "chocobo.png";
		return new ResourceLocation(Constants.MODID, path);
	}
}

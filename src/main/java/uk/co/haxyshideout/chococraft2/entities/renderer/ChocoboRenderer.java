package uk.co.haxyshideout.chococraft2.entities.renderer;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import uk.co.haxyshideout.chococraft2.config.Constants;
import uk.co.haxyshideout.chococraft2.entities.EntityChocobo;

/**
 * Created by clienthax on 14/4/2015.
 */
public class ChocoboRenderer extends RenderLiving {

	ResourceLocation resourceLocation = new ResourceLocation(Constants.MODID, "textures/entities/Chocobos/Untamed/Male/redchocobo.png");

	public ChocoboRenderer(RenderManager rendermanagerIn, ModelBase modelbaseIn) {
		super(rendermanagerIn, modelbaseIn, 1.5f);
	}

	@Override
	public float handleRotationFloat(EntityLivingBase entityLiving, float f) {//Wing rotation
		EntityChocobo entityChocobo = (EntityChocobo) entityLiving;
		return (MathHelper.sin(entityChocobo.wingRotation) + 1F) * entityChocobo.destPos;
	}


	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return resourceLocation;
	}
}

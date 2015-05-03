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
import uk.co.haxyshideout.chococraft2.entities.BagType;
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
		EntityChocobo entityChocobo = (EntityChocobo) entity;
		String path = "textures/entities/Chocobos/"+ (entityChocobo.isTamed() ? "Tamed" : "Untamed") +"/";
		if(entityChocobo.getBagType() == BagType.PACK)
			path += "PackBagged/";
		else if(entityChocobo.getBagType() == BagType.SADDLE)
			path += "SaddleBagged/";
		else if(entityChocobo.isSaddled())
			path += "Saddled/";

		path +=(entityChocobo.isMale ? "Male" : "Female") +"/"+entityChocobo.getChocoboColor().name().toLowerCase()+"chocobo.png";
		resourceLocation = new ResourceLocation(Constants.MODID, path);
		return resourceLocation;
	}
}

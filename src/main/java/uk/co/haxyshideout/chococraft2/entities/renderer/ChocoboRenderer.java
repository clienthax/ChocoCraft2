package uk.co.haxyshideout.chococraft2.entities.renderer;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import uk.co.haxyshideout.chococraft2.config.Constants;

/**
 * Created by clienthax on 14/4/2015.
 */
public class ChocoboRenderer extends RenderLiving {

	public ChocoboRenderer(RenderManager rendermanagerIn, ModelBase modelbaseIn) {
		super(rendermanagerIn, modelbaseIn, 0.5f);
	}

	ResourceLocation resourceLocation = new ResourceLocation(Constants.MODID, "textures/entities/Chocobos/Untamed/Male/redchocobo.png");

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return resourceLocation;
	}
}

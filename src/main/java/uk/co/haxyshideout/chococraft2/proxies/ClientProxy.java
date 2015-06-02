package uk.co.haxyshideout.chococraft2.proxies;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import uk.co.haxyshideout.chococraft2.config.Additions;
import uk.co.haxyshideout.chococraft2.config.Constants;
import uk.co.haxyshideout.chococraft2.entities.EntityChocobo;
import uk.co.haxyshideout.chococraft2.entities.models.ModelChocobo;
import uk.co.haxyshideout.chococraft2.entities.renderer.ChocoboRenderer;
import uk.co.haxyshideout.haxylib.utils.RegistryHelper;

/**
 * Created by clienthax on 12/4/2015.
 */
public class ClientProxy extends ServerProxy {

	@Override
	public void registerRenderers() {
		RegistryHelper.registerRenderers(Additions.class, Constants.MODID);
	}

	@Override
	public void registerEntities() {
		super.registerEntities();
		//Have to register renderers in here because mojang is stupid
		RenderManager manager = Minecraft.getMinecraft().getRenderManager();
		RenderingRegistry.registerEntityRenderingHandler(EntityChocobo.class, new ChocoboRenderer(manager, new ModelChocobo()));
	}

}

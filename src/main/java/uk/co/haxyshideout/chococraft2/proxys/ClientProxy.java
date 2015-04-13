package uk.co.haxyshideout.chococraft2.proxys;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import uk.co.haxyshideout.chococraft2.config.Additions;
import uk.co.haxyshideout.chococraft2.config.Constants;
import uk.co.haxyshideout.haxylib.utils.RegistryHelper;

/**
 * Created by clienthax on 12/4/2015.
 */
public class ClientProxy extends ServerProxy {

	@Override
	public void registerRenderers() {

		RegistryHelper.registerRenderers(Additions.class, Constants.MODID);

	}
}

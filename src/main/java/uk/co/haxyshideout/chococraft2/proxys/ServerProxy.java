package uk.co.haxyshideout.chococraft2.proxys;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import uk.co.haxyshideout.chococraft2.ChocoCraft2;
import uk.co.haxyshideout.chococraft2.entities.EntityChocobo;

/**
 * Created by clienthax on 12/4/2015.
 */
public class ServerProxy {

	public void registerRenderers(){}

	@SuppressWarnings("UnusedAssignment")
	public void registerEntities() {
		int entityId = 0;
		EntityRegistry.registerModEntity(EntityChocobo.class, "chocobo", entityId++, ChocoCraft2.instance, 64, 1, true);
	}

}

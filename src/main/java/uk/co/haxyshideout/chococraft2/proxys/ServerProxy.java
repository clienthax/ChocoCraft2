package uk.co.haxyshideout.chococraft2.proxys;

import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import uk.co.haxyshideout.chococraft2.ChocoCraft2;
import uk.co.haxyshideout.chococraft2.entities.EntityChocobo;
import uk.co.haxyshideout.chococraft2.worldgen.GysahlGen;

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

	public void registerWorldGenerators() {
		GameRegistry.registerWorldGenerator(new GysahlGen(), 1);//TODO config option for weight
	}
}

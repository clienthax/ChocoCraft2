package uk.co.haxyshideout.chococraft2.proxies;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
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
		EntityRegistry.addSpawn(EntityChocobo.class, 2, 1, 5, EnumCreatureType.AMBIENT, BiomeGenBase.forest);
		EntityRegistry.addSpawn(EntityChocobo.class, 2, 1, 3, EnumCreatureType.AMBIENT, BiomeGenBase.hell);
	}

	public void registerWorldGenerators() {
		GameRegistry.registerWorldGenerator(new GysahlGen(), 1);//TODO config option for weight
	}

	public void openChocopedia(EntityChocobo chocobo) {

	}

	public void updateRiderState(Entity rider) {

	}

}

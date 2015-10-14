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
		EntityRegistry.registerGlobalEntityID(EntityChocobo.class, "chocobo", EntityRegistry.findGlobalUniqueEntityId(), 0xFFEE00, 0xFFFFFF);
        EntityRegistry.addSpawn(EntityChocobo.class, 15, 4, 10, EnumCreatureType.MONSTER, 
                BiomeGenBase.taigaHills, BiomeGenBase.jungle, BiomeGenBase.jungleHills, BiomeGenBase.plains, 
                BiomeGenBase.taiga, BiomeGenBase.forest, BiomeGenBase.forestHills, BiomeGenBase.swampland, 
                BiomeGenBase.river, BiomeGenBase.beach, BiomeGenBase.desert, BiomeGenBase.extremeHills, 
                BiomeGenBase.extremeHillsEdge, BiomeGenBase.desertHills, BiomeGenBase.frozenRiver, BiomeGenBase.icePlains, 
                BiomeGenBase.coldBeach, BiomeGenBase.coldTaiga, BiomeGenBase.megaTaiga, BiomeGenBase.coldTaigaHills, 
                BiomeGenBase.stoneBeach, BiomeGenBase.extremeHillsPlus, BiomeGenBase.birchForest, BiomeGenBase.savanna, 
                BiomeGenBase.mesa, BiomeGenBase.roofedForest, BiomeGenBase.mushroomIsland, BiomeGenBase.mushroomIslandShore, 
                BiomeGenBase.mesaPlateau);
	}

	public void registerWorldGenerators() {
		GameRegistry.registerWorldGenerator(new GysahlGen(), 1);//TODO config option for weight
	}

	public void openChocopedia(EntityChocobo chocobo) {

	}

	public void updateRiderState(Entity rider) {

	}

}

package uk.co.haxyshideout.chococraft2.proxies;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import uk.co.haxyshideout.chococraft2.ChocoCraft2;
import uk.co.haxyshideout.chococraft2.entities.EntityBabyChocobo;
import uk.co.haxyshideout.chococraft2.entities.EntityChocobo;
import uk.co.haxyshideout.chococraft2.worldgen.GysahlGen;

/**
 * Created by clienthax on 12/4/2015.
 */
public class ServerProxy
{

	public void registerRenderers()
	{

	}

	@SuppressWarnings("UnusedAssignment")
	public void registerEntities()
	{
		int entityId = 0;
		
		EntityRegistry.registerModEntity(EntityBabyChocobo.class, "babychocobo", entityId++, ChocoCraft2.instance, 64, 1, true);
		EntityRegistry.registerModEntity(EntityChocobo.class, "chocobo", entityId++, ChocoCraft2.instance, 64, 1, true);
		//TODO might have to change this back to monster to decrease the spawn rates
		EntityRegistry.addSpawn(EntityChocobo.class, 50, 0, 4, EnumCreatureType.CREATURE,
				                BiomeGenBase.taigaHills, BiomeGenBase.jungle, BiomeGenBase.jungleHills, BiomeGenBase.plains,
				                BiomeGenBase.taiga, BiomeGenBase.forest, BiomeGenBase.forestHills, BiomeGenBase.swampland,
				                BiomeGenBase.river, BiomeGenBase.beach, BiomeGenBase.desert, BiomeGenBase.extremeHills,
				                BiomeGenBase.extremeHillsEdge, BiomeGenBase.desertHills, BiomeGenBase.frozenRiver, BiomeGenBase.icePlains,
				                BiomeGenBase.coldBeach, BiomeGenBase.coldTaiga, BiomeGenBase.megaTaiga, BiomeGenBase.coldTaigaHills,
				                BiomeGenBase.stoneBeach, BiomeGenBase.extremeHillsPlus, BiomeGenBase.birchForest, BiomeGenBase.savanna,
				                BiomeGenBase.mesa, BiomeGenBase.roofedForest, BiomeGenBase.mushroomIsland, BiomeGenBase.mushroomIslandShore,
				                BiomeGenBase.mesaPlateau);
		EntityRegistry.addSpawn(EntityChocobo.class, 2, 1, 3, EnumCreatureType.CREATURE, BiomeGenBase.hell);
	}

	public void registerWorldGenerators()
	{
		GameRegistry.registerWorldGenerator(new GysahlGen(), 7);// TODO config option for weight
	}

	public void openChocopedia(EntityChocobo chocobo)
	{
		;
	}

	public void updateRiderState(EntityPlayer rider)
	{
		;
	}

}

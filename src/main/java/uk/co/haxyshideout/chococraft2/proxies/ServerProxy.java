package uk.co.haxyshideout.chococraft2.proxies;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import uk.co.haxyshideout.chococraft2.ChocoCraft2;
import uk.co.haxyshideout.chococraft2.config.ChococraftConfig;
import uk.co.haxyshideout.chococraft2.entities.EntityBabyChocobo;
import uk.co.haxyshideout.chococraft2.entities.EntityChocobo;
import uk.co.haxyshideout.chococraft2.worldgen.GysahlGen;

import java.util.List;

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
		ChococraftConfig config = ChocoCraft2.instance.getConfig();
		List<BiomeGenBase> spawnBiomes = config.getSpawnBiomes();
		EntityRegistry.addSpawn(EntityChocobo.class, config.getOverworldWeight(), config.getOverworldMinGroup(), config.getOverworldMaxGroup(), EnumCreatureType.CREATURE, spawnBiomes.toArray(new BiomeGenBase[spawnBiomes.size()]));
		EntityRegistry.addSpawn(EntityChocobo.class, config.getNetherWeight(), config.getNetherMinGroup(), config.getNetherMaxGroup(), EnumCreatureType.CREATURE, BiomeGenBase.hell);
	}

	public void registerWorldGenerators()
	{
		GameRegistry.registerWorldGenerator(new GysahlGen(), ChocoCraft2.instance.getConfig().getGysahlWorldGenWeight());
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

package uk.co.haxyshideout.chococraft2.config;

import static uk.co.haxyshideout.chococraft2.entities.EntityChocobo.ChocoboColor.BLACK;
import static uk.co.haxyshideout.chococraft2.entities.EntityChocobo.ChocoboColor.BLUE;
import static uk.co.haxyshideout.chococraft2.entities.EntityChocobo.ChocoboColor.GOLD;
import static uk.co.haxyshideout.chococraft2.entities.EntityChocobo.ChocoboColor.GREEN;
import static uk.co.haxyshideout.chococraft2.entities.EntityChocobo.ChocoboColor.PINK;
import static uk.co.haxyshideout.chococraft2.entities.EntityChocobo.ChocoboColor.PURPLE;
import static uk.co.haxyshideout.chococraft2.entities.EntityChocobo.ChocoboColor.RED;
import static uk.co.haxyshideout.chococraft2.entities.EntityChocobo.ChocoboColor.WHITE;
import static uk.co.haxyshideout.chococraft2.entities.EntityChocobo.ChocoboColor.YELLOW;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import com.google.common.reflect.TypeToken;
import lombok.Getter;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.biome.BiomeGenBase;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.apache.commons.io.FileUtils;
import uk.co.haxyshideout.chococraft2.entities.ChocoboAbilityInfo;

import java.io.File;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;

/**
 * Created by clienthax on 13/4/2015.
 */
public class ChococraftConfig
{

	@Getter
	private int ticksToAdult;
	@Getter
	private int changeForFeather;
	@Getter
	private int ticksUntilNextFeather;
	List<String> normalSpawnBiomeNames;
	@Getter
	private int overworldMinGroup;
	@Getter
	private int overworldMaxGroup;
	@Getter
	private int overworldWeight;
	@Getter
	private int netherMinGroup;
	@Getter
	private int netherMaxGroup;
	@Getter
	private int netherWeight;
	@Getter
	private int gysahlWorldGenWeight;
	@Getter
	private HashMap<String, HashMap<String, List<HashMap<String, String>>>> breedingInfoHashmap;

	CommentedConfigurationNode mainNode;

	public ChococraftConfig()
	{
		loadConfigFile();
		loadBreedingConfigFile();
	}

	private void loadBreedingConfigFile() {
		try {
			File breedingConfigFile = new File("./config/", "chococraft2breeding.hocon");
			if(!breedingConfigFile.exists()) {//TODO figure out how to copy this out of the jar/devenv at some point
				FileUtils.writeLines(breedingConfigFile, Resources.readLines(new URL("https://raw.githubusercontent.com/clienthax/chococraft2/master/src/main/resources/assets/chococraft2/cfg/breeding.hocon"), Charset.defaultCharset()));
			}
			ConfigurationLoader<CommentedConfigurationNode> configurationLoader = HoconConfigurationLoader.builder().setFile(breedingConfigFile).build();
			CommentedConfigurationNode mainNode = configurationLoader.load();
			CommentedConfigurationNode breedingConfigNode = mainNode.getNode("BreedingConfig");
			breedingInfoHashmap = breedingConfigNode.getValue(new TypeToken<HashMap<String, HashMap<String, List<HashMap<String, String>>>>>() {
			});
		} catch (Exception e) {
			System.out.println("Error loading the chococraft 2 breeding config");
			e.printStackTrace();
		}
	}

	private void loadConfigFile() {
		try {
			File configFile = new File("./config/", "chococraft2.hocon");
			ConfigurationLoader<CommentedConfigurationNode> configurationLoader = HoconConfigurationLoader.builder().setFile(configFile).build();
			mainNode = configurationLoader.load(ConfigurationOptions.defaults().setShouldCopyDefaults(true));
			CommentedConfigurationNode babyNode = mainNode.getNode("babys");
			ticksToAdult = babyNode.getNode("ticksToAdult").setComment("How many ticks it will take for the baby Chocobo to grow into the adult stage (Default 27000)").getInt(27000);
			CommentedConfigurationNode adultNode = mainNode.getNode("adults");
			CommentedConfigurationNode adultDropsNode = adultNode.getNode("drops");
			changeForFeather = adultDropsNode.getNode("changeForFeather").setComment("The change out of 100, for a feather to drop from a chocobo (Default 10)").getInt(10);
			ticksUntilNextFeather = adultDropsNode.getNode("ticksUntilNextFeather").setComment("How many ticks between a change for a feather to drop (Default 5000)").getInt(5000);
			CommentedConfigurationNode adultSpawnNode = adultNode.getNode("spawns");
			List<String> defaultSpawnBiomes = Lists.newArrayList("TaigaHills", "Jungle", "JungleHills", "Plains",
					"Taiga", "Forest", "ForestHills", "Swampland", "River", "Beach", "Desert", "Extreme Hills",
					"Extreme Hills Edge", "DesertHills", "FrozenRiver", "Ice Plains", "Cold Beach", "Cold Taiga",
					"Mega Taiga", "Cold Taiga Hills", "Stone Beach", "Extreme Hills+", "Birch Forest", "Savanna",
					"Mesa", "Roofed Forest", "MushroomIsland", "MushroomIslandShore", "Mesa Plateau"
			);
			normalSpawnBiomeNames = adultSpawnNode.getNode("SpawnBiomeNames").setComment("List of all biomes in the overworld that normal Chocobos should spawn in").getList(new TypeToken<String>() {}, defaultSpawnBiomes);
			CommentedConfigurationNode overworldNode = adultSpawnNode.getNode("overworldSpawn");
			overworldMinGroup = overworldNode.getNode("overworldMinGroup").setComment("Minimum amount to spawn in group (Default 0)").getInt(0);
			overworldMaxGroup = overworldNode.getNode("overworldMaxGroup").setComment("Maximum amount to spawn in group (Default 4)").getInt(4);
			overworldWeight = overworldNode.getNode("overworldWeight").setComment("Spawn weight (Default 50)").getInt(50);
			CommentedConfigurationNode netherNode = adultSpawnNode.getNode("netherSpawn");
			netherMinGroup = netherNode.getNode("overworldMinGroup").setComment("Minimum amount to spawn in group (Default 1)").getInt(1);
			netherMaxGroup = netherNode.getNode("overworldMaxGroup").setComment("Maximum amount to spawn in group (Default 3)").getInt(3);
			netherWeight = netherNode.getNode("overworldWeight").setComment("Spawn weight (Default 2)").getInt(2);

			CommentedConfigurationNode worldGen = mainNode.getNode("worldGen");
			gysahlWorldGenWeight = worldGen.getNode("gysahlWorldGenWeight").setComment("Weight of the gysahl generator, lower = more, higher = less (Defaut 7)").getInt(7);

			loadAbilityInfo();
			configurationLoader.save(mainNode);
		} catch (Exception e) {
			System.out.println("Error loading Chococraft 2 config");
			e.printStackTrace();
		}
	}

	public List<BiomeGenBase> getSpawnBiomes() {
		List<BiomeGenBase> spawnBiomes = Lists.newArrayListWithCapacity(normalSpawnBiomeNames.size());
		for(BiomeGenBase biomeGenBase : BiomeGenBase.getBiomeGenArray()) {
			if(biomeGenBase != null && normalSpawnBiomeNames.contains(biomeGenBase.biomeName)) {
				spawnBiomes.add(biomeGenBase);
			}
		}
		return spawnBiomes;
	}

	private void loadAbilityInfo()
	{
		CommentedConfigurationNode abilityNode = mainNode.getNode("abilitys");
		loadAbilityForColout(new ChocoboAbilityInfo(YELLOW).setSpeeds(20, 10, 0).setMaxHP(30).setStepHeight(1, 0.5f), abilityNode.getNode("YELLOW"));
		loadAbilityForColout(new ChocoboAbilityInfo(GREEN).setSpeeds(27, 10, 0).setMaxHP(30).setStepHeight(2, 0.5f), abilityNode.getNode("GREEN"));
		loadAbilityForColout(new ChocoboAbilityInfo(BLUE).setSpeeds(27, 55, 0).setMaxHP(30).setStepHeight(1, 0.5f).setCanWalkOnWater(true), abilityNode.getNode("BLUE"));
		loadAbilityForColout(new ChocoboAbilityInfo(WHITE).setSpeeds(35, 45, 0).setMaxHP(40).setStepHeight(2, 0.5f).setCanClimb(true).setCanWalkOnWater(true), abilityNode.getNode("WHITE"));
		loadAbilityForColout(new ChocoboAbilityInfo(BLACK).setSpeeds(40, 20, 0).setMaxHP(40).setStepHeight(2, 0.5f).setCanWalkOnWater(true).setCanClimb(true), abilityNode.getNode("BLACK"));
		loadAbilityForColout(new ChocoboAbilityInfo(GOLD).setSpeeds(50, 20, 55).setMaxHP(50).setStepHeight(2, 0.5f).setCanWalkOnWater(true).setCanClimb(true).setCanFly(true).setImmuneToFire(true), abilityNode.getNode("GOLD"));// TODO needs particles
		loadAbilityForColout(new ChocoboAbilityInfo(PINK).setSpeeds(55, 25, 60).setMaxHP(50).setStepHeight(2, 0.5f).setCanClimb(true).setCanWalkOnWater(true).setCanFly(true), abilityNode.getNode("PINK"));
		loadAbilityForColout(new ChocoboAbilityInfo(RED).setSpeeds(55, 25, 60).setMaxHP(50).setStepHeight(2, 0.5f).setCanClimb(true).setCanWalkOnWater(true).setCanFly(true), abilityNode.getNode("RED"));
		loadAbilityForColout(new ChocoboAbilityInfo(PURPLE).setSpeeds(40, 10, 55).setMaxHP(50).setStepHeight(1, 0.5f).setCanClimb(true).setCanFly(true).setImmuneToFire(true).addRiderAbility(new PotionEffect(12, 100, -1, true, false)), abilityNode.getNode("PURPLE"));
	}

	private void loadAbilityForColout(ChocoboAbilityInfo abilityInfo, CommentedConfigurationNode node) {
		float landSpeed = node.getNode("landSpeed").getFloat(abilityInfo.getLandSpeed());
		float waterSpeed = node.getNode("waterSpeed").getFloat(abilityInfo.getWaterSpeed());
		float airSpeed = node.getNode("airSpeed").getFloat(abilityInfo.getAirbornSpeed());
		int maxHP = node.getNode("maxHP").getInt(abilityInfo.getMaxHP());
		float mountedStepHeight = node.getNode("mountedStepHeight").getFloat(abilityInfo.getStepHeight(true));
		float normalStepHeight = node.getNode("normalStepHeight").getFloat(abilityInfo.getStepHeight(false));
		boolean canWalkOnWater = node.getNode("canWalkOnWater").getBoolean(abilityInfo.canWalkOnWater());
		boolean canClimb = node.getNode("canClimb").getBoolean(abilityInfo.canClimb());
		boolean canFly = node.getNode("canFly").getBoolean(abilityInfo.canFly());
		boolean immuneToFire = node.getNode("immuneToFire").getBoolean(abilityInfo.isImmuneToFire());
		abilityInfo.setSpeeds(landSpeed, waterSpeed, airSpeed).setMaxHP(maxHP).setStepHeight(mountedStepHeight, normalStepHeight).setCanWalkOnWater(canWalkOnWater)
				.setCanClimb(canClimb).setCanFly(canFly).setImmuneToFire(immuneToFire).save();
	}

}

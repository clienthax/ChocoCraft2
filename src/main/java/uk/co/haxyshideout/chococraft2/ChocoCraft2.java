package uk.co.haxyshideout.chococraft2;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import uk.co.haxyshideout.chococraft2.commands.DebugCommand;
import uk.co.haxyshideout.chococraft2.config.Additions;
import uk.co.haxyshideout.chococraft2.config.Constants;
import uk.co.haxyshideout.chococraft2.config.RecipeHandler;
import uk.co.haxyshideout.chococraft2.events.EventHandler;
import uk.co.haxyshideout.chococraft2.network.PacketRegistry;
import uk.co.haxyshideout.chococraft2.proxies.ServerProxy;

/**
 * Created by clienthax on 12/4/2015.
 */
@Mod(modid = Constants.MODID, name = Constants.MODNAME, version = Constants.MODVERSION)
public class ChocoCraft2 {
	/*
	TODO list
	chicibos + growing + growing by cake
	chocobo bag code
	special ability code (possibly a extra handler for this), flying , falling damage, fire proof, speed etc
	breeding!
	the special gyshall recipies (red etc)
	achievements
	purple egg needs to spawn the bloody chocobo
	chocobos must stare at people. is best
	whole config system - use configurate, is awesome. as forges config system sucks.
	chocopedia text - needs to be translatable
	chocobos to be ridden properly
	chocobos seem to go invisible if you walk to close to them

	optimizations:
	condense booleans in datawatcher.

	additions:
	dyable collars! - going to need to get that bloody image generator working for this stuff..
	implement chocobo whistle, should tp the last chocobo you rode to you aslong as you are in the same world ( spawn near the player?)

	implement our own spawn eggs for all chocobos (a wrapper should be good enough)


	 */

	@SidedProxy(clientSide = "uk.co.haxyshideout.chococraft2.proxies.ClientProxy", serverSide = "uk.co.haxyshideout.chococraft2.proxies.ServerProxy")
	public static ServerProxy proxy;

	@Mod.Instance(value = Constants.MODID)
	public static ChocoCraft2 instance;

	@Mod.EventHandler
	public void onPreInit(FMLPreInitializationEvent event) {
		Additions.registerAdditions();
		proxy.registerEntities();
		proxy.registerRenderers();
		proxy.registerWorldGenerators();
		RecipeHandler.registerRecipies();
		PacketRegistry.registerPackets();
	}

	@Mod.EventHandler
	public void onInit(FMLInitializationEvent event) {
		EventHandler eventHandler = new EventHandler();
		MinecraftForge.EVENT_BUS.register(eventHandler);
		FMLCommonHandler.instance().bus().register(eventHandler);
		proxy.registerEntities();
	}

	@Mod.EventHandler
	public void onServerStart(FMLServerStartingEvent event) {
		event.registerServerCommand(new DebugCommand());
	}

}

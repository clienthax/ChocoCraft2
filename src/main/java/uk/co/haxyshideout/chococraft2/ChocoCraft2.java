package uk.co.haxyshideout.chococraft2;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import uk.co.haxyshideout.chococraft2.config.Additions;
import uk.co.haxyshideout.chococraft2.config.Constants;
import uk.co.haxyshideout.chococraft2.config.RecipieHandler;
import uk.co.haxyshideout.chococraft2.events.EventHandler;
import uk.co.haxyshideout.chococraft2.proxys.ServerProxy;

/**
 * Created by clienthax on 12/4/2015.
 */
@Mod(modid = Constants.MODID, name = Constants.MODNAME, version = Constants.MODVERSION)
public class ChocoCraft2 {

	@SidedProxy(clientSide = "uk.co.haxyshideout.chococraft2.proxys.ClientProxy", serverSide = "uk.co.haxyshideout.chococraft2.proxys.ServerProxy")
	public static ServerProxy proxy;

	@Mod.EventHandler
	public void onPreInit(FMLPreInitializationEvent event) {
		Additions.registerAdditions();
		proxy.registerRenderers();
		RecipieHandler.registerRecipies();
	}

	@Mod.EventHandler
	public void onInit(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new EventHandler());
	}

}

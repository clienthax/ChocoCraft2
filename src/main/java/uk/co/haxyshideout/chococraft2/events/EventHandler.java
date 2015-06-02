package uk.co.haxyshideout.chococraft2.events;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import uk.co.haxyshideout.chococraft2.blocks.GysahlStemBlock;
import uk.co.haxyshideout.chococraft2.config.Additions;
import uk.co.haxyshideout.chococraft2.spawner.ChocoboSpawner;

import java.util.List;

/**
 * Created by clienthax on 13/4/2015.
 */
public class EventHandler {

	int spawnTimer = 0;

	@SubscribeEvent
	public void onBoneMealUse(BonemealEvent event) {
		if(event.block.getBlock() != Additions.gysahlStemBlock)
			return;

		//This tells forge we have processed the event
		event.setResult(Event.Result.ALLOW);
		//If the stage is below max, then set the growth to max stage, if its already fully grown, cancel the event (prevent the player from losing bonemeal)
		if((Integer) event.block.getValue(GysahlStemBlock.STAGE) < GysahlStemBlock.MAXSTAGE) {
			((GysahlStemBlock) event.block.getBlock()).setGrowthStage(event.world, event.pos, event.block);
		} else
			event.setCanceled(true);
	}

	@SubscribeEvent
	public void handleSpawning(TickEvent.WorldTickEvent event) {
		if(spawnTimer++ < 50)//TODO could prob be neatened up by using the total world time and % tickdelay
			return;
		spawnTimer = 0;

		for(EntityPlayerMP playerMP : (List<EntityPlayerMP>)MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
			ChocoboSpawner.doChocoboSpawning(playerMP.worldObj, playerMP.getPosition());
		}

	}

}

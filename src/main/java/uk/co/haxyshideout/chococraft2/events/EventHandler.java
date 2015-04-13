package uk.co.haxyshideout.chococraft2.events;

import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import uk.co.haxyshideout.chococraft2.blocks.GysahlStemBlock;
import uk.co.haxyshideout.chococraft2.config.Additions;

/**
 * Created by clienthax on 13/4/2015.
 */
public class EventHandler {

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
}

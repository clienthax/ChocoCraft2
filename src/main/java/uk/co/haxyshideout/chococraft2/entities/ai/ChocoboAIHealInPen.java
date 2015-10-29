package uk.co.haxyshideout.chococraft2.entities.ai;

import net.minecraft.block.BlockCauldron;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import uk.co.haxyshideout.chococraft2.config.Additions;
import uk.co.haxyshideout.chococraft2.entities.EntityChocobo;
import uk.co.haxyshideout.haxylib.utils.RandomHelper;
import uk.co.haxyshideout.haxylib.utils.WorldHelper;

/**
 * Created by clienthax on 20/5/2015.
 */
public class ChocoboAIHealInPen extends EntityAIBase {

	EntityChocobo chocobo;

	public ChocoboAIHealInPen(EntityChocobo chocobo) {
		this.chocobo = chocobo;
		setMutexBits(4);
	}

	@Override
	public void startExecuting() {
		for(IBlockState cauldronState : WorldHelper.getBlockstatesInRangeOfEntity(Blocks.cauldron, chocobo, 5, 0)) {
			if((Integer) cauldronState.getValue(BlockCauldron.LEVEL) == 3) {//cauldron is full
				chocobo.heal(RandomHelper.getRandomInt(3) + 1);
			}
		}
	}

	@Override
	public boolean shouldExecute() {//check the task is run only every 40 ticks and that the chocobo is standing on straw
		if(chocobo.worldObj.getWorldTime() % 40 == 0 && chocobo.getHealth() != chocobo.getMaxHealth())
			if(chocobo.worldObj.getBlockState(chocobo.getPosition()).getBlock() == Additions.strawBlock) {
			return true;
		}
		return false;
	}
}

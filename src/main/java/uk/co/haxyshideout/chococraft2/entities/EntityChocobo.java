package uk.co.haxyshideout.chococraft2.entities;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.world.World;
import uk.co.haxyshideout.chococraft2.config.Constants;

/**
 * Created by clienthax on 14/4/2015.
 */
public class EntityChocobo extends EntityTameable {

	public static enum chocoboColor
	{
		YELLOW,
		GREEN,
		BLUE,
		WHITE,
		BLACK,
		GOLD,
		PINK,
		RED,
		PURPLE
	}

	public EntityChocobo(World world) {
		super(world);
		this.setSize(1.3f, 1.9f);

		((PathNavigateGround)this.getNavigator()).setAvoidsWater(true);
		this.tasks.addTask(0, new EntityAIWander(this, 1.0D));
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		//corresponding to enum.ordinal
		this.dataWatcher.addObject(Constants.dataWatcherVariant, Integer.valueOf(0));
		//0 for no bag, 1 for saddlebag, 2 for pack bag
		this.dataWatcher.addObject(Constants.dataWatcherBagType, Byte.valueOf((byte)0));
	}

	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		return null;
	}

	@Override
	protected String getDeathSound() {
		return "chococraft2:choco_kweh";
	}

	@Override
	protected String getHurtSound() {
		return "chococraft2:choco_kweh";
	}

	@Override
	protected String getLivingSound() {
		if(rand.nextInt(4) == 0)
			return "chococraft2:choco_kweh";

		return null;
	}

	/**
	 * Get number of ticks, at least during which the living entity will be silent.
	 */
	public int getTalkInterval() {
		return 150;
	}



}

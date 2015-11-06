package uk.co.haxyshideout.chococraft2.entities.ai;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import uk.co.haxyshideout.chococraft2.config.Additions;
import uk.co.haxyshideout.chococraft2.entities.EntityChocobo;

public class ChocoboAIMate extends EntityAIBase
{
	private EntityChocobo theAnimal;
	World theWorld;
	private EntityChocobo targetMate;
	/** Delay preventing a baby from spawning immediately when two mate-able animals find each other. */
	int spawnBabyDelay;
	/** The speed the creature moves at during mating behavior. */
	double moveSpeed;

	public ChocoboAIMate(EntityChocobo animal, double speedIn)
	{
		this.theAnimal = animal;
		this.theWorld = animal.worldObj;
		this.moveSpeed = speedIn;
		this.setMutexBits(3);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute()
	{
		if (!this.theAnimal.isInLove())
		{
			return false;
		}
		else
		{
			this.targetMate = this.getNearbyMate();
			return this.targetMate != null;
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean continueExecuting()
	{
		return this.targetMate.isEntityAlive() && this.targetMate.isInLove() && this.spawnBabyDelay < 60;
	}

	/**
	 * Resets the task
	 */
	public void resetTask()
	{
		this.targetMate = null;
		this.spawnBabyDelay = 0;
	}

	/**
	 * Updates the task
	 */
	public void updateTask()
	{
		this.theAnimal.getLookHelper().setLookPositionWithEntity(this.targetMate, 10.0F, (float)this.theAnimal.getVerticalFaceSpeed());
		this.theAnimal.getNavigator().tryMoveToEntityLiving(this.targetMate, this.moveSpeed);
		++this.spawnBabyDelay;

		if (this.spawnBabyDelay >= 60 && this.theAnimal.getDistanceSqToEntity(this.targetMate) < 9.0D)
		{
			this.spawnBaby();
		}
	}

	/**
	 * Loops through nearby animals and finds another animal of the same type that can be mated with. Returns the first
	 * valid mate found.
	 */
	@SuppressWarnings("WhileLoopReplaceableByForEach")
	private EntityChocobo getNearbyMate()
	{
		float f = 8.0F;
		List list = this.theWorld.getEntitiesWithinAABB(this.theAnimal.getClass(), this.theAnimal.getEntityBoundingBox().expand((double)f, (double)f, (double)f));
		double d0 = Double.MAX_VALUE;
		EntityChocobo entityanimal = null;
		Iterator iterator = list.iterator();

		while (iterator.hasNext())
		{
			EntityChocobo entityanimal1 = (EntityChocobo)iterator.next();

			if (this.theAnimal.canMateWith(entityanimal1) && this.theAnimal.getDistanceSqToEntity(entityanimal1) < d0)
			{
				entityanimal = entityanimal1;
				d0 = this.theAnimal.getDistanceSqToEntity(entityanimal1);
			}
		}

		return entityanimal;
	}

	/**
	 * Spawns a baby animal of the same type.
	 */
	private void spawnBaby()
	{
		if(theAnimal.getChocoboColor() == EntityChocobo.ChocoboColor.PURPLE && targetMate.getChocoboColor() == EntityChocobo.ChocoboColor.PURPLE) {
			theAnimal.entityDropItem(new ItemStack(Additions.purpleSpawnEggItem), 0);
			this.theAnimal.setGrowingAge(6000);
			this.targetMate.setGrowingAge(6000);
			this.theAnimal.resetInLove();
			this.targetMate.resetInLove();
			return;
		}

		EntityAgeable entityageable = this.theAnimal.createChild(this.targetMate);

		if (entityageable != null)
		{
			EntityPlayer entityplayer = this.theAnimal.getPlayerInLove();

			if (entityplayer == null && this.targetMate.getPlayerInLove() != null)
			{
				entityplayer = this.targetMate.getPlayerInLove();
			}

			if (entityplayer != null)
			{
				entityplayer.triggerAchievement(StatList.animalsBredStat);
			}

			this.theAnimal.setGrowingAge(6000);
			this.targetMate.setGrowingAge(6000);
			this.theAnimal.resetInLove();
			this.targetMate.resetInLove();
			entityageable.setGrowingAge(-24000);
			entityageable.setLocationAndAngles(this.theAnimal.posX, this.theAnimal.posY, this.theAnimal.posZ, 0.0F, 0.0F);
			this.theWorld.spawnEntityInWorld(entityageable);
			Random random = this.theAnimal.getRNG();

			for (int i = 0; i < 7; ++i)
			{
				double d0 = random.nextGaussian() * 0.02D;
				double d1 = random.nextGaussian() * 0.02D;
				double d2 = random.nextGaussian() * 0.02D;
				this.theWorld.spawnParticle(EnumParticleTypes.HEART, this.theAnimal.posX + (double)(random.nextFloat() * this.theAnimal.width * 2.0F) - (double)this.theAnimal.width, this.theAnimal.posY + 0.5D + (double)(random.nextFloat() * this.theAnimal.height), this.theAnimal.posZ + (double)(random.nextFloat() * this.theAnimal.width * 2.0F) - (double)this.theAnimal.width, d0, d1, d2);
			}

			if (this.theWorld.getGameRules().getGameRuleBooleanValue("doMobLoot"))
			{
				this.theWorld.spawnEntityInWorld(new EntityXPOrb(this.theWorld, this.theAnimal.posX, this.theAnimal.posY, this.theAnimal.posZ, random.nextInt(7) + 1));
			}
		}
	}
}
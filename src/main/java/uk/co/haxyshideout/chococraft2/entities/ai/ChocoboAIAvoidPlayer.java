package uk.co.haxyshideout.chococraft2.entities.ai;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import uk.co.haxyshideout.haxylib.items.GenericArmor;
import uk.co.haxyshideout.haxylib.utils.RandomHelper;

import java.util.List;

/**
 * Created by clienthax on 20/5/2015.
 */
public class ChocoboAIAvoidPlayer extends EntityAIBase {

	public final Predicate canBeSeenSelector = new Predicate()
	{
		public boolean isApplicable(Entity entityIn)
		{
			return entityIn.isEntityAlive() && ChocoboAIAvoidPlayer.this.theEntity.getEntitySenses().canSee(entityIn);
		}
		@Override
		public boolean apply(Object p_apply_1_)
		{
			return this.isApplicable((Entity)p_apply_1_);
		}
	};

	public final Predicate playerSuitSelector = new Predicate()
	{
		public boolean apply(Entity entity)
		{
			if(entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) entity;
				int chance = 0;
				for (ItemStack stack : player.inventory.armorInventory) {
					if(stack != null) {
						if(stack.getItem() instanceof GenericArmor)//lazy way..
							chance += 25;
					}
				}

				return !RandomHelper.getChanceResult(chance);

			}
			return false;
		}
		@Override
		public boolean apply(Object object)
		{
			return this.apply((Entity)object);
		}
	};

	/** The entity we are attached to */
	protected EntityCreature theEntity;
	private double farSpeed;
	private double nearSpeed;
	protected Entity closestLivingEntity;
	private float avoidDistance;
	/** The PathEntity of our entity */
	private PathEntity entityPathEntity;
	/** The PathNavigate of our entity */
	private PathNavigate entityPathNavigate;
	private Predicate avoidTargetSelector;

	public ChocoboAIAvoidPlayer(EntityCreature creature, float searchDistance, double farSpeedIn, double nearSpeedIn)
	{
		this.theEntity = creature;
		this.avoidTargetSelector = playerSuitSelector;
		this.avoidDistance = searchDistance;
		this.farSpeed = farSpeedIn;
		this.nearSpeed = nearSpeedIn;
		this.entityPathNavigate = creature.getNavigator();
		this.setMutexBits(1);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute()
	{
		if(theEntity.worldObj.getWorldTime() % 10 != 0)
			return false;

		List list = this.theEntity.worldObj.getEntitiesInAABBexcluding(this.theEntity, this.theEntity.getEntityBoundingBox().expand((double)this.avoidDistance, 3.0D, (double)this.avoidDistance), Predicates.and(IEntitySelector.NOT_SPECTATING, this.canBeSeenSelector, this.avoidTargetSelector));

		if (list.isEmpty())
		{
			return false;
		}
		else
		{
			this.closestLivingEntity = (Entity)list.get(0);
			Vec3 vec3 = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.theEntity, 16, 7, new Vec3(this.closestLivingEntity.posX, this.closestLivingEntity.posY, this.closestLivingEntity.posZ));

			if (vec3 == null)
			{
				return false;
			}
			else if (this.closestLivingEntity.getDistanceSq(vec3.xCoord, vec3.yCoord, vec3.zCoord) < this.closestLivingEntity.getDistanceSqToEntity(this.theEntity))
			{
				return false;
			}
			else
			{
				this.entityPathEntity = this.entityPathNavigate.getPathToPos(new BlockPos(vec3.xCoord, vec3.yCoord, vec3.zCoord));
				return this.entityPathEntity != null;
			}
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean continueExecuting()
	{
		return !this.entityPathNavigate.noPath();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting()
	{
		this.entityPathNavigate.setPath(this.entityPathEntity, this.farSpeed);
	}

	/**
	 * Resets the task
	 */
	@Override
	public void resetTask()
	{
		this.closestLivingEntity = null;
	}

	/**
	 * Updates the task
	 */
	@Override
	public void updateTask()
	{
		if (this.theEntity.getDistanceSqToEntity(this.closestLivingEntity) < 49.0D)
		{
			this.theEntity.getNavigator().setSpeed(this.nearSpeed);
		}
		else
		{
			this.theEntity.getNavigator().setSpeed(this.farSpeed);
		}
	}


}

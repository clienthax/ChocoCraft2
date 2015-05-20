package uk.co.haxyshideout.chococraft2.entities.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import uk.co.haxyshideout.chococraft2.entities.EntityChocobo;

public class ChocoboAIFollowLure extends EntityAIBase
{
	private EntityChocobo chocobo;
	private EntityLivingBase theOwner;
	World theWorld;
	private double followSpeed;
	private PathNavigate petPathfinder;
	private int field_75343_h;
	float maxDist;
	float minDist;

	public ChocoboAIFollowLure(EntityTameable thePetIn, double followSpeedIn, float minDistIn, float maxDistIn)
	{
		this.chocobo = (EntityChocobo)thePetIn;
		this.theWorld = thePetIn.worldObj;
		this.followSpeed = followSpeedIn;
		this.petPathfinder = thePetIn.getNavigator();
		this.minDist = minDistIn;
		this.maxDist = maxDistIn;
		this.setMutexBits(3);

		if (!(thePetIn.getNavigator() instanceof PathNavigateGround))
		{
			throw new IllegalArgumentException("Unsupported mob type for FollowLureGoal");
		}
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute()
	{
		EntityLivingBase entityLuring = this.chocobo.getEntityLuring();

		if (entityLuring == null)
		{
			return false;
		}
		else if (this.chocobo.getMovementType() != EntityChocobo.MovementType.FOLLOW_LURE)
		{
			return false;
		}
		else if (this.chocobo.getDistanceSqToEntity(entityLuring) < (double)(this.minDist * this.minDist))
		{
			return false;
		}
		else
		{
			this.theOwner = entityLuring;
			return true;
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean continueExecuting()
	{
		return !this.petPathfinder.noPath() && this.chocobo.getDistanceSqToEntity(this.theOwner) > (double)(this.maxDist * this.maxDist) && chocobo.getMovementType() == EntityChocobo.MovementType.FOLLOW_LURE;
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting()
	{
		this.field_75343_h = 0;
		((PathNavigateGround)this.chocobo.getNavigator()).setAvoidsWater(false);
	}

	/**
	 * Resets the task
	 */
	public void resetTask()
	{
		this.theOwner = null;
		this.petPathfinder.clearPathEntity();
		((PathNavigateGround)this.chocobo.getNavigator()).setAvoidsWater(true);
	}

	/**
	 * Updates the task
	 */
	public void updateTask()
	{
		this.chocobo.getLookHelper().setLookPositionWithEntity(this.theOwner, 10.0F, (float)this.chocobo.getVerticalFaceSpeed());

		if (!this.chocobo.isSitting())
		{
			if (--this.field_75343_h <= 0)
			{
				this.field_75343_h = 10;

				if (!this.petPathfinder.tryMoveToEntityLiving(this.theOwner, this.followSpeed))
				{
					if (!this.chocobo.getLeashed())
					{
						if (this.chocobo.getDistanceSqToEntity(this.theOwner) >= 144.0D)
						{
							int i = MathHelper.floor_double(this.theOwner.posX) - 2;
							int j = MathHelper.floor_double(this.theOwner.posZ) - 2;
							int k = MathHelper.floor_double(this.theOwner.getEntityBoundingBox().minY);

							for (int l = 0; l <= 4; ++l)
							{
								for (int i1 = 0; i1 <= 4; ++i1)
								{
									if ((l < 1 || i1 < 1 || l > 3 || i1 > 3) && World.doesBlockHaveSolidTopSurface(this.theWorld, new BlockPos(i + l, k - 1, j + i1)) && !this.theWorld.getBlockState(new BlockPos(i + l, k, j + i1)).getBlock().isFullCube() && !this.theWorld.getBlockState(new BlockPos(i + l, k + 1, j + i1)).getBlock().isFullCube())
									{
										this.chocobo.setLocationAndAngles((double)((float)(i + l) + 0.5F), (double)k, (double)((float)(j + i1) + 0.5F), this.chocobo.rotationYaw, this.chocobo.rotationPitch);
										this.petPathfinder.clearPathEntity();
										return;
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
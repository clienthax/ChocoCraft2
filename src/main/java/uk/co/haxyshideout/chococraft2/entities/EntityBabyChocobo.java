package uk.co.haxyshideout.chococraft2.entities;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import uk.co.haxyshideout.chococraft2.config.Constants;
import uk.co.haxyshideout.chococraft2.entities.EntityChocobo.ChocoboColor;

public class EntityBabyChocobo extends EntityAnimal
{
	public EntityBabyChocobo(World worldIn)
	{
		super(worldIn);
		
		this.tasks.addTask(2, new EntityAIWander(this, this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue()));
		this.tasks.addTask(3, new EntityAISwimming(this));
		this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(5, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityMob.class, true));
	}

	@Override
	public EntityAgeable createChild(EntityAgeable ageable)
	{
		return null;
	}

	public void setColor(ChocoboColor color)
	{
		dataWatcher.updateObject(Constants.dataWatcherVariant, (byte) color.ordinal());
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		super.writeEntityToNBT(tagCompound);
		tagCompound.setByte("Color", (byte) getChocoboColor().ordinal());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompound)
	{
		super.readEntityFromNBT(tagCompound);
		setColor(ChocoboColor.values()[tagCompound.getByte("Color")]);
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();

		// corresponding to enum.ordinal
		this.dataWatcher.addObject(Constants.dataWatcherVariant, (byte) 0);
	}

	public boolean isTamed()
	{
		return false;
	}

	public boolean isMale()
	{
		return true;
	}

	public ChocoboColor getChocoboColor()
	{
		return ChocoboColor.values()[dataWatcher.getWatchableObjectByte(Constants.dataWatcherVariant)];
	}

	@Override
	protected String getDeathSound()
	{
		return "chococraft2:choco_kweh";
	}

	@Override
	protected String getHurtSound()
	{
		return "chococraft2:choco_kweh";
	}

	@Override
	protected String getLivingSound()
	{
		if (rand.nextInt(4) == 0)
			return "chococraft2:choco_kweh";

		return null;
	}
}

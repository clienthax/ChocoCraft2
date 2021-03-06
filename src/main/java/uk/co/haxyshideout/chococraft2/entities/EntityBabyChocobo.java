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
import uk.co.haxyshideout.chococraft2.ChocoCraft2;
import uk.co.haxyshideout.chococraft2.config.Additions;
import uk.co.haxyshideout.chococraft2.config.Constants;
import uk.co.haxyshideout.chococraft2.entities.EntityChocobo.ChocoboColor;

public class EntityBabyChocobo extends EntityAnimal
{
    private int ticksExisted;

	public EntityBabyChocobo(World worldIn)
	{
		super(worldIn);
		this.setSize(0.5f, 0.5f);
		this.tasks.addTask(2, new EntityAIWander(this, this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue()));
		this.tasks.addTask(3, new EntityAISwimming(this));
		this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(5, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityMob.class, true));
	}

	@Override
	public void onLivingUpdate()
    {
        super.onLivingUpdate();
        this.ticksExisted++;
        
        if(this.ticksExisted >= ChocoCraft2.instance.getConfig().getTicksToAdult() && !this.worldObj.isRemote)
        {
	        growUp();
        }
    }

	private void growUp() {
		this.setDead();
		EntityChocobo chocobo = new EntityChocobo(this.worldObj);
		chocobo.setColor(this.getChocoboColor());
		chocobo.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
		this.worldObj.spawnEntityInWorld(chocobo);
	}

	@Override
	public boolean interact(EntityPlayer player)
	{
		if (worldObj.isRemote)// return if client
			return false;

		if (player.getHeldItem() == null)// Make sure the player is holding something for the following checks
			return false;

		if (player.getHeldItem().getItem() == Additions.gysahlCakeItem)
		{
			this.consumeItemFromStack(player, player.inventory.getCurrentItem());
			growUp();
		}

		return true;
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

package uk.co.haxyshideout.chococraft2.entities;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.AnimalChest;
import net.minecraft.inventory.IInvBasic;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import uk.co.haxyshideout.chococraft2.ChocoCraft2;
import uk.co.haxyshideout.chococraft2.config.Additions;
import uk.co.haxyshideout.chococraft2.config.Constants;
import uk.co.haxyshideout.chococraft2.entities.ai.ChocoboAIAvoidPlayer;
import uk.co.haxyshideout.chococraft2.entities.ai.ChocoboAIFollowLure;
import uk.co.haxyshideout.chococraft2.entities.ai.ChocoboAIFollowOwner;
import uk.co.haxyshideout.chococraft2.entities.ai.ChocoboAIHealInPen;
import uk.co.haxyshideout.chococraft2.entities.ai.ChocoboAIMate;
import uk.co.haxyshideout.chococraft2.entities.ai.ChocoboAIWatchPlayer;
import uk.co.haxyshideout.chococraft2.entities.breeding.Breeding;
import uk.co.haxyshideout.haxylib.utils.InventoryHelper;
import uk.co.haxyshideout.haxylib.utils.RandomHelper;
import uk.co.haxyshideout.haxylib.utils.WorldHelper;

/**
 * Created by clienthax on 14/4/2015.
 */
public class EntityChocobo extends EntityTameable implements IInvBasic
{
	public float wingRotation;
	public float destPos;
	private float wingRotDelta;
	private EntityPlayerMP entityLuring = null;
	private ChocoboAIAvoidPlayer chocoboAIAvoidPlayer;
	private ChocoboAIHealInPen chocoboAIHealInPen;
	private AnimalChest chocoboChest;
	private int ticksUntilNextFeatherDrop;
	private final RiderState riderState;
	public boolean fedGoldenGyshal = false;

	public enum ChocoboColor
	{
		YELLOW, GREEN, BLUE, WHITE, BLACK, GOLD, PINK, RED, PURPLE
	}

	public enum BagType
	{
		NONE, SADDLE, PACK
	}

	public enum MovementType
	{
		WANDER, FOLLOW_OWNER, STANDSTILL, FOLLOW_LURE
	}

	public EntityChocobo(World world)
	{
		super(world);
		this.setSize(1.3f, 2.3f);
		setMale(world.rand.nextBoolean());
		setCustomNameTag(DefaultNames.getRandomName(isMale()));
		resetFeatherDropTime();
		riderState = new RiderState();
		((PathNavigateGround) this.getNavigator()).setCanSwim(false);
		this.tasks.addTask(1, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(1, new ChocoboAIFollowOwner(this, 1.0D, 5.0F, 5.0F));// follow speed 1, min and max 5
		this.tasks.addTask(1, new ChocoboAIFollowLure(this, 1.0D, 5.0F, 5.0F));
		this.tasks.addTask(1, new ChocoboAIWatchPlayer(this, EntityPlayer.class, 5));
		this.tasks.addTask(2, new ChocoboAIMate(this, 1.0D));
		this.tasks.addTask(1, new EntityAISwimming(this));

		initChest();

		// Default init
		if (!world.isRemote)
		{
			if (WorldHelper.isHellWorld(world))
				setColor(ChocoboColor.PURPLE);
			else
				setColor(ChocoboColor.YELLOW);
		}

		this.isImmuneToFire = getAbilityInfo().isImmuneToFire();
	}

	public void mountEntity(Entity entityIn)
	{
		if (this.worldObj.isRemote)
		{
			if (Minecraft.getMinecraft().thePlayer.getUniqueID().equals(entityIn.getUniqueID()))
			{
				Minecraft.getMinecraft().gameSettings.thirdPersonView = 1;
			}
		}
	}

	public void setStats()
	{
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.getAbilityInfo().getMaxHP());// set max health
		setHealth(getMaxHealth());// reset the hp to max
		onGroundSpeedFactor = this.getAbilityInfo().getLandSpeed() / 100f;
		this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(onGroundSpeedFactor);
	}

	public RiderState getRiderState()
	{
		return riderState;
	}

	@Override
	public void moveEntityWithHeading(float strafe, float forward)
	{
		// TODO some point in future, move this to its own ai class
		if (this.getControllingPassenger() != null && this.getControllingPassenger() instanceof EntityLivingBase)
		{
			this.prevRotationYaw = this.rotationYaw = this.getControllingPassenger().rotationYaw;
			this.rotationPitch = this.getControllingPassenger().rotationPitch * 0.5F;
			this.setRotation(this.rotationYaw, this.rotationPitch);
			this.rotationYawHead = this.renderYawOffset = this.rotationYaw;
			strafe = ((EntityLivingBase) this.getControllingPassenger()).moveStrafing * 0.5F;
			forward = ((EntityLivingBase) this.getControllingPassenger()).moveForward;

			if (forward <= 0.0F)
			{
				forward *= 0.25F;
			}

			if (isInWater() && this.getAbilityInfo().canWalkOnWater())
			{
				motionY = 0.4d;
				moveFlying(strafe, forward, 100 / getAbilityInfo().getWaterSpeed());
				setJumping(true);
			}

			if (this.riderState.isJumping() && this.getAbilityInfo().getCanFly())
			{
				this.isJumping = true;
				this.jump();
				moveFlying(strafe, forward, 100 / getAbilityInfo().getAirbornSpeed());
			}
			else if (this.riderState.isJumping() && !this.isJumping && this.onGround)
			{
				this.motionY += 0.75;
				this.riderState.setJumping(false);
				this.isJumping = true;
			}
			if (!this.worldObj.isRemote)
			{
				this.setAIMoveSpeed((float) this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
				super.moveEntityWithHeading(strafe, forward);
			}

			this.prevLimbSwingAmount = this.limbSwingAmount;
			double d1 = this.posX - this.prevPosX;
			double d0 = this.posZ - this.prevPosZ;
			float f4 = MathHelper.sqrt_double(d1 * d1 + d0 * d0) * 4.0F;

			if (f4 > 1.0F)
			{
				f4 = 1.0F;
			}

			this.limbSwingAmount += (f4 - this.limbSwingAmount) * 0.4F;
			this.limbSwing += this.limbSwingAmount;
		}
		else
		{
			super.moveEntityWithHeading(strafe, forward);
		}
	}

	@Override
	public void setupTamedAI()
	{
		if (chocoboAIAvoidPlayer == null)
		{
			chocoboAIAvoidPlayer = new ChocoboAIAvoidPlayer(this, 10.0F, 1.0D, 1.2D);
		}
		if (chocoboAIHealInPen == null)
		{
			chocoboAIHealInPen = new ChocoboAIHealInPen(this);
		}

		tasks.removeTask(chocoboAIAvoidPlayer);
		tasks.removeTask(chocoboAIHealInPen);

		if (isTamed())
		{
			tasks.addTask(4, chocoboAIHealInPen);
		}
		else
		{
			tasks.addTask(5, chocoboAIAvoidPlayer);
		}
	}

	@Override
	public boolean isBreedingItem(ItemStack itemStack)
	{
		return itemStack.getItem() == Additions.gysahlLoverlyItem || itemStack.getItem() == Additions.gysahlGoldenItem;
	}

	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();

		if (this.getAbilityInfo().canClimb() || this.getControllingPassenger() != null)
		{
			this.stepHeight = 1.0F;
		}

		this.fallDistance = 0f;

		// Wing rotations, control packet, client side
		if (worldObj.isRemote)
		{
			// Client side
			if (this.getControllingPassenger() != null && this.getControllingPassenger() instanceof EntityPlayer)
			{
				if (Minecraft.getMinecraft().thePlayer.getUniqueID().equals(this.getControllingPassenger().getUniqueID()) && Keyboard.isKeyDown(Keyboard.KEY_SPACE))
				{
					this.riderState.setJumping(true);
				}

				ChocoCraft2.proxy.updateRiderState((EntityPlayer) this.getControllingPassenger());
			}

			this.destPos += (double) (this.onGround ? -1 : 4) * 0.3D;
			this.destPos = MathHelper.clamp_float(destPos, 0f, 1f);

			if (!this.onGround)
			{
				this.wingRotDelta = Math.min(wingRotation, 1f);
			}

			this.wingRotDelta *= 0.9D;

			if (!this.onGround && this.motionY < 0.0D)
			{
				this.motionY *= 0.8D;
			}
			this.wingRotation += this.wingRotDelta * 2.0F;

			return;// Rest of code should be run on server only
		}

		// Drop a chocobo feather randomly
		if (--ticksUntilNextFeatherDrop <= 0)
		{
			if (RandomHelper.getChanceResult(ChocoCraft2.instance.getConfig().getChangeForFeather()))
				entityDropItem(new ItemStack(Additions.chocoboFeatherItem), 0);
			resetFeatherDropTime();
		}

		if (this.getControllingPassenger() != null)
		{
			// TODO is this needed?
			rotationPitch = 0;
			rotationYaw = this.getControllingPassenger().rotationYaw;
			prevRotationYaw = this.getControllingPassenger().rotationYaw;
			setRotation(rotationYaw, rotationPitch);
		}

	}

	public void resetFeatherDropTime()
	{
		ticksUntilNextFeatherDrop = rand.nextInt(ChocoCraft2.instance.getConfig().getTicksUntilNextFeather());
	}

	/*
	 * DataWatcher
	 */

	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		super.writeEntityToNBT(tagCompound);
		tagCompound.setByte("Color", (byte) getChocoboColor().ordinal());
		tagCompound.setByte("BagType", (byte) getBagType().ordinal());
		tagCompound.setBoolean("Saddled", isSaddled());
		tagCompound.setBoolean("Male", isMale());
		tagCompound.setByte("MovementType", (byte) getMovementType().ordinal());

		if (getBagType() != BagType.NONE)
		{
			NBTTagList nbttaglist = new NBTTagList();

			for (int i = 0; i < chocoboChest.getSizeInventory(); ++i)
			{
				ItemStack itemstack = chocoboChest.getStackInSlot(i);

				if (itemstack != null)
				{
					NBTTagCompound nbttagcompound1 = new NBTTagCompound();
					nbttagcompound1.setByte("Slot", (byte) i);
					itemstack.writeToNBT(nbttagcompound1);
					nbttaglist.appendTag(nbttagcompound1);
				}
			}

			tagCompound.setTag("Items", nbttaglist);
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompound)
	{
		super.readEntityFromNBT(tagCompound);
		setColor(ChocoboColor.values()[tagCompound.getByte("Color")]);
		setBag(BagType.values()[tagCompound.getByte("BagType")]);
		setSaddled(tagCompound.getBoolean("Saddled"));
		setMale(tagCompound.getBoolean("Male"));
		setMovementType(MovementType.values()[tagCompound.getByte("MovementType")]);

		if (getBagType() != BagType.NONE)
		{
			NBTTagList nbttaglist = tagCompound.getTagList("Items", 10);
			initChest();

			for (int i = 0; i < nbttaglist.tagCount(); ++i)
			{
				NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
				int j = nbttagcompound1.getByte("Slot") & 255;

				if (j >= 0 && j < chocoboChest.getSizeInventory())
				{
					chocoboChest.setInventorySlotContents(j, ItemStack.loadItemStackFromNBT(nbttagcompound1));
				}
			}
		}

	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		// corresponding to enum.ordinal
		this.dataWatcher.register(Constants.dataWatcherVariant, (byte) 0);
		// 0 for no bag, 1 for saddlebag, 2 for pack bag
		this.dataWatcher.register(Constants.dataWatcherBagType, (byte) 0);
		// 1 if saddled, 0 if not
		this.dataWatcher.register(Constants.dataWatcherSaddled, (byte) 0);
		// 1 if male, 0 if not
		this.dataWatcher.register(Constants.dataWatcherMale, (byte) 0);
		// 0 if wandering, 1 if following owner, 2 staying still
		this.dataWatcher.register(Constants.dataWatcherFollowingOwner, (byte) 0);
	}

	public void setColor(ChocoboColor color)
	{
		dataWatcher.set(Constants.dataWatcherVariant, (byte) color.ordinal());
		setStats();
	}

	public ChocoboColor getChocoboColor()
	{
		return ChocoboColor.values()[dataWatcher.get(Constants.dataWatcherVariant)];
	}

	public void setBag(BagType bag)
	{
		dataWatcher.set(Constants.dataWatcherBagType, (byte) bag.ordinal());
		initChest();
	}

	public BagType getBagType()
	{
		return BagType.values()[dataWatcher.get(Constants.dataWatcherBagType)];
	}

	public void setSaddled(boolean saddled)
	{
		dataWatcher.set(Constants.dataWatcherSaddled, (byte) (saddled ? 1 : 0));
	}

	public boolean isSaddled()
	{
		return dataWatcher.get(Constants.dataWatcherSaddled) == 1;
	}

	public void setMale(boolean isMale)
	{
		dataWatcher.set(Constants.dataWatcherMale, (byte) (isMale ? 1 : 0));
	}

	public boolean isMale()
	{
		return dataWatcher.get(Constants.dataWatcherMale) == 1;
	}

	public void setMovementType(MovementType movementType)
	{
		dataWatcher.set(Constants.dataWatcherFollowingOwner, (byte) movementType.ordinal());
	}

	public MovementType getMovementType()
	{
		return MovementType.values()[dataWatcher.get(Constants.dataWatcherFollowingOwner)];
	}

	/*
	 * End of dataWatcher
	 */

	@Override
	public EntityAgeable createChild(EntityAgeable otherParent)
	{
		EntityBabyChocobo entity = new EntityBabyChocobo(otherParent.worldObj);
		entity.setColor(Breeding.getColour(this, (EntityChocobo) otherParent));
		// Reset golden status
		this.fedGoldenGyshal = false;
		((EntityChocobo) otherParent).fedGoldenGyshal = false;
		return entity;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return new SoundEvent(new ResourceLocation("chococraft2:choco_kweh"));
	}

	@Override
	protected SoundEvent getHurtSound()
	{
		return new SoundEvent(new ResourceLocation("chococraft2:choco_kweh"));
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		if (rand.nextInt(4) == 0)
			return new SoundEvent(new ResourceLocation("chococraft2:choco_kweh"));

		return null;
	}

	@Override
	public void heal(float healAmount)
	{
		super.heal(healAmount);
		((WorldServer) worldObj).spawnParticle(EnumParticleTypes.HEART, false, posX, posY + 2.5, posZ, 3, 0.3d, 0, 0.3d, 1);
	}

	@Override
	public double getMountedYOffset()
	{
		return 1.65D;
	}

	@Override
	public boolean shouldRiderFaceForward(EntityPlayer player)
	{
		return true;
	}

	/**
	 * Get number of ticks, at least during which the living entity will be silent.
	 */
	@Override
	public int getTalkInterval()
	{
		return 150;
	}

	/**
	 * Only show the name tag if the chocobo is tamed and not being ridden
	 */
	@Override
	public boolean getAlwaysRenderNameTagForRender()
	{
		return (isTamed() && this.getControllingPassenger() == null);
	}

	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand, ItemStack stack)
	{
		if (!worldObj.isRemote)
		{
			if (player.getHeldItem(hand) != null && player.getHeldItem(hand).getItem() == Additions.chocopediaItem && isTamed() && getOwner() == player)
			{
				ChocoCraft2.proxy.openChocopedia(this);
				return true;
			}
		}

		if (worldObj.isRemote)// return if client
			return false;

		if (player.getHeldItem(hand) == null && isSaddled() && !player.isSneaking())
		{// If the player is not holding anything and the chocobo is saddled, mount the chocobo
			player.startRiding(this);
			this.mountEntity(player);
			return true;
		}

		if (player.isSneaking() && getBagType() != BagType.NONE)
		{
			player.displayGUIChest(chocoboChest);
		}

		if (player.getHeldItem(hand) == null)// Make sure the player is holding something for the following checks
			return false;

		if (player.getHeldItem(hand).getItem() == Additions.gysahlGreenItem)
		{// random chance of taming + random healing amount
			if (!isTamed())
			{
				this.consumeItemFromStack(player, player.inventory.getCurrentItem());
				player.addChatComponentMessage(new TextComponentString("You attempt to tame the wild Chocobo"));
				if (RandomHelper.getChanceResult(10))
				{// Successfull tame
					setOwnerId(player.getUniqueID());
					setTamed(true);
					InventoryHelper.giveIfMissing(new ItemStack(Additions.chocopediaItem), (EntityPlayerMP) player);
					player.addChatComponentMessage(new TextComponentString("You tamed the Chocobo!"));
				}
			}
			else if (isTamed())
			{
				if (getHealth() != getMaxHealth())
				{
					this.consumeItemFromStack(player, player.inventory.getCurrentItem());
					heal(RandomHelper.getRandomInt(5));
				}
				else
				{
					player.addChatComponentMessage(new TextComponentString("This Chocobo is already at full health!"));
				}
			}
			return true;
		}

		/*
		 * Follow the player who clicked the entity if not tamed, if the chocobo is tamed, verify this is the owner, and then follow, if the entity is already following the player, stop following them.
		 */
		if (player.getHeldItem(hand).getItem() == Additions.chocoboFeatherItem)
		{
			if (getMovementType() == MovementType.FOLLOW_LURE && entityLuring != null && entityLuring == player)
			{
				setMovementType(MovementType.STANDSTILL);
				entityLuring = null;
				return true;
			}
			if ((isTamed() && getOwnerId().equals(player.getUniqueID().toString())) || !isTamed())
			{
				setMovementType(MovementType.FOLLOW_LURE);
				entityLuring = (EntityPlayerMP) player;
			}
			return true;
		}

		if (!isTamed() || getOwner() != player)// Return if the chocobo is not tamed, as the following require that the chocobo is tamed. and that they are being used by the owner
			return false;

		if (player.getHeldItem(hand).getItem() == Additions.gysahlGoldenItem)
		{
			this.consumeItemFromStack(player, player.inventory.getCurrentItem());
			this.fedGoldenGyshal = true;
			this.setInLove(player);
		}
		else if (player.getHeldItem(hand).getItem() == Additions.gysahlLoverlyItem)
		{
			this.consumeItemFromStack(player, player.inventory.getCurrentItem());
			this.setInLove(player);
		}

		if (player.getHeldItem(hand).getItem() == Additions.chocoboSaddleItem && !isSaddled())
		{
			this.consumeItemFromStack(player, player.inventory.getCurrentItem());
			// if the player is holding a saddle and the chocobo is not saddled, saddle the chocobo
			player.addChatComponentMessage(new TextComponentString("You put saddle on Chocobo"));// TODO lang
			setSaddled(true);
			return true;
		}

		if (player.getHeldItem(hand).getItem() == Additions.chocoboSaddleBagItem && getBagType() == BagType.NONE && isSaddled())
		{// holding a saddle bag and no bag on chocobo, chocobo needs to be saddled
			this.consumeItemFromStack(player, player.inventory.getCurrentItem());
			setBag(BagType.SADDLE);
			return true;
		}

		if (player.getHeldItem(hand).getItem() == Additions.chocoboPackBagItem && getBagType() == BagType.NONE)
		{// holding a pack bag and no bag on chocobo, remove the saddle if the chocobo is saddled
			this.consumeItemFromStack(player, player.inventory.getCurrentItem());
			setBag(BagType.PACK);
			return true;
		}

		if (getChocoboColor() == ChocoboColor.GOLD && player.getHeldItem(hand).getItem() == Additions.gysahlRedItem)
		{
			this.consumeItemFromStack(player, player.inventory.getCurrentItem());
			setColor(ChocoboColor.RED);
		}
		if (getChocoboColor() == ChocoboColor.GOLD && player.getHeldItem(hand).getItem() == Additions.gysahlPinkItem)
		{
			this.consumeItemFromStack(player, player.inventory.getCurrentItem());
			setColor(ChocoboColor.PINK);
		}
		if (player.getHeldItem(hand).getItem() == Additions.chocoboWhistleItem)
		{
			NBTTagCompound tagCompound = new NBTTagCompound();
			tagCompound.setString("LinkedChocoboUUID", this.getUniqueID().toString());
			player.getHeldItem(hand).setTagCompound(tagCompound);
			player.getHeldItem(hand).setStackDisplayName(this.getName() + "'s Whistle");
			player.addChatComponentMessage(new TextComponentString("You linked the whistle to " + this.getName()));
		}

		return false;
	}

	@Override
	public boolean canMateWith(EntityAnimal otherAnimal)
	{// Check that its a chocobo and that its the right sex
		return otherAnimal != this && (otherAnimal.getClass() == this.getClass() && this.isInLove() && otherAnimal.isInLove() && ((EntityChocobo) otherAnimal).isMale() != this.isMale());
	}

	@Override
	public float getJumpUpwardsMotion()
	{
		return 0.5f;
	}

	public EntityPlayerMP getEntityLuring()
	{
		return entityLuring;
	}

	@Override
	protected boolean isMovementBlocked()
	{
		return getMovementType() == MovementType.STANDSTILL && this.getControllingPassenger() == null;
	}

	@Override
	public void onDeath(DamageSource cause)
	{
		super.onDeath(cause);
		dropGear(null);
		for (int i = 0; i < rand.nextInt(3); i++)
			entityDropItem(new ItemStack(Additions.chocoboFeatherItem), 0);
	}

	// Inv stuff
	public void initChest()
	{
		AnimalChest animalChest = chocoboChest;
		chocoboChest = new AnimalChest("ChocoboChest", getChestSize());
		chocoboChest.setCustomName(getCustomNameTag());

		if (animalChest != null)
		{
			animalChest.removeInventoryChangeListener(this);
			int i = Math.min(animalChest.getSizeInventory(), this.chocoboChest.getSizeInventory());
			for (int j = 0; j < i; ++j)
			{
				ItemStack itemstack = animalChest.getStackInSlot(j);
				if (itemstack != null)
				{
					this.chocoboChest.setInventorySlotContents(j, itemstack.copy());
				}
			}
		}
		this.chocoboChest.addInventoryChangeListener(this);
	}

	public int getChestSize()
	{
		if (getBagType() == BagType.NONE)
			return 0;
		if (getBagType() == BagType.PACK)
			return 54;
		if (getBagType() == BagType.SADDLE)
			return 27;
		return 0;
	}

	@Override
	public void onInventoryChanged(InventoryBasic inventoryBasic)
	{

	}

	public void dropGear(@Nullable EntityPlayerMP player)
	{
		if (worldObj.isRemote)
			return;

		if (chocoboChest != null)
		{
			for (int slot = 0; slot < chocoboChest.getSizeInventory(); slot++)
			{
				ItemStack itemStack = chocoboChest.getStackInSlot(slot);
				if (itemStack != null)
				{
					if (player != null)
						InventoryHelper.giveOrDropStack(itemStack, player);
					else
						entityDropItem(itemStack, 0);
				}
			}
		}
		if (isSaddled())
		{
			setSaddled(false);
			if (player != null)
				InventoryHelper.giveOrDropStack(new ItemStack(Additions.chocoboSaddleItem), player);
			else
				entityDropItem(new ItemStack(Additions.chocoboSaddleItem), 0);
		}
		if (getBagType() == EntityChocobo.BagType.SADDLE)
		{
			setBag(EntityChocobo.BagType.NONE);
			if (player != null)
				InventoryHelper.giveOrDropStack(new ItemStack(Additions.chocoboSaddleBagItem), player);
			else
				entityDropItem(new ItemStack(Additions.chocoboSaddleBagItem), 0);
		}
		if (getBagType() == EntityChocobo.BagType.PACK)
		{
			setBag(EntityChocobo.BagType.NONE);
			if (player != null)
				InventoryHelper.giveOrDropStack(new ItemStack(Additions.chocoboPackBagItem), player);
			else
				entityDropItem(new ItemStack(Additions.chocoboPackBagItem), 0);
		}
	}

	public ChocoboAbilityInfo getAbilityInfo()
	{
		return ChocoboAbilityInfo.getAbilityInfo(this.getChocoboColor());
	}
}

package uk.co.haxyshideout.chococraft2.config;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;

/**
 * Created by clienthax on 12/4/2015.
 */
public class Constants
{
	public static final String MODID = "chococraft2";
	public static final String MODNAME = "ChocoCraft2";
	public static final String MODVERSION = "0.0.2";

	public static final DataParameter<Byte> dataWatcherVariant = EntityDataManager.<Byte> createKey(EntityAgeable.class, DataSerializers.BYTE);
	public static final DataParameter<Byte> dataWatcherBagType = EntityDataManager.<Byte> createKey(EntityAgeable.class, DataSerializers.BYTE);
	public static final DataParameter<Byte> dataWatcherSaddled = EntityDataManager.<Byte> createKey(EntityAgeable.class, DataSerializers.BYTE);
	public static final DataParameter<Byte> dataWatcherMale = EntityDataManager.<Byte> createKey(EntityAgeable.class, DataSerializers.BYTE);
	public static final DataParameter<Byte> dataWatcherFollowingOwner = EntityDataManager.<Byte> createKey(EntityAgeable.class, DataSerializers.BYTE);
}

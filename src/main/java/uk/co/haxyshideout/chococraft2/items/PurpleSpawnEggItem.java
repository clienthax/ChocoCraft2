package uk.co.haxyshideout.chococraft2.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import uk.co.haxyshideout.haxylib.items.GenericItem;
import uk.co.haxyshideout.haxylib.utils.RandomHelper;

/**
 * Created by clienthax on 2/6/2015.
 */
public class PurpleSpawnEggItem extends GenericItem {

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {

		if(world.isRemote)
			return itemstack;

		if(!player.capabilities.isCreativeMode)
			itemstack.stackSize--;

		world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (RandomHelper.random.nextFloat() * 0.4f + 0.8f));
		//TODO spawn baby purple chicibo

		return itemstack;
	}

}

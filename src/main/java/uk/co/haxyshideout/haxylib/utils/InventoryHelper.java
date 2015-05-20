package uk.co.haxyshideout.haxylib.utils;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

/**
 * Created by clienthax on 20/5/2015.
 */
public class InventoryHelper {

	public static void giveOrDropStack(ItemStack stack, EntityPlayerMP player) {//Give the item to the player, if fails, drops the item on ground at player
		boolean addedToPlayer = player.inventory.addItemStackToInventory(stack);
		if(addedToPlayer)
			return;

		EntityItem entityItem = new EntityItem(player.worldObj, player.posX, player.posY, player.posZ, stack);
		player.worldObj.spawnEntityInWorld(entityItem);
	}

}

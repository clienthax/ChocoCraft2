package uk.co.haxyshideout.chococraft2.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import uk.co.haxyshideout.chococraft2.entities.EntityChocobo;
import uk.co.haxyshideout.haxylib.items.GenericItem;

import java.util.UUID;

public class ChocoboWhistleItem extends GenericItem {

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
		if (world.isRemote)// return if client
			return itemstack;

		itemstack = super.onItemRightClick(itemstack, world, player);

		if(!itemstack.hasTagCompound() || !itemstack.getTagCompound().hasKey("LinkedChocoboUUID"))
			return itemstack;

		UUID chocoboUUID = UUID.fromString(itemstack.getTagCompound().getString("LinkedChocoboUUID"));
		Entity entity = MinecraftServer.getServer().getEntityFromUuid(chocoboUUID);
		if(entity == null) {
			player.addChatComponentMessage(new ChatComponentText("Unable to find linked chocobo"));
			return itemstack;
		}

		if(entity instanceof EntityChocobo) {
			if(entity.worldObj != player.worldObj) {
				player.addChatComponentMessage(new ChatComponentText("The chocobo is not in the same world as you"));
				return itemstack;
				//	MinecraftServer.getServer().getConfigurationManager().transferEntityToWorld(entity, entity.dimension, (WorldServer)entity.getEntityWorld(), (WorldServer)player.worldObj);
			}
			entity.setLocationAndAngles(player.posX, player.posY, player.posZ, 0, 0);
			player.addChatComponentMessage(new ChatComponentText("You called "+entity.getCommandSenderName()+" to you"));
		}

		return itemstack;
	}
}

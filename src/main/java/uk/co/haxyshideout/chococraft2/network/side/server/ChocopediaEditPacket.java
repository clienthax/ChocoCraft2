package uk.co.haxyshideout.chococraft2.network.side.server;

import com.mojang.authlib.GameProfile;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import uk.co.haxyshideout.chococraft2.client.gui.ChocopediaGui;
import uk.co.haxyshideout.chococraft2.entities.EntityChocobo;

import java.util.UUID;

/**
 * Created by clienthax on 5/5/2015.
 */
public class ChocopediaEditPacket implements IMessage {

	UUID entityID;
	String chocoboName;
	EntityChocobo.MovementType movementType;
	String ownerName;

	public ChocopediaEditPacket() {}

	public ChocopediaEditPacket(ChocopediaGui chocopediaGui) {
		entityID = chocopediaGui.chocobo.getUniqueID();
		chocoboName = chocopediaGui.name;
		movementType = chocopediaGui.movementType;
		ownerName = chocopediaGui.owner;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		entityID = UUID.fromString(ByteBufUtils.readUTF8String(buf));
		chocoboName = ByteBufUtils.readUTF8String(buf);
		movementType = EntityChocobo.MovementType.values()[buf.readByte()];
		ownerName = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, entityID.toString());
		ByteBufUtils.writeUTF8String(buf, chocoboName);
		buf.writeByte(movementType.ordinal());
		ByteBufUtils.writeUTF8String(buf, ownerName);
	}

	public static class Handler implements IMessageHandler<ChocopediaEditPacket, IMessage> {

		@Override
		public IMessage onMessage(ChocopediaEditPacket message, MessageContext ctx) {
			Entity entity = MinecraftServer.getServer().getEntityFromUuid(message.entityID);
			if (entity != null && entity instanceof EntityChocobo) {
				EntityChocobo chocobo = (EntityChocobo) entity;
				EntityPlayerMP player = ctx.getServerHandler().playerEntity;
				if (player == chocobo.getOwner()) {//Verify that the person who sent the packet is the owner of the chocobo
					chocobo.setCustomNameTag(message.chocoboName);
					chocobo.setMovementType(message.movementType);
					GameProfile ownerProfile = MinecraftServer.getServer().getPlayerProfileCache().getGameProfileForUsername(message.ownerName);
					if(ownerProfile != null) {
						chocobo.setOwnerId(ownerProfile.getId().toString());
					} else {
						ctx.getServerHandler().playerEntity.addChatComponentMessage(new ChatComponentText("Unable to find owner by that name, not applying new owner!"));
					}
				}
			}

			return null;
		}
	}

}

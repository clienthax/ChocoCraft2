package uk.co.haxyshideout.chococraft2.network.side.server;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import uk.co.haxyshideout.chococraft2.entities.EntityChocobo;

import java.util.UUID;

/**
 * Created by clienthax on 5/5/2015.
 */
public class DropGearPacket implements IMessage {

	public DropGearPacket(){}

	UUID entityID;

	public DropGearPacket(EntityChocobo chocobo) {
		entityID = chocobo.getUniqueID();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		entityID = UUID.fromString(ByteBufUtils.readUTF8String(buf));
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, entityID.toString());
	}

	public static class Handler implements IMessageHandler<DropGearPacket, IMessage> {
		@Override
		public IMessage onMessage(DropGearPacket message, MessageContext ctx) {
			Entity entity = MinecraftServer.getServer().getEntityFromUuid(message.entityID);
			if(entity != null && entity instanceof EntityChocobo) {
				EntityChocobo chocobo = (EntityChocobo) entity;
				EntityPlayerMP player = ctx.getServerHandler().playerEntity;
				if(player == chocobo.getOwner()) {//Verify that the person who sent the packet is the owner of the chocobo
					chocobo.dropGear(ctx.getServerHandler().playerEntity);
				}
			}

			return null;
		}

	}


}

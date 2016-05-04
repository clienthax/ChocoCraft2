package uk.co.haxyshideout.chococraft2.network.side.server;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import uk.co.haxyshideout.chococraft2.entities.EntityChocobo;
import uk.co.haxyshideout.chococraft2.entities.RiderState;

/**
 * Created by clienthax on 8/6/2015.
 */
public class RiderStateUpdatePacket implements IMessage {

	RiderState riderState;

	public RiderStateUpdatePacket() {}

	public RiderStateUpdatePacket(EntityChocobo riddenEntity) {//TODO make interface for controllable entities for haxylib, edit this to take that interface etc
		riderState = riddenEntity.getRiderState();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		riderState = new RiderState();
		riderState.setJumping(buf.readBoolean());
		riderState.setSneaking(buf.readBoolean());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(riderState.isJumping());
		buf.writeBoolean(riderState.isSneaking());
	}

	public static class Handler implements IMessageHandler<RiderStateUpdatePacket, IMessage> {
		@Override
		public IMessage onMessage(RiderStateUpdatePacket message, MessageContext ctx) {
			Entity entity = ctx.getServerHandler().playerEntity.getRidingEntity();
			if(!(entity instanceof EntityChocobo))
				return null;

			EntityChocobo chocobo = (EntityChocobo) entity;
			chocobo.getRiderState().updateState(message.riderState);

			return null;
		}

	}

}

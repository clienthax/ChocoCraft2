package uk.co.haxyshideout.chococraft2.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import uk.co.haxyshideout.chococraft2.config.Constants;
import uk.co.haxyshideout.chococraft2.network.side.server.ChocopediaEditPacket;
import uk.co.haxyshideout.chococraft2.network.side.server.DropGearPacket;
import uk.co.haxyshideout.chococraft2.network.side.server.RiderStateUpdatePacket;

/**
 * Created by clienthax on 5/5/2015.
 */
public class PacketRegistry {

	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Constants.MODID);

	public static void registerPackets() {
		int id = 0;
		INSTANCE.registerMessage(DropGearPacket.Handler.class, DropGearPacket.class, id++, Side.SERVER);
		INSTANCE.registerMessage(ChocopediaEditPacket.Handler.class, ChocopediaEditPacket.class, id++, Side.SERVER);
		INSTANCE.registerMessage(RiderStateUpdatePacket.Handler.class, RiderStateUpdatePacket.class, id++, Side.SERVER);
	}

}

package uk.co.haxyshideout.chococraft2.proxies;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import uk.co.haxyshideout.chococraft2.client.gui.ChocopediaGui;
import uk.co.haxyshideout.chococraft2.config.Additions;
import uk.co.haxyshideout.chococraft2.config.Constants;
import uk.co.haxyshideout.chococraft2.entities.EntityChocobo;
import uk.co.haxyshideout.chococraft2.entities.RiderState;
import uk.co.haxyshideout.chococraft2.entities.models.ModelChocobo;
import uk.co.haxyshideout.chococraft2.entities.renderer.ChocoboRenderer;
import uk.co.haxyshideout.chococraft2.network.PacketRegistry;
import uk.co.haxyshideout.chococraft2.network.side.server.RiderStateUpdatePacket;
import uk.co.haxyshideout.haxylib.utils.RegistryHelper;

/**
 * Created by clienthax on 12/4/2015.
 */
public class ClientProxy extends ServerProxy
{

	private RiderState localRiderState = new RiderState();

	@Override
	public void registerRenderers()
	{
		RegistryHelper.registerRenderers(Additions.class, Constants.MODID);
	}

	@Override
	public void registerEntities()
	{
		super.registerEntities();
		// Have to register renderers in here because mojang is stupid
		RenderManager manager = Minecraft.getMinecraft().getRenderManager();
		RenderingRegistry.registerEntityRenderingHandler(EntityChocobo.class, new ChocoboRenderer(manager, new ModelChocobo()));
	}

	@Override
	public void openChocopedia(EntityChocobo chocobo)
	{
		Minecraft.getMinecraft().displayGuiScreen(new ChocopediaGui(chocobo));
	}

	@Override
	public void updateRiderState(EntityPlayer rider)
	{
		EntityChocobo chocobo = (EntityChocobo) rider.ridingEntity;
		chocobo.getRiderState().updateState(getRiderState(rider));
		if (chocobo.getRiderState().hasChanged())
		{
			RiderStateUpdatePacket packet = new RiderStateUpdatePacket(chocobo);
			PacketRegistry.INSTANCE.sendToServer(packet);
		}
		chocobo.getRiderState().resetChanged();
	}

	private RiderState getRiderState(Entity rider)
	{
		EntityPlayerSP riderSP = (EntityPlayerSP) rider;
		localRiderState.setJumping(riderSP.movementInput.jump);
		localRiderState.setSneaking(riderSP.movementInput.sneak);
		return localRiderState;
	}

}

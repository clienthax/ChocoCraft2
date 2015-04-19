package uk.co.haxyshideout.chococraft2.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import uk.co.haxyshideout.chococraft2.entities.EntityChocobo;

/**
 * Created by clienthax on 13/4/2015.
 */
public class DebugCommand extends CommandBase {

	@Override
	public String getCommandName() {
		return "chocodebug";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/"+getCommandName();
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		EntityChocobo chocobo = new EntityChocobo(sender.getEntityWorld());
		EntityPlayerMP player = (EntityPlayerMP) sender;
		chocobo.setPosition(player.posX, player.posY, player.posZ);
		player.worldObj.spawnEntityInWorld(chocobo);
	}
}

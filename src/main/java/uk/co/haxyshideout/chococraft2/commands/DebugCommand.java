package uk.co.haxyshideout.chococraft2.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

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

		if(args == null)
			return;
		Minecraft.getMinecraft().gameSettings.guiScale = Integer.parseInt(args[0]);
	}
}

package com.pridemc.games.commands;

import com.pridemc.games.arena.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ArenaHelp implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		MessageUtil.sendMsg(sender, "Type /arena + [arg].");
		MessageUtil.sendMsgNoPrefix(sender, "/arena create [name]");
		MessageUtil.sendMsgNoPrefix(sender, "/arena edit [name]");
		MessageUtil.sendMsgNoPrefix(sender, "/arena tp [name]");
		MessageUtil.sendMsgNoPrefix(sender, "/arena start [name] - Jump to Grace Period/Game Start");
		MessageUtil.sendMsgNoPrefix(sender, "/arena set ...");
		
		/*
		 *Add help items for the arena 
		 */
		
		return true;
	}
}

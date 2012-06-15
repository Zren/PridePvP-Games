package com.pridemc.games.commands;

import ca.xshade.bukkit.util.ChatUtil;
import com.pridemc.games.arena.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ArenaHelp implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		MessageUtil.sendMsg(sender, "Type /arena + [arg].");
		MessageUtil.sendMsgNoPrefix(sender, ChatUtil.formatCommand("", "/arena", "create [name]", "Create a new arena."));
		MessageUtil.sendMsgNoPrefix(sender, ChatUtil.formatCommand("", "/arena", "edit [name]", "Start editing an arena."));
		MessageUtil.sendMsgNoPrefix(sender, ChatUtil.formatCommand("", "/arena", "tp [name]", "Teleport to an arena's spawn."));
		MessageUtil.sendMsgNoPrefix(sender, ChatUtil.formatCommand("", "/arena", "start [name]", "Force start an arena. An arena will skip to the grace period, or if already in the grace period, will start the game."));
		MessageUtil.sendMsgNoPrefix(sender, ChatUtil.formatCommand("", "/arena", "set ...", "Modify the current arena."));
		
		/*
		 *Add help items for the arena 
		 */
		
		return true;
	}
}

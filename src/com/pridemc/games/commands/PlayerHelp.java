package com.pridemc.games.commands;

import ca.xshade.bukkit.util.ChatUtil;
import com.pridemc.games.arena.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PlayerHelp implements CommandExecutor {
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		MessageUtil.sendMsg(sender, "Type /pg + [arg].");
		MessageUtil.sendMsgNoPrefix(sender, ChatUtil.formatCommand("", "/pg", "arenas", "List all the arenas."));
		MessageUtil.sendMsgNoPrefix(sender, ChatUtil.formatCommand("", "/pg", "list", "List the remaining players in the arena."));
		MessageUtil.sendMsgNoPrefix(sender, ChatUtil.formatCommand("", "/pg", "votestart", "Vote to start the arena."));
		MessageUtil.sendMsgNoPrefix(sender, ChatUtil.formatCommand("", "/pg", "leave", "Leave your arena."));
		MessageUtil.sendMsgNoPrefix(sender, ChatUtil.formatCommand("", "/pg", "spawn", "Go to spawn."));
		if (sender.hasPermission("pridegames.admin"))
			MessageUtil.sendMsgNoPrefix(sender, ChatUtil.formatCommand("", "/pg", "setspawn", "Set the spawn point."));
		//MessageUtil.sendMsgNoPrefix(sender, ChatUtil.formatCommand("", "/pg", "shop", "Teleport to the shop."));
		//if (sender.hasPermission("pridegames.admin"))
		//	MessageUtil.sendMsgNoPrefix(sender, ChatUtil.formatCommand("", "/pg", "setshop", "Set the shop location."));
		
		return true;
	}
}

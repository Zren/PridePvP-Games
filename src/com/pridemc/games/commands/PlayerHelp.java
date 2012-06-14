package com.pridemc.games.commands;

import com.pridemc.games.arena.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PlayerHelp implements CommandExecutor {
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		MessageUtil.sendMsg(sender, "Type /pg + [arg].");
		MessageUtil.sendMsgNoPrefix(sender, "/pg list");
		MessageUtil.sendMsgNoPrefix(sender, "/pg votestart");
		MessageUtil.sendMsgNoPrefix(sender, "/pg leave");
		MessageUtil.sendMsgNoPrefix(sender, "/pg spawn");
		if (sender.hasPermission("pridegames.admin"))
			MessageUtil.sendMsgNoPrefix(sender, "/pg setspawn");
		MessageUtil.sendMsgNoPrefix(sender, "/pg shop");
		if (sender.hasPermission("pridegames.admin"))
			MessageUtil.sendMsgNoPrefix(sender, "/pg setshop");
		
		return true;
	}
}

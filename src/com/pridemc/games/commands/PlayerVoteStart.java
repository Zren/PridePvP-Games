package com.pridemc.games.commands;

import com.pridemc.games.arena.ArenaManager;
import com.pridemc.games.arena.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerVoteStart {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			if  (ArenaManager.isInArena(sender.getName())) {
				Player player = (Player)sender;
				ArenaManager.voteToStart(player);
			} else {
				MessageUtil.sendMsg(sender, ChatColor.RED + "You can't use this command now!");
			}
			return true;
		}

		return false;
	}
}

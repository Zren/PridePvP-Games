package com.pridemc.games.commands;

import ca.xshade.bukkit.util.ChatUtil;
import com.pridemc.games.arena.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerList {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player && ArenaManager.isInArena(sender.getName())) {
			Arena arena = ArenaManager.getArenaPlayerIsIn(sender.getName());
			String msg = "Players in " + ChatColor.AQUA + "%s" + ChatColor.YELLOW + ": " + ChatColor.GRAY + "[%s" + ChatColor.GRAY + "]";
			MessageUtil.sendMsg(sender, msg, arena.getName(), ChatUtil.list(ArenaUtil.getPlayerDisplayNames(arena), ChatColor.WHITE, ChatColor.GRAY));
		} else {
			return new PlayerListArenas().onCommand(sender, cmd, label, args);
		}
		
		return true;
	}
}

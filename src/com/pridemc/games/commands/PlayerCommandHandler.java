package com.pridemc.games.commands;

import com.pridemc.games.arena.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PlayerCommandHandler implements CommandExecutor {

	PlayerHelp help;
	PlayerSpawnSet setspawn;
	PlayerShop shop;
	PlayerShopSet setshop;
	PlayerList list;
	PlayerLeave leaveToSpawn;
	PlayerVoteStart votestart;

	DevArenaStates devArenaStates = new DevArenaStates();
	ArenaStart devArenaStart = new ArenaStart();

	public PlayerCommandHandler() {
		help = new PlayerHelp();
		setspawn = new PlayerSpawnSet();
		shop = new PlayerShop();
		setshop = new PlayerShopSet();
		list = new PlayerList();
		leaveToSpawn = new PlayerLeave();
		votestart = new PlayerVoteStart();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("help")) {
				return (help.onCommand(sender, cmd, label, args));

			} else if (args[0].equalsIgnoreCase("spawn")) {
				return (leaveToSpawn.onCommand(sender, cmd, label, args));

			} else if (args[0].equalsIgnoreCase("setspawn")) {
				return (setspawn.onCommand(sender, cmd, label, args));

			} else if (args[0].equalsIgnoreCase("shop")) {
				return (shop.onCommand(sender, cmd, label, args));

			} else if (args[0].equalsIgnoreCase("setshop")) {
				return (setshop.onCommand(sender, cmd, label, args));

			} else if (args[0].equalsIgnoreCase("list")) {
				return (list.onCommand(sender, cmd, label, args));

			} else if (args[0].equalsIgnoreCase("leave")) {
				return (leaveToSpawn.onCommand(sender, cmd, label, args));

			} else if (args[0].equalsIgnoreCase("votestart")) {
				return (votestart.onCommand(sender, cmd, label, args));

			} else if (args[0].equalsIgnoreCase("state")) {
				return (devArenaStates.onCommand(sender, cmd, label, args));

			} else if (args[0].equalsIgnoreCase("devstart")) {
				return (devArenaStart.onCommand(sender, cmd, label, args));

			}
		} else {
			String msg = "Type " + ChatColor.GOLD + "/pg help" + ChatColor.YELLOW + " to view player commands";
			MessageUtil.sendMsg(sender, msg);
		}

		return true;
	}
}

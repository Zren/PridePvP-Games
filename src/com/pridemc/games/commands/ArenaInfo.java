package com.pridemc.games.commands;

import ca.xshade.bukkit.util.ChatUtil;
import ca.xshade.bukkit.util.CollectionUtil;
import com.pridemc.games.arena.Arena;
import com.pridemc.games.arena.ArenaManager;
import com.pridemc.games.arena.ArenaUtil;
import com.pridemc.games.arena.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/17/12
 */
public class ArenaInfo {
	public static boolean onCommand(CommandSender sender, String[] args) {
		Arena arena = null;

		if (args.length < 2) {
			if (sender instanceof Player) {
				Player player = (Player)sender;
				arena = ArenaManager.getArenaPlayerIsIn(player.getName());
			}

			if (arena == null) {
				// Wasn't a player in an arena.
				MessageUtil.sendMsg(sender, ChatColor.RED + "Please specify an arena.");
				return true;
			}
		} else {
			String arenaName = args[1];
			arena = ArenaManager.getArena(arenaName);
			if (arena == null) {
				String msg = ChatColor.RED + "There is no arena called '%s'.";
				MessageUtil.sendMsg(sender, msg, arenaName);
				return true;
			}
		}

		sendArenaInfo(sender, arena);

		return true;
	}

	public static void sendArenaInfo(CommandSender sender, Arena arena) {
		// Title / Name
		MessageUtil.sendMsgNoPrefix(sender, ChatUtil.formatTitle(arena.getName()));

		// Region
		String msg;
		if (arena.hasRegion()) {
			msg = String.format("min = %s, max = %s",
					arena.getRegion().getMin(),
					arena.getRegion().getMax());
		} else {
			msg = ChatColor.RED + "Not set.";
		}
		MessageUtil.sendMsgNoPrefix(sender, ChatUtil.keyValue("Region", msg));

		// Portal
		if (arena.hasPortal()) {
			msg = String.format("key sign = %s", arena.getPortal().getKeyLocation());
		} else {
			msg = ChatColor.RED + "Not set.";
		}
		MessageUtil.sendMsgNoPrefix(sender, ChatUtil.keyValue("Portal", msg));

		// Spawn Point
		if (arena.hasSpawnPoint()) {
			msg = arena.getSpawnPoint().toString();
		} else {
			msg = ChatColor.RED + "Not set.";
		}
		MessageUtil.sendMsgNoPrefix(sender, ChatUtil.keyValue("Spawn Point", msg));

		// Game Spawn Points
		int numGameSpawnPoints = arena.getGameSpawnPointVectors().size();
		if (numGameSpawnPoints > 0) {
			msg = String.format("(Total: %d) ", numGameSpawnPoints);
			msg += ChatUtil.list(CollectionUtil.toStringList(arena.getGameSpawnPointVectors()), ChatColor.WHITE, ChatColor.GRAY);
		} else {
			msg = ChatColor.RED + "None set.";
		}
		MessageUtil.sendMsgNoPrefix(sender, ChatUtil.keyValue("Game Points", msg));

		// Start Time
		if (arena.getStartTime() == Long.MAX_VALUE) {
			msg = "Max Value (Future / No revert)";
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
			msg = sdf.format(new Date(arena.getStartTime()));
		}
		MessageUtil.sendMsgNoPrefix(sender, ChatUtil.keyValue("Start Time (Revert Back To)", msg));

		// State
		MessageUtil.sendMsgNoPrefix(sender, ChatUtil.keyValue("State", "%s aka %s",
				(arena.getState().canJoin() ?  ChatColor.GREEN : ChatColor.RED) + arena.getState().getShortName(),
				arena.getState()));
		// Votes
		MessageUtil.sendMsgNoPrefix(sender, ChatUtil.keyValue("Votes", "Current = %d, Need = %d, Requires = %d",
				arena.getNumVotesToStart(),
				arena.getNumVotesNeededToStart(),
				arena.getNumVotesRequiredToStart()));

		// Players
		MessageUtil.sendMsgNoPrefix(sender, ChatUtil.keyValue("Players", "Current = %d, Need = %d, Requires [%d, %d]",
				arena.getNumPlayers(),
				arena.getNumPlayersNeededToStart(),
				arena.getNumPlayersRequiredToStart(),
				arena.getMaxNumPlayers()));

		// Players in arena
		MessageUtil.sendMsgNoPrefix(sender, ChatUtil.keyValue("Players",
				ChatUtil.list(ArenaUtil.getPlayerDisplayNames(arena), ChatColor.WHITE, ChatColor.GRAY)));


	}
}

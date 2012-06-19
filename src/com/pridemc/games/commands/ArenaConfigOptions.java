package com.pridemc.games.commands;

import ca.xshade.bukkit.util.config.WorldVector;
import com.pridemc.games.arena.*;
import com.sk89q.worldedit.IncompleteRegionException;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class ArenaConfigOptions implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {
			if (args.length < 2) {
				String msg = ChatColor.RED + "You have to specifiy something to set!" +
						"Spawnpoint [" + ChatColor.YELLOW + "sp" + ChatColor.RED + "], " +
						"Game point[" + ChatColor.YELLOW + "gp" + ChatColor.RED + "], " +
						"Max players[" + ChatColor.YELLOW + "mp" + ChatColor.RED + "], " +
						"Playercount before start[" + ChatColor.YELLOW + "pbs" + ChatColor.RED + "]" +
						"Region[" + ChatColor.YELLOW + "region" + ChatColor.RED + "]" +
						"Votes Required[" + ChatColor.YELLOW + "v" + ChatColor.RED + "]";
				MessageUtil.sendMsg(sender, msg);

			} else {
				Player player = (Player) sender;
				if (!ArenaEditManager.isEdidting(player)) {
					String msg = ChatColor.RED + "You are not editing an arena!" +
							" Type " + ChatColor.YELLOW + "/arena edit <name> " + ChatColor.RED + "to begin editing an arena";
					MessageUtil.sendMsg(sender, msg);
					return true;
				}

				Arena arena = ArenaEditManager.getArenaPlayerIsEditing(player);

				if (arena == null) {
					String msg = ChatColor.RED + "The arena you were editing (%s) no longer exists.";
					MessageUtil.sendMsg(sender, msg, ArenaEditManager.getArenaNamePlayerIsEditing(player));

					ArenaEditManager.finishEditSession(player);
					return true;
				}

				String msgArenaChange = "Set " + ChatColor.AQUA + "%s" + ChatColor.YELLOW + " for " + ChatColor.AQUA + "%s" + ChatColor.YELLOW + " to " + ChatColor.AQUA + "%s";

				if (args[1].equalsIgnoreCase("sp")) {
					//Setting spawn points
					arena.setSpawnPoint(player.getLocation());
					String msg = "Spawn point for %s set at your location.";
					MessageUtil.sendMsg(sender, msg, arena.getName());

				} else if (args[1].equalsIgnoreCase("gp")) {
					//Setting game points

					Location location = player.getLocation();
					WorldVector worldVector = new WorldVector(location).floorVector();
					arena.addGameSpawnPointVectors(worldVector);

					String msg = "Game spawn point for %s set at your location (%s). Total: %d";
					MessageUtil.sendMsg(sender, msg, arena.getName(), worldVector.toString(), arena.getGameSpawnPointVectors().size());


				} else if (args[1].equalsIgnoreCase("mp")) {
					//Setting the max players
					if (args.length < 3) {
						String msg = ChatColor.RED + "Missing arguments. Correct usage: /arena set mp #";
						MessageUtil.sendMsg(sender, msg);
					} else {
						try {
							Integer number = Integer.valueOf(args[2]);
							arena.setMaxNumPlayers(number);
							MessageUtil.sendMsg(sender, msgArenaChange, "Max Player Amound", arena.getName(), number);
						} catch (NumberFormatException ex) {
							MessageUtil.sendMsg(sender, ChatColor.RED + "The last argument must be a number!");
						}
					}
				} else if (args[1].equalsIgnoreCase("v")) {
					//Setting the votes to start
					if (args.length < 3) {
						sender.sendMessage(ChatColor.RED + "Missing arguments. Correct usage: /arena set v #");
					} else {
						try {
							int number = Integer.valueOf(args[2]);
							arena.setNumVotesRequiredToStart(number);
							MessageUtil.sendMsg(sender, msgArenaChange, "votes required", arena.getName(), number);
						} catch (NumberFormatException ex) {
							MessageUtil.sendMsg(sender, ChatColor.RED + "The last argument must be a number!");
						}
					}
				} else if (args[1].equalsIgnoreCase("pbs")) {
					//Setting the players to start
					if (args.length < 3) {
						sender.sendMessage(ChatColor.RED + "Missing arguments. Correct usage: /arena set pbs #");
					} else {
						try {
							Integer number = Integer.valueOf(args[2]);
							arena.setNumPlayersRequiredToStart(number);
							MessageUtil.sendMsg(sender, msgArenaChange, "Players Required to Start", arena.getName(), number);
						} catch (NumberFormatException ex) {
							MessageUtil.sendMsg(sender, ChatColor.RED + "The last argument must be a number!");
						}
					}
				} else if (args[1].equalsIgnoreCase("region")) {
					try {
						Vector minVector = WorldEditUtil.getSelectionMinimum(player);
						Vector maxVector = WorldEditUtil.getSelectionMaximum(player);
						arena.setRegion(new CuboidRegion(player.getWorld(), minVector, maxVector));
						MessageUtil.sendMsg(sender, msgArenaChange, "Region", arena.getName(), String.format("(%s) -> (%s)", maxVector, maxVector));
					} catch (IncompleteRegionException e) {
						MessageUtil.sendMsg(sender, ChatColor.RED + "You have not selected a region (WorldEdit).");
					}
				}

				ArenaManager.updateArena(arena);
			}
		}

		return true;
	}
}

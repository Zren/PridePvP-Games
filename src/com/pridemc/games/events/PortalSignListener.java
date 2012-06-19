package com.pridemc.games.events;

import ca.xshade.bukkit.util.config.WorldVector;
import com.pridemc.games.arena.*;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/15/12
 */
public class PortalSignListener implements Listener {
	@EventHandler
	public void onSignChange(SignChangeEvent event) {
		Block block = event.getBlock();
		Player player = event.getPlayer();

		if (block.getType() == Material.WALL_SIGN) {
			if (player.hasPermission("pridegames.admin")) {
				org.bukkit.block.Sign sign = (org.bukkit.block.Sign)block.getState();
				if (event.getLine(0).equalsIgnoreCase("[PrideArena]")) {
					String arenaName = event.getLine(1);

					Arena arena = ArenaManager.getArena(arenaName);
					if (arena == null) {
						arena = new Arena(arenaName);
						ArenaManager.addArena(arena);
						ArenaEditManager.startEditSession(player, arena);
						String msg = "New arena " + ChatColor.AQUA + "%s" + ChatColor.YELLOW + " succesfully created! You are now editing this arena as well. To stop editing, type" + ChatColor.GOLD + " /arena edit";
						MessageUtil.sendMsg(player, msg, arenaName);
					}
					Location location = sign.getLocation();
					WorldVector worldVector = new WorldVector(location);
					if (worldVector == null)
						MessageUtil.sendMsg(player, "ASDFSADF");
					arena.setPortalKeyVector(worldVector);

					String msg = "Portal for %s created.";
					MessageUtil.sendMsg(player, msg, arenaName);
					arena.loadPortal(arena.getPortalKeyVector());
					ArenaManager.updateArena(arena);
				}
			} else {
				event.getBlock().breakNaturally();
				MessageUtil.sendMsg(player, ChatColor.RED + "You don't have permission to place portal signs!");
			}
		}
	}
}

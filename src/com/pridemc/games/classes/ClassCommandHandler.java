package com.pridemc.games.classes;

import com.pridemc.games.arena.Arena;
import com.pridemc.games.arena.ArenaManager;
import com.pridemc.games.arena.ArenaPlayer;
import com.pridemc.games.arena.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClassCommandHandler implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			PlayerClassManager.sendListOfAvailableClasses(sender);
		} else if (args.length > 0) { // If statement unnecessary
			Player player = (Player) sender;

			if (ArenaManager.isInArena(player.getName())) {
				Arena arena = ArenaManager.getArenaPlayerIsIn(player.getName());

				if (!arena.getState().canChangeClass()) {
					MessageUtil.sendMsg(player, ChatColor.RED + "You can't use this command now!");
					return true;
				}

				if (PlayerClassManager.hasAClass(player.getName())) {
					MessageUtil.sendMsg(player, ChatColor.RED + "You have already selected a class!");
					return true;
				}

				String className = args[0];
				boolean selectedAClass = PlayerClassManager.selectClass(player, className);
				if (selectedAClass) {
					ArenaPlayer arenaPlayer = arena.getArenaPlayer(player.getName());
					arenaPlayer.equip();
				} else {
					PlayerClassManager.sendListOfAvailableClasses(player);
				}
			} else {
				MessageUtil.sendMsg(player, ChatColor.RED + "You can't use this command now!");
				return true;
			}
		}
		return true;
	}
}

package com.pridemc.games.commands;

import com.pridemc.games.arena.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ArenaCommandHandler implements CommandExecutor {

	ArenaCreation create;
	ArenaRemoval remove;
	ArenaHelp help;
	ArenaConfigOptions set;
	ArenaTp tp;
	ArenaEdit edit;
	ArenaStart start;

	public ArenaCommandHandler() {
		create = new ArenaCreation();
		remove = new ArenaRemoval();
		help = new ArenaHelp();
		set = new ArenaConfigOptions();
		tp = new ArenaTp();
		edit = new ArenaEdit();
		start = new ArenaStart();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender.hasPermission("pridegames.admin")) {

			if (args.length > 0) {

				if (args[0].equalsIgnoreCase("create")) {
					return (create.onCommand(sender, cmd, label, args));

				} else if (args[0].equalsIgnoreCase("remove")) {
					return (remove.onCommand(sender, cmd, label, args));

				} else if (args[0].equalsIgnoreCase("help")) {
					return (help.onCommand(sender, cmd, label, args));

				} else if (args[0].equalsIgnoreCase("set")) {
					return (set.onCommand(sender, cmd, label, args));

				} else if (args[0].equalsIgnoreCase("tp")) {
					return (tp.onCommand(sender, cmd, label, args));

				} else if (args[0].equalsIgnoreCase("edit")) {
					return (edit.onCommand(sender, cmd, label, args));

				} else if (args[0].equalsIgnoreCase("start")) {
					return (start.onCommand(sender, cmd, label, args));

				} else if (args[0].equalsIgnoreCase("region")) {
					return (new ArenaEditRegion().onCommand(sender, cmd, label, args));

				}

			} else {
				String msg = "Type " + ChatColor.GOLD + "/arena help" + ChatColor.YELLOW + " to view the commands that deal with arenas";
				MessageUtil.sendMsg(sender, msg);
			}
		}

		return true;
	}
}

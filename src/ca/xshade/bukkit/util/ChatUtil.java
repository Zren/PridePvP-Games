package ca.xshade.bukkit.util;

import org.bukkit.ChatColor;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/15/12
 */
public class ChatUtil {
	public static String formatCommand(String requirement, String command, String subCommand, String help) {
		String out = "  ";
		if (requirement.length() > 0)
			out += ChatColor.RED + requirement + ": ";
		out += ChatColor.BLUE + command;
		if (subCommand.length() > 0)
			out += " " + ChatColor.AQUA + subCommand;
		if (help.length() > 0)
			out += " " + ChatColor.GRAY + " : " + help;
		return out;
	}
}


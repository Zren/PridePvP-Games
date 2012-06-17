package ca.xshade.bukkit.util;

import ca.xshade.util.StringUtil;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/15/12
 */
public class ChatUtil {
	public static String formatCommand(String requirement, String command, String subCommand, String help) {
		String out = "  ";
		out += ChatColor.BLUE + command;
		if (subCommand.length() > 0)
			out += " " + ChatColor.AQUA + subCommand;
		if (requirement.length() > 0)
			out += " " + ChatColor.RED + "[" + requirement + "]";
		if (help.length() > 0)
			out += " " + ChatColor.GRAY + ": " + help;
		return out;
	}

	public static String list(Collection<String> items, ChatColor itemColour, ChatColor delimeterColor) {
		Collection<String> formattedItems = new ArrayList<String>();
		for (String item : items) {
			formattedItems.add(itemColour + item);
		}
		return StringUtil.join(formattedItems, delimeterColor + ", ");
	}

	public static String formatTitle(String title) {
		String line = ChatColor.GOLD + "[ " + ChatColor.YELLOW + ChatColor.BOLD + "%s" + ChatColor.GOLD + " ]";
		return String.format(line, title);
	}

	public static String keyValue(String key, String value, Object ... args) {
		String line = ChatColor.AQUA + key + ": " + ChatColor.YELLOW + value;
		return String.format(line, args);
	}
}


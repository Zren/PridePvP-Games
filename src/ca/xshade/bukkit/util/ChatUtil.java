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
		if (requirement.length() > 0)
			out += ChatColor.RED + requirement + ": ";
		out += ChatColor.BLUE + command;
		if (subCommand.length() > 0)
			out += " " + ChatColor.AQUA + subCommand;
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
}


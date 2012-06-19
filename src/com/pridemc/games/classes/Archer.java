package com.pridemc.games.classes;

import ca.xshade.util.Chance;
import com.pridemc.games.arena.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/14/12
 */
public class Archer extends PlayerClass {
	public Archer() {
		setName("Archer");
		setDescription("Bow & 10 Arrows, Iron Helm, and a Golden Apple.");
	}

	@Override
	public void equipPlayer(Player player) {
		// Give Equipment
		player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET, 1)); // 1 Armour

		if (Chance.oneIn(4)) {
			// Bad Luck
			MessageUtil.sendMsg(player, ChatColor.RED + "Bad Luck! Got a regular apple instead.");
			player.getInventory().addItem(new ItemStack(Material.APPLE, 1));
		} else {
			player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 1));
		}

		if (Chance.oneIn(4)) {
			// Bad Luck
			MessageUtil.sendMsg(player, ChatColor.RED + "Bad Luck! Got a broken bow instead. Quickly! Make a new one.");
			player.getInventory().addItem(new ItemStack(Material.STRING, 3));
			player.getInventory().addItem(new ItemStack(Material.WORKBENCH, 1));
		} else {
			ItemStack bowItemStack = new ItemStack(Material.BOW, 1);
			if (Chance.oneIn(3)) {
				// Good Luck
				MessageUtil.sendMsg(player, ChatColor.GREEN + "Good Luck! Got an enchanted bow instead.");
				bowItemStack.addEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
			}
			player.getInventory().addItem(bowItemStack);
		}

		player.getInventory().addItem(new ItemStack(Material.ARROW, 10));
	}
}

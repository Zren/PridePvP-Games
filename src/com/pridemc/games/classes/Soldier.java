package com.pridemc.games.classes;

import ca.xshade.util.Chance;
import com.pridemc.games.arena.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/14/12
 */
public class Soldier extends PlayerClass {
	public Soldier() {
		setName("Soldier");
		setDescription("Leather armour and a Stone Sword.");
	}

	@Override
	public void equipPlayer(Player player) {
		// Give Equipment
		player.getInventory().setChestplate(new ItemStack(Material.IRON_HELMET, 1)); // 1 Armour
		player.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE, 1)); // 1.5 Armour
		player.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS, 1)); // 1 Armour

		if (Chance.oneIn(4)) {
			// Bad Luck
			MessageUtil.sendMsg(player, ChatColor.RED + "Bad Luck! Got a broken sword instead. Quickly! Make a new one.");
			player.getInventory().addItem(new ItemStack(Material.WOOD_SWORD, 1));
			player.getInventory().addItem(new ItemStack(Material.STICK, 1));
			player.getInventory().addItem(new ItemStack(Material.COBBLESTONE, 2));
			player.getInventory().addItem(new ItemStack(Material.WORKBENCH, 1));
		} else {
			player.getInventory().addItem(new ItemStack(Material.STONE_SWORD, 1));
		}

		if (Chance.oneIn(4))
			player.getInventory().addItem(new ItemStack(Material.IRON_INGOT, 1));
	}
}
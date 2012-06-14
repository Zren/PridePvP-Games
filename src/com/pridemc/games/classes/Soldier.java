package com.pridemc.games.classes;

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
	}

	@Override
	public void equipPlayer(Player player) {
		// Clear First
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);

		// Reset Player Effects
		player.getActivePotionEffects().clear();

		// Give Equipment
		player.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE, 1));
		player.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS, 1));
		player.getInventory().addItem(new ItemStack(Material.STONE_SWORD, 1));
	}
}
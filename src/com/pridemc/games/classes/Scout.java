package com.pridemc.games.classes;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/14/12
 */
public class Scout extends PlayerClass {
	public Scout() {
		setName("Scout");
		setDescription("Gold Plate, Stone Sword, and a vial of speed.");
	}

	@Override
	public void equipPlayer(Player player) {
		// Give Equipment
		player.getInventory().setChestplate(new ItemStack(Material.GOLD_CHESTPLATE, 1));
		player.getInventory().addItem(new ItemStack(Material.STONE_SWORD, 1));
		player.getInventory().addItem(new Potion(PotionType.SPEED).toItemStack(1));
	}
}
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
public class Tank extends PlayerClass {
	public Tank() {
		setName("Tank");
		setDescription("A specialized Soldier with a Diamond Plate and a Strength Potion.");
		setPermission("pg.class.tank");
		setRequirement(Requirement.VIP);
	}

	@Override
	public void equipPlayer(Player player) {
		// Give Equipment
		player.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE, 1));
		player.getInventory().addItem(new Potion(PotionType.STRENGTH).toItemStack(1));

		// Soldier Equipment
		player.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS, 1));
		player.getInventory().addItem(new ItemStack(Material.STONE_SWORD, 1));


	}
}

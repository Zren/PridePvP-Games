package com.pridemc.games.classes;

import org.bukkit.Material;
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
		player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET, 1));
		player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 1));

		ItemStack bowItemStack = new ItemStack(Material.BOW, 1);
		//bowItemStack.addEnchantment(Enchantment.ARROW_DAMAGE, 1);
		player.getInventory().addItem(bowItemStack);
		player.getInventory().addItem(new ItemStack(Material.ARROW, 10));
	}
}

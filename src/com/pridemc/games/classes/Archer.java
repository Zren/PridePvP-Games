package com.pridemc.games.classes;

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
		setDescription("Enchanted Bow & 10 Arrows, Iron Helm, and a Golden Apple.");
	}

	@Override
	public void equipPlayer(Player player) {
		// Give Equipment
		player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET, 1));
		player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 1));

		ItemStack bowItemStack = new ItemStack(Material.BOW, 1);
		bowItemStack.addEnchantment(Enchantment.ARROW_DAMAGE, 1);
		player.getInventory().addItem(bowItemStack);
		player.getInventory().addItem(new ItemStack(Material.ARROW, 10));

		// Add Effect to all bows in Inv.
		for(ItemStack bow : player.getInventory().getContents()){
			if(bow != null){
				if(bow.getType().equals(Material.BOW)){
					bow.addEnchantment(Enchantment.ARROW_DAMAGE, 1);
				}
			}
		}
	}
}

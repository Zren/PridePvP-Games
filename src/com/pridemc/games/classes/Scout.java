package com.pridemc.games.classes;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
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
	}

	@Override
	public void equipPlayer(Player player) {
		// Clear First
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);

		// Reset Player Effects
		player.getActivePotionEffects().clear();

		// Give Equipment
		player.getInventory().setChestplate(new ItemStack(Material.GOLD_CHESTPLATE, 1));
		player.getInventory().addItem(new ItemStack(Material.STONE_SWORD, 1));
		player.getInventory().addItem(new Potion(PotionType.SPEED).toItemStack(1));

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
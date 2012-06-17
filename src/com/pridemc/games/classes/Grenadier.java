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
public class Grenadier extends PlayerClass {
	public Grenadier() {
		setName("Grenadier");
		setDescription("Equiped with 4 Tier I Splash Potions.");
		calculatePermission();
		setRequirement(Requirement.VIP);
	}

	@Override
	public void equipPlayer(Player player) {
		// Give Equipment
		ItemStack helmItemStack = new ItemStack(Material.IRON_HELMET, 1); // 1 Armour
		helmItemStack.addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 1);
		player.getInventory().addItem(helmItemStack);

		Potion damageSplashPotion = new Potion(PotionType.INSTANT_DAMAGE);
		damageSplashPotion.splash();
		ItemStack damageSplashPotionItemStack = damageSplashPotion.toItemStack(1);

		for (int i = 0; i < 4; i++)
			player.getInventory().addItem(damageSplashPotionItemStack);


	}
}

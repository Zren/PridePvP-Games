package com.pridemc.games.classes;

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
		setPermission("pg.class.grenadier");
		setRequirement(Requirement.VIP);
	}

	@Override
	public void equipPlayer(Player player) {
		// Give Equipment
		Potion damageSplashPotion = new Potion(PotionType.INSTANT_DAMAGE);
		damageSplashPotion.splash();
		ItemStack damageSplashPotionItemStack = damageSplashPotion.toItemStack(1);

		for (int i = 0; i < 4; i++)
			player.getInventory().addItem(damageSplashPotionItemStack);
	}
}

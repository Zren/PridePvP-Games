package com.pridemc.games.classes;

import com.pridemc.games.arena.EffectUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/17/12
 */
public class Diver extends PlayerClass {
	public Diver() {
		setName("Diver");
		setDescription("Underwater oxygen extraction apparatus + map.");
		calculatePermission();
		setRequirement(Requirement.VIP);
	}

	@Override
	public void equipPlayer(Player player) {
		EffectUtil.permanentEffect(player, PotionEffectType.WATER_BREATHING);

		ItemStack swordItemStack = new ItemStack(Material.WOOD_SWORD, 1);
		player.getInventory().addItem(swordItemStack);

		ItemStack helmItemStack = new ItemStack(Material.LEATHER_HELMET, 1);
		helmItemStack.addEnchantment(Enchantment.WATER_WORKER, 1);
		player.getInventory().setHelmet(helmItemStack);

		for (int i = 0; i < 2; i++)
			player.getInventory().addItem(new ItemStack(Material.COOKED_FISH, 1));
		player.getInventory().addItem(new ItemStack(Material.MAP, 1));

	}
}

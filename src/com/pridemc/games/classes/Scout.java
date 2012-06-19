package com.pridemc.games.classes;

import com.pridemc.games.arena.EffectUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/14/12
 */
public class Scout extends PlayerClass {
	public Scout() {
		setName("Scout");
		//setDescription("Gold Plate, Stone Sword, and a vial of speed.");
		setDescription("Invulnerable to arrows with a vial of speed.");
	}

	@Override
	public void equipPlayer(Player player) {
		// Give Equipment
		player.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE, 1));


		ItemStack bootsItemStack = new ItemStack(Material.LEATHER_BOOTS, 1);
		bootsItemStack.addEnchantment(Enchantment.PROTECTION_PROJECTILE, 4);
		bootsItemStack.addEnchantment(Enchantment.PROTECTION_FALL, 4);
		player.getInventory().setBoots(bootsItemStack);

		player.getInventory().addItem(new ItemStack(Material.WOOD_SWORD, 1));
		player.getInventory().addItem(new Potion(PotionType.SPEED).toItemStack(1));

		EffectUtil.permanentEffect(player, PotionEffectType.FAST_DIGGING);
	}
}
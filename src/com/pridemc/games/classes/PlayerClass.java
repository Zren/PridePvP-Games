package com.pridemc.games.classes;

import org.bukkit.entity.Player;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/14/12
 */
public abstract class PlayerClass {
	private String name;
	private String permission = null;

	public boolean canSelectAsClass(Player player) {
		if (permission == null || permission.isEmpty())
			return true;
		return player.hasPermission(permission);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public static void resetPlayer(Player player) {
		// Clear Inventory + Armor
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);

		// Reset Player Effects
		player.getActivePotionEffects().clear();
	}

	public abstract void equipPlayer(Player player);
}

package com.pridemc.games.classes;

import org.bukkit.entity.Player;
import org.bukkit.permissions.Permissible;
import org.bukkit.potion.PotionEffect;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/14/12
 */
public abstract class PlayerClass {
	private static final String VIP_CLASS_NODE_PREFIX = "pg.class.";

	public enum Requirement {
		NONE("Basic Classes"),
		VIP("VIP Donor Classes");

		private String description;
		private Requirement(String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}
	}

	private String name;
	private String permission = null;
	private String description = "";
	private Requirement requirement = Requirement.NONE;

	public boolean canSelectAsClass(Permissible permissible) {
		if (permission == null || permission.isEmpty())
			return true;
		return permissible.hasPermission(permission);
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

	public void calculatePermission() {
		setPermission(VIP_CLASS_NODE_PREFIX + getName().toLowerCase());
	}

	public static void resetPlayer(Player player) {
		// Clear Inventory + Armor
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);

		// Reset Player Effects
		for (PotionEffect potionEffect : player.getActivePotionEffects()) {
			player.removePotionEffect(potionEffect.getType());
		}
	}

	public abstract void equipPlayer(Player player);

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Requirement getRequirement() {
		return requirement;
	}

	public void setRequirement(Requirement requirement) {
		this.requirement = requirement;
	}
}

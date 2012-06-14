package com.pridemc.games.classes;

import org.bukkit.entity.Player;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/14/12
 */
public abstract class PlayerClass {
	String name;
	String permission = null;

	public void equip(Player player) {

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

	public abstract void equipPlayer(Player player);
}

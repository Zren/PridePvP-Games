package com.pridemc.games.events;

import ca.xshade.bukkit.util.MinecraftUtil;
import com.pridemc.games.arena.Arena;
import com.pridemc.games.arena.ArenaManager;
import com.pridemc.games.classes.Diver;
import com.pridemc.games.classes.PlayerClass;
import com.pridemc.games.classes.PlayerClassManager;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffectType;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/3/12
 */
public class PvP implements Listener {

	@EventHandler(ignoreCancelled = true)
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		Entity defender = event.getEntity();
		if (defender instanceof Player) {
			Player defenderPlayer = (Player)defender;
			if (ArenaManager.isInArena(defenderPlayer.getName())) {
				Arena arena = ArenaManager.getArenaPlayerIsIn(defenderPlayer.getName());
				if (!arena.getState().canPvP()) {
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onEntityDamageByEntityMonitor(EntityDamageByEntityEvent event) {
		Entity defenderEntity = event.getEntity();
		if (defenderEntity instanceof Player) {
			Player defenderPlayer = (Player)defenderEntity;
			Entity attackerEntity = event.getDamager();
			if (attackerEntity != null && attackerEntity instanceof Player) {
				Player attackerPlayer = (Player)attackerEntity;

				if (ArenaManager.isInArena(defenderPlayer.getName()) && ArenaManager.isInArena(attackerPlayer.getName())) {
					Arena defenderArena = ArenaManager.getArenaPlayerIsIn(defenderPlayer.getName());
					Arena atttackerArena = ArenaManager.getArenaPlayerIsIn(defenderPlayer.getName());

					if (defenderArena.equals(atttackerArena)) {
						onArenaPvP(event, defenderPlayer, attackerPlayer);
					}
				}

			}
		}
	}

	public void onArenaPvP(EntityDamageByEntityEvent event, Player defender, Player attacker) {
		PlayerClass attackerClass = PlayerClassManager.getPlayerClass(attacker.getName());

		if (attackerClass instanceof Diver) {
			if (attacker.getItemInHand().getType() == Material.WOOD_SWORD) {
				int durationTicks = MinecraftUtil.secondsToTicks(5);
				defender.addPotionEffect(PotionEffectType.POISON.createEffect(durationTicks, 1));
			}
		}
	}
}

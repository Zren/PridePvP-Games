package com.pridemc.games.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/15/12
 */
public class PlayerMoveBlockEvent extends Event implements Cancellable {
	private static final HandlerList handlers = new HandlerList();

	private Player player;
	private Block fromBlock, toBlock;
	private boolean cancelled;

	public PlayerMoveBlockEvent(Player player, Block fromBlock, Block toBlock, boolean cancelled) {
		this.player = player;
		this.fromBlock = fromBlock;
		this.toBlock = toBlock;
		this.cancelled = cancelled;
	}

	public Player getPlayer() {
		return player;
	}

	public Block getFromBlock() {
		return fromBlock;
	}

	public Block getToBlock() {
		return toBlock;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}

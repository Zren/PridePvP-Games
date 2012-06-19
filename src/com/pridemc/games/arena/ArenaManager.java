package com.pridemc.games.arena;

import ca.xshade.bukkit.util.TaskInjector;
import com.pridemc.games.classes.PlayerClass;
import com.pridemc.games.classes.PlayerClassManager;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/2/12
 */
public class ArenaManager {
	private static ArenaManager instance;
	private Map<String, Arena> arenaMap = new HashMap<String, Arena>();
	private Map<String, String> playerToArenaMap = new HashMap<String, String>();

	public static void setInstance(ArenaManager instance) {
		ArenaManager.instance = instance;
	}

	public static ArenaManager getInstance() {
		return instance;
	}

	public static Arena getArena(String name) {
		//return getInstance().arenaMap.get(name.toLowerCase());
		return getInstance().arenaMap.get(name);
	}

	public static void addPlayerToArena(Player player, String arenaName) throws Exception {
		Arena arena = getArena(arenaName);
		addPlayerToArena(player, arena);
	}

	// Use this method for future proofing
	// Eg: a player joins two arenas at once somehow.
	public static void addPlayerToArena(Player player, Arena arena) throws Exception {
		// Validation
		if (getInstance().playerToArenaMap.containsKey(player.getName()))
			throw new Exception(String.format("%s already belongs to %s while trying to join %s.",
					player.getName(),
					getInstance().playerToArenaMap.get(player.getName()),
					arena.getName()));
		if (!arena.getState().canJoin())
			throw new Exception(String.format("%s is already in progress", arena.getName())); // Don't need an error. Just don't let player warp through the portal.
		if (arena.isFull())
			throw new Exception(String.format("%s is already full", arena.getName()));


		//
		_addPlayerToArena(player, arena);

		// Reset player inv and state.
		PlayerClass.resetPlayer(player);

		// Teleport player
		if (arena.hasSpawnPoint())
			player.teleport(arena.getSpawnPoint());


		// Reaction
		String msg = ChatColor.AQUA + "%s" + ChatColor.YELLOW + " joined the arena Arena %s [%d / %d].";
		List<Player> playersInArena = ArenaUtil.asBukkitPlayerList(arena.getArenaPlayers());
		MessageUtil.sendMsgToAllPlayers(playersInArena, msg,
				player.getName(), arena.getName(), arena.getArenaPlayers().size(), arena.getMaxNumPlayers());

		//
		if (arena.getNumPlayers() >= arena.getMaxNumPlayers()) {
			// Arena is ready
			msg = "Reached max number of players.";
			MessageUtil.sendMsgToAllPlayers(playersInArena, msg, arena.getNumPlayersNeededToStart());
			arena.startTaskFor(Arena.State.INITIAL_GRACE_PERIOD);
		} else if (arena.getState() == Arena.State.WAITING_FOR_PLAYERS && arena.getNumPlayers() >= arena.getNumPlayersRequiredToStart()) {
			// Arena is ready
			arena.startTaskFor(Arena.State.COUNTING_DOWN);
		} else {
			msg = "This arena requires %d more players to begin automatically. Do " + ChatColor.AQUA + "/pg votestart" + ChatColor.YELLOW + " to start now.";
			MessageUtil.sendMsgToAllPlayers(playersInArena, msg, arena.getNumPlayersNeededToStart());
		}

		//
		msg = "Do " + ChatColor.AQUA + "/class" + ChatColor.YELLOW + " to pick your class.";
		MessageUtil.sendMsg(player, msg, arena.getName());

		//
		updateArena(arena);
	}

	private static void _addPlayerToArena(Player player, Arena arena) {
		arena.addPlayer(new ArenaPlayer(player));
		getInstance().playerToArenaMap.put(player.getName(), arena.getName());
		//Core.instance.getPlaying().put(player, arena.getName()); //TODO Legacy
	}

	public static void removePlayerFromArena(Player player) {
		Arena arena = ArenaManager.getArenaPlayerIsIn(player.getName());
		//if (arena != null && !arena.getState().canJoin()) { // If in a game state where the players can't join, then remove the player
		if (arena != null) {
			ArenaManager._cleanUpPlayer(player);

			List<Player> arenaPlayersAlive = ArenaUtil.asBukkitPlayerList(arena.getArenaPlayers());

			List<CommandSender> msgTargets = new ArrayList<CommandSender>(arenaPlayersAlive);
			msgTargets.add(player);
			String msg = ChatColor.AQUA + "%s" + ChatColor.YELLOW + " has died! " + ChatColor.AQUA + "%d" + ChatColor.YELLOW + " players remaining!";
			MessageUtil.sendMsgToAll(msgTargets, msg, player.getName(), arenaPlayersAlive.size());
			EffectUtil.explosionOverPlayers(arenaPlayersAlive);

			//
			updateArena(arena);

			if (ArenaManager.checkEndGameConditions(arena)) {
				ArenaManager.endGame(arena);
			}
		}
	}

	public static void _removePlayerFromArena(String playerName) {
		Arena arena = ArenaManager.getArenaPlayerIsIn(playerName);
		if (arena == null)
			return;
		arena.setPlayerAsDead(playerName);
		PlayerClassManager.unregisterPlayerClass(playerName);
		getInstance().playerToArenaMap.remove(playerName);
	}

	public static void voteToStart(Player player) {
		Arena arena = ArenaManager.getArenaPlayerIsIn(player.getName());

		if (!arena.getState().canJoin()) {
			MessageUtil.sendMsg(player, ChatColor.RED + "You can't use this command now!");
			return;
		}

		boolean votedToStart = arena.voteToStart(player.getName());
		if (votedToStart) {
			List<Player> playersInArena = ArenaUtil.asBukkitPlayerList(arena.getArenaPlayers());
			String msg = ChatColor.AQUA + "%s" + ChatColor.YELLOW + " voted to start the arena now. " + ChatColor.AQUA + "%d" + ChatColor.YELLOW + " more votes required (" + ChatColor.AQUA + "/pg votestart" + ChatColor.YELLOW + ").";
			MessageUtil.sendMsgToAllPlayers(playersInArena, msg, player.getName(), arena.getNumVotesNeededToStart());
		} else {
			String msg = ChatColor.RED + "Already voted. " + ChatColor.AQUA + "%d" + ChatColor.YELLOW + " more votes required.";
			MessageUtil.sendMsg(player, msg, arena.getNumVotesNeededToStart());
		}

		if (arena.getNumVotesToStart() >= arena.getNumVotesRequiredToStart()) {
			arena.startTaskFor(Arena.State.INITIAL_GRACE_PERIOD);
		}
	}

	public static Arena getArenaPlayerIsIn(String playerName) {
		String arenaName = getInstance().playerToArenaMap.get(playerName);
		return getArena(arenaName);
	}

	public static boolean checkEndGameConditions(Arena arena) {
		if (!arena.getState().canJoin()) {
			Set<ArenaPlayer> alivePlayers = arena.getArenaPlayers();
			if (alivePlayers.size() <= 1) {
				return true;
			}
		}
		return false;
	}

	public static void endGame(Arena arena) {
		List<ArenaPlayer> alivePlayers = new ArrayList<ArenaPlayer>(arena.getArenaPlayers()); //TODO ?

		if (alivePlayers.size() > 0) {
			Player winningPlayer = alivePlayers.get(0).getPlayer();

			// Msg
			String msg = ChatColor.AQUA + "%s" + ChatColor.YELLOW + " won in the %s arena!";
			MessageUtil.sendMsgToServer(msg, winningPlayer.getDisplayName(), arena.getName());

			// Cleanup remaining players.
			for (ArenaPlayer arenaPlayer : alivePlayers) {
				ArenaManager.cleanUpPlayer(arenaPlayer.getPlayer());
			}
		} else {
			MessageUtil.sendMsgToServer("The %s arena ended with no winner.", arena.getName());
		}

		// Cleanup Arena
		resetArena(arena.getName());
	}

	public static void addArena(Arena arena) {
		//getInstance().arenaMap.put(arena.getName().toLowerCase(), arena);
		getInstance().arenaMap.put(arena.getName(), arena);
		updateArena(arena);
	}

	public static void cleanUpPlayer(Player player) {
		player.teleport(ArenaCore.getInstance().getGlobalSpawnPoint());
		_cleanUpPlayer(player);
	}

	public static void _cleanUpPlayer(Player player) {
		_removePlayerFromArena(player.getName());
		PlayerClass.resetPlayer(player);
	}

	public static Arena resetArena(String arenaName) {
		cleanupArena(getArena(arenaName));
		addArena(new Arena(arenaName));
		return getArena(arenaName);
	}

	public static boolean isInArena(String playerName) {
		return getInstance().playerToArenaMap.containsKey(playerName);
	}

	public static void cleanupArena(Arena arena) {
		for (Player player : ArenaUtil.asBukkitPlayerList(arena.getArenaPlayers())) {
			cleanUpPlayer(player);
		}

		arena.getTaskInjector().cancelAll();
		RevertManager.revertArena(arena);

	}

	public static void cleanupAllArenas() {
		for (Arena arena : getArenas()) {
			cleanupArena(arena);
		}
	}

	public static Collection<Arena> getArenas() {
		return getInstance().arenaMap.values();
	}

	public static Arena getArenaFromPortalBlock(Block block) {
		for (Arena arena : getArenas()) {
			if (!arena.hasPortal())
				continue;

			if (arena.getPortal().isTeleBlock(block))
				return arena;
		}
		return null;
	}

	public static void updateArena(Arena arena) {
		TaskInjector.getInstance().schedule(new UpdateArenaTask(arena), 0);
	}

	public static void removeArena(Arena arena) {
		//TODO teardown portal signs etc.
		getInstance().arenaMap.remove(arena.getName());
	}

	public static void deleteArena(Arena arena) {

		removeArena(arena);
		ArenaCore.getInstance().getArenaConfig().set(arena.getName(), null);
	}
}

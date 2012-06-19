package com.pridemc.games.arena;

import ca.xshade.bukkit.util.TaskInjector;
import ca.xshade.bukkit.util.config.Config;
import ca.xshade.bukkit.util.config.WorldVector;
import com.pridemc.games.portal.ArenaPortal;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.logging.Logger;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/2/12
 */
public class Arena {


	//TODO Loading gamestate?
	public enum State {
		// Normal GameStates
		WAITING_FOR_PLAYERS("Open",
				true, false, true, false, true),
		COUNTING_DOWN("Starting Soon",
				true, false, true, false, true),
		INITIAL_GRACE_PERIOD("Started|Grace",
				false, true, true, false, true),
		RUNNING_GAME("Running",
				false, true, false, true, false),
		// Special GameStates
		EDIT("Editing",
				false, true, false, false, false),
		CLOSED("Closed",
				false, false, false, false, false);

		private boolean canJoin, canEditBlocks, canChangeClass, canPvP, canUnpackEquipment;
		private String shortName;

		private State(String shortName, boolean canJoin, boolean canEditBlocks, boolean canChangeClass, boolean canPvP, boolean canUnpackEquipment) {
			setShortName(shortName);
			this.canJoin = canJoin;
			this.canEditBlocks = canEditBlocks;
			this.canChangeClass = canChangeClass;
			this.canPvP = canPvP;
			this.canUnpackEquipment = canUnpackEquipment;
		}

		public boolean canJoin() {
			return canJoin;
		}

		public boolean canEditBlocks() {
			return canEditBlocks;
		}

		public boolean canChangeClass() {
			return canChangeClass;
		}

		public boolean canPvP() {
			return canPvP;
		}

		public boolean canUnpackEquipment() {
			return canUnpackEquipment;
		}

		public boolean canDropItems() {
			// Player can't drop items when the arena is in a state to choose classes.
			return !canChangeClass();
		}

		/**
		 * Limits to 16 chars for use on a sign.
		 * @return
		 */
		private void setShortName(String shortName) {
			this.shortName = shortName;
		}

		public String getShortName() {
			return shortName;
		}
	}


	// Not Persistent
	private Map<String, ArenaPlayer> arenaPlayerMap = new HashMap<String, ArenaPlayer>();
	private Set<ArenaPlayer> arenaPlayers = new HashSet<ArenaPlayer>();
	private Map<ArenaPlayer, Location> playerSpawnPoints = new HashMap<ArenaPlayer, Location>();
	private long startTime = System.currentTimeMillis();
	private TaskInjector taskInjector = TaskInjector.newInstance();
	private Set<ArenaPlayer> playersVotingToStart = new HashSet<ArenaPlayer>();
	private State state = State.WAITING_FOR_PLAYERS;

	// Persistent
	private String name;
	private CuboidRegion region = null;

	//
	Location spawnPoint;
	int maxNumPlayers;
	int numPlayersRequiredToStart;
	int numVotesRequiredToStart;
	WorldVector portalKeyVector;
	List<WorldVector> gameSpawnPointVectors;
	long timeLimitInMinutes; //TODO


	// Semi - Persistenet
	private ArenaPortal portal = null;

	// Contants
	final int DEFAULT_MAX_PLAYERS = 15;
	final int DEFAULT_PLAYERS_TO_START = 8;
	final int DEFAULT_VOTES_TO_START = 2;

	final String NODE_REGION = "region";
	final String NODE_MAX_PLAYERS = "max_players";
	final String NODE_PLAYERS_REQUIRED = "playercount_to_start";
	final String NODE_VOTES_REQUIRED = "votes_to_start";
	final String NODE_SPAWNPOINT = "spawnpoint";
	final String NODE_PORTAL_KEY = "portal.key";
	final String NODE_GAME_SPAWNPOINTS = "game_spawnpoints";

	public Arena(String name) {
		this.name = name;
		load();
	}

	public void setMaxNumPlayers(int maxNumPlayers) {
		configSet(NODE_MAX_PLAYERS, maxNumPlayers);
	}

	public void setNumPlayersRequiredToStart(int numPlayersRequiredToStart) {
		configSet(NODE_PLAYERS_REQUIRED, numPlayersRequiredToStart);
	}

	public void setNumVotesRequiredToStart(int numVotesRequiredToStart) {
		configSet(NODE_VOTES_REQUIRED, numVotesRequiredToStart);
	}

	public void setPortalKeyVector(WorldVector portalKeyVector) {
		configSet(NODE_PORTAL_KEY, portalKeyVector);
	}

	public void setGameSpawnPointVectors(List<WorldVector> gameSpawnPointVectors) {
		configSet(NODE_GAME_SPAWNPOINTS, gameSpawnPointVectors);
	}

	public WorldVector getPortalKeyVector() {
		return getArenaConfig().getWorldVector(getSubNodePath(NODE_PORTAL_KEY));
	}

	public void addGameSpawnPointVectors(WorldVector worldVector) {
		List<WorldVector> gameSpawnPointVectors = getGameSpawnPointVectors();
		gameSpawnPointVectors.add(worldVector);
		setGameSpawnPointVectors(gameSpawnPointVectors);
	}

	public void load() {
		// Root Node
		Config arenaConfig = getArenaConfig();

		if (!arenaConfig.getKeys(false).contains(getName())) {
			arenaConfig.createSection(getName());
		}

		setSpawnPoint(getSpawnPoint());
		setRegion(getRegionFromConfig());
		setMaxNumPlayers(getMaxNumPlayers());
		setNumPlayersRequiredToStart(getNumPlayersRequiredToStart());
		setNumVotesRequiredToStart(getNumVotesRequiredToStart());
		setPortalKeyVector(getPortalKeyVector());
		setGameSpawnPointVectors(getGameSpawnPointVectors());

		loadPortal(getPortalKeyVector());


		setState(State.WAITING_FOR_PLAYERS);

		ArenaCore.getInstance().save();
	}


	public String getName() {
		return name;
	}

	public int getNumPlayers() {
		return getArenaPlayers().size();
	}

	public Set<ArenaPlayer> getArenaPlayers() {
		return arenaPlayers;
	}

	public State getState() {
		return state;
	}

	protected void setState(State state) {
		if (state.ordinal() < this.state.ordinal()) {
			Logger logger = Logger.getLogger(getClass().getName());
			logger.warning("-----------------------------------------------------------");
			logger.warning("-------------------       BUG REPORT     ------------------");
			logger.warning("-----------------------------------------------------------");
			logger.warning(String.format("Arena %s is trying to go back to an earlier gamestate. %s -> %s", getName(), state.name(), this.state.name()));
			Thread.dumpStack();
			logger.warning("-----------------------------------------------------------");
		}

		this.state = state;
	}

	protected void addPlayer(ArenaPlayer arenaPlayer) {
		arenaPlayers.add(arenaPlayer);
		arenaPlayerMap.put(arenaPlayer.getName(), arenaPlayer);
	}

	public ArenaPlayer getArenaPlayer(String playerName) {
		return arenaPlayerMap.get(playerName);
	}

	public void removePlayer(String playerName) {
		ArenaPlayer arenaPlayer = getArenaPlayer(playerName);
		if (arenaPlayer != null) {
			arenaPlayers.remove(arenaPlayer);
			arenaPlayerMap.remove(playerName);
		}
	}

	public List<WorldVector> getGameSpawnPointVectors() {
		return getArenaConfig().getWorldVectorList(getSubNodePath(NODE_GAME_SPAWNPOINTS));
	}

	public List<Location> getGameSpawnPointLocations() {
		List<Location> gameSpawnPoints = new ArrayList<Location>();
		for (WorldVector worldVector : getGameSpawnPointVectors()) {
			gameSpawnPoints.add(worldVector.toLocation());
		}
		return gameSpawnPoints;
	}

	public int getMaxNumPlayers() {
		return getArenaConfig().getInt(getSubNodePath(NODE_MAX_PLAYERS), DEFAULT_MAX_PLAYERS);
	}

	public boolean isFull() {
		return arenaPlayers.size() >= getMaxNumPlayers();
	}

	public List<Player> getBukkitPlayers() {
		return ArenaUtil.asBukkitPlayerList(getArenaPlayers());
	}


	public void setPlayerSpawnPoints() {
		List<Location> spawnPoints = getGameSpawnPointLocations();
		List<ArenaPlayer> players = new ArrayList<ArenaPlayer>(getArenaPlayers());
		Collections.shuffle(spawnPoints);

		for (int i = 0; i < players.size(); i++) {
			playerSpawnPoints.put(players.get(i), spawnPoints.get(i % spawnPoints.size()));
		}
	}

	public void teleportAllToGameSpawnPoint() {
		for (ArenaPlayer arenaPlayer : getArenaPlayers()) {
			teleportToGameSpawnPoint(arenaPlayer);
		}
	}

	public void teleportToGameSpawnPoint(ArenaPlayer arenaPlayer) {
		arenaPlayer.getPlayer().teleport(playerSpawnPoints.get(arenaPlayer));
	}

	public Location getSpawnPoint() {
		return getArenaConfig().getLocation(getSubNodePath(NODE_SPAWNPOINT));
	}

	public void setSpawnPoint(Location spawnPoint) {
		getArenaConfig().set(getSubNodePath(NODE_SPAWNPOINT), spawnPoint);
	}

	public void setPlayerAsDead(String playerName) {
		ArenaPlayer arenaPlayer = getArenaPlayer(playerName);
		arenaPlayers.remove(arenaPlayer);
		playersVotingToStart.remove(arenaPlayer);
	}

	public boolean voteToStart(String playerName) {
		ArenaPlayer arenaPlayer = getArenaPlayer(playerName);
		if (playersVotingToStart.contains(arenaPlayer))
			return false;

		playersVotingToStart.add(arenaPlayer);
		return true;
	}

	public int getNumVotesToStart() {
		return getPlayersVotingToStart().size();
	}

	public int getNumVotesRequiredToStart() {
		return getArenaConfig().getInt(getSubNodePath(NODE_VOTES_REQUIRED), DEFAULT_VOTES_TO_START);
	}

	public int getNumVotesNeededToStart() {
		// Scales down when less people are online.
		// Allowing for configured requirements of only 1 vote. You could have 0 as well, but it wouldn't trigger by itself.
		int required = getNumVotesRequiredToStart();
		int online = Bukkit.getServer().getOnlinePlayers().length;
		online = Math.max(2, online); // Limit lower bound to 2. As we want at least two players in an arena.
		required = Math.min(required, online); // Choose whichever is less.
		int needed = required - getNumVotesToStart();
		needed = Math.max(0, needed); // Limit lower bound to zero.
		return needed;
	}

	public int getNumPlayersRequiredToStart() {
		return getArenaConfig().getInt(getSubNodePath(NODE_PLAYERS_REQUIRED), DEFAULT_PLAYERS_TO_START);
	}

	public int getNumPlayersNeededToStart() {
		return Math.max(0, getNumPlayersRequiredToStart() - getNumPlayers()); // limit lower bounds to 0.
	}

	public Set<ArenaPlayer> getPlayersVotingToStart() {
		return playersVotingToStart;
	}

	public TaskInjector getTaskInjector() {
		return taskInjector;
	}

	public void startTaskFor(State state) {
		scheduleTaskFor(state, 0);
	}

	public void scheduleTaskFor(State state, long delay) {
		getTaskInjector().cancelAll();
		switch (state) {
			case CLOSED:
				getTaskInjector().schedule(new ArenaClosedTask(this), delay);
				break;

			case WAITING_FOR_PLAYERS:
				getTaskInjector().schedule(new ArenaWaitingForPlayersTask(this), delay);
				break;

			case COUNTING_DOWN:
				getTaskInjector().schedule(new ArenaCountdownTask(this), delay);
				break;

			case INITIAL_GRACE_PERIOD:
				getTaskInjector().schedule(new ArenaGraceTask(this), delay);
				break;

			case RUNNING_GAME:
				getTaskInjector().schedule(new ArenaStartGameTask(this), delay);
				break;
			case EDIT:
				getTaskInjector().schedule(new ArenaStartEditSessionTask(this), delay);

			default:
				break;
		}
	}

	public void loadPortal(WorldVector worldVector) {
		if (worldVector == null)
			return;

		if (hasPortal()) {
			//TODO Break down old portal.
		}

		this.portal = new ArenaPortal(worldVector.toLocation().getBlock());
	}

	public void update() {
		updatePortal();
	}

	public void updatePortal() {
		if (!hasPortal())
			return;

		portal.update(this);
	}

	public boolean hasPortal() {
		return portal != null;
	}

	public ArenaPortal getPortal() {
		return portal;
	}

	public String getSubNodePath(String subNode) {
		return getName() + "." + subNode;
	}

	public void configSet(String subNode, Object obj) {
		getArenaConfig().set(getSubNodePath(subNode), obj);
	}

	public Config getArenaConfig() {
		return ArenaCore.getInstance().getArenaConfig();
	}


	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public void setRegion(World world, Vector min, Vector max) {
		setRegion(new CuboidRegion(world, min, max));
	}

	public void setRegion(CuboidRegion region) {
		this.region = region;
		configSet(NODE_REGION, region);
	}

	public CuboidRegion getRegion() {
		return region;
	}

	public CuboidRegion getRegionFromConfig() {
		Object val = getArenaConfig().get(getSubNodePath(NODE_REGION));
		if (val instanceof CuboidRegion) {
			return (CuboidRegion)val;
		} else {
			return null;
		}
	}

	public boolean hasRegion() {
		return region != null;
	}


	public boolean hasSpawnPoint() {
		return getSpawnPoint() != null;
	}

	public boolean isSetup() {
		return hasRegion()
			&& hasPortal()
			&& getGameSpawnPointVectors().size() > 0
			&& hasSpawnPoint();
	}



}

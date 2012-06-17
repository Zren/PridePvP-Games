package com.pridemc.games.portal;

import ca.xshade.bukkit.util.BlockFaceUtil;
import ca.xshade.bukkit.util.BlockRefUtil;
import com.pridemc.games.arena.Arena;
import com.pridemc.games.arena.ArenaPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/15/12
 */
public class ArenaPortal {
	BlockFace signDirection;

	Block arenaPortalDescriptionBlock;
	Block arenaPortalStateBlock;
	List<Block> innerBlocks;
	List<Block> teleBlocks;
	List<Block> playerListBlocks;

	public ArenaPortal(Block block) {
		if (block.getType() != Material.WALL_SIGN)
			throw new IllegalArgumentException("Could not find a sign at marker.");

		arenaPortalDescriptionBlock = block;
		Sign sign = toSign(arenaPortalDescriptionBlock);

		MaterialData materialData = sign.getData();
		org.bukkit.material.Sign signMaterialData = (org.bukkit.material.Sign)materialData;
		signDirection = signMaterialData.getFacing();

		assignBlocks();
	}

	public Block getTopRightOfFrame() {
		return BlockRefUtil.getBlockInDirection(arenaPortalDescriptionBlock, signDirection.getOppositeFace());
	}

	private void assignBlocks() {
		assignStateBlock();
		assignInnerAreaBlocks();
		assignPlayerListBlocks();
	}

	public void setBlockAsSign(Block block) {
		block.setType(Material.WALL_SIGN);

		// Rotate the sign to the in the right direction.
		BlockState blockState = block.getState();
		org.bukkit.material.Sign signMaterialData = (org.bukkit.material.Sign)blockState.getData();
		signMaterialData.setFacingDirection(signDirection);
		blockState.update();


		// Set
		//block.setTypeIdAndData(Material.WALL_SIGN.getId(), signMaterialData.getData(), true);
	}

	public Sign toSign(Block block) {
		if (block.getType() != Material.WALL_SIGN) {
			setBlockAsSign(block);
		}
		return (Sign)block.getState();
	}

	private void assignStateBlock() {
		Block cursorBlock = BlockRefUtil.getBlockInDirection(arenaPortalDescriptionBlock, BlockFace.DOWN);
		arenaPortalStateBlock = cursorBlock;
		toSign(arenaPortalStateBlock);
	}

	private void assignInnerAreaBlocks() {
		BlockFace right = BlockFaceUtil.rotateRight90(signDirection);

		// Get top bottom block of inner area.
		Block cursorBlock = BlockRefUtil.getBlockInDirection(getTopRightOfFrame(), right);
		cursorBlock = BlockRefUtil.getBlockInDirection(cursorBlock, BlockFace.DOWN, 3);

		innerBlocks = new ArrayList<Block>();
		teleBlocks = new ArrayList<Block>();

		for (int i = 0; i < 2; i++) {
			if (i > 0) {
				// Move cursorBlock left.
				cursorBlock = BlockRefUtil.getBlockInDirection(cursorBlock, right);
			}
			// Add cursorBlock to path as it's the one on the ground.
			teleBlocks.add(cursorBlock);

			// Add blocks above cursor and itself to the innerBlocks list.
			for (int y = 0; y < 3; y++) {
				innerBlocks.add(BlockRefUtil.getBlockInDirection(cursorBlock, BlockFace.UP, y));
			}
		}
	}

	private void assignPlayerListBlocks() {
		BlockFace right = BlockFaceUtil.rotateRight90(signDirection);

		// Get top bottom block of inner area.
		Block cursorBlock = BlockRefUtil.getBlockInDirection(arenaPortalDescriptionBlock, right, 3);

		playerListBlocks = new ArrayList<Block>();

		// Add blocks below cursor and itself to the playerblocks list.
		for (int y = 0; y < 4; y++) {
			playerListBlocks.add(BlockRefUtil.getBlockInDirection(cursorBlock, BlockFace.DOWN, y));
		}
	}

	public void update(Arena arena) {
		updateSigns(arena);
		updateInnerArea(arena);
	}

	public void updateInnerArea(Arena arena) {
		if (arena.getState().canJoin()) {
			setBlockList(innerBlocks, Material.AIR);
		} else {
			setBlockList(innerBlocks, Material.IRON_FENCE);
		}
	}

	public void updateSigns(Arena arena) {
		Sign arenaPortalDescriptionSign = toSign(arenaPortalDescriptionBlock);
		arenaPortalDescriptionSign.setLine(0, String.format("[%s]", arena.getName()));
		arenaPortalDescriptionSign.setLine(1, String.format(ChatColor.DARK_RED + "Requires"));
		arenaPortalDescriptionSign.setLine(2, String.format("Players: %d-%d", arena.getNumPlayersRequiredToStart(), arena.getMaxNumPlayers()));
		arenaPortalDescriptionSign.setLine(3, String.format("Votes: %d", arena.getNumVotesRequiredToStart()));
		arenaPortalDescriptionSign.update();

		Sign arenaPortalStateSign = toSign(arenaPortalStateBlock);
		arenaPortalStateSign.setLine(0, String.format("%s%s", arena.getState().canJoin() ? ChatColor.DARK_BLUE : ChatColor.DARK_RED, arena.getState().getShortName()));
		arenaPortalStateSign.setLine(1, String.format(""));
		arenaPortalStateSign.setLine(2, String.format("Players                "));
		arenaPortalStateSign.setLine(3, String.format("§1%d §0/ §1%d                ", arena.getNumPlayers(), arena.getMaxNumPlayers()));
		arenaPortalStateSign.update();

		upatePlayerListSigns(arena);
	}

	public void upatePlayerListSigns(Arena arena) {
		List<String> view = new ArrayList<String>();
		view.add("§1-- Players --");
		Iterator<ArenaPlayer> arenaPlayerIterator = arena.getArenaPlayers().iterator();
		for (int i = 0; i < 15; i++) {
			if (!arenaPlayerIterator.hasNext())
				break;
			ArenaPlayer arenaPlayer = arenaPlayerIterator.next();
			view.add(arenaPlayer.getName());
		}
		if (arenaPlayerIterator.hasNext())
			view.set(15, "...");

		Iterator<String> viewIterator = view.iterator();
		for (int signIndex = 0; signIndex < 4; signIndex++) {
			Sign sign = toSign(playerListBlocks.get(signIndex));

			boolean changed = false;
			for (int lineIndex = 0; lineIndex < 4; lineIndex++) {
				String oldLine = sign.getLine(lineIndex);
				String newLine;
				if (viewIterator.hasNext())
					newLine = viewIterator.next();
				else
					newLine = "";

				if (!oldLine.equals(newLine)) {
					sign.setLine(lineIndex, newLine);
					changed = true;
				}
			}
			if (changed)
				sign.update();
		}
	}

	public void setBlockList(Iterable<Block> blockList, Material material) {
		for (Block block : blockList) {
			block.setType(material);
		}
	}

	public boolean isTeleBlock(Block block) {
		return teleBlocks.contains(block);
	}

	public Location getKeyLocation() {
		return arenaPortalDescriptionBlock.getLocation();
	}
}

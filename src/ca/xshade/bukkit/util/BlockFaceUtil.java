package ca.xshade.bukkit.util;

import org.bukkit.block.BlockFace;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/15/12
 */
public class BlockFaceUtil {
	public static BlockFace rotateLeft90(BlockFace blockFace) {
		switch (blockFace) {
			case NORTH: return BlockFace.WEST;
			case EAST: return BlockFace.NORTH;
			case SOUTH: return BlockFace.EAST;
			case WEST: return BlockFace.SOUTH;
			// TODO Add the other vectors.
			default: return blockFace;
		}
	}

	public static BlockFace rotateRight90(BlockFace blockFace) {
		switch (blockFace) {
			case NORTH: return BlockFace.EAST;
			case EAST: return BlockFace.SOUTH;
			case SOUTH: return BlockFace.WEST;
			case WEST: return BlockFace.NORTH;
			// TODO Add the other vectors.
			default: return blockFace;
		}
	}
}

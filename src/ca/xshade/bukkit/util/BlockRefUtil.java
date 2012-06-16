package ca.xshade.bukkit.util;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/15/12
 */
public class BlockRefUtil {
	public static Block getBlockInDirection(Block block, BlockFace direction) {
		return getBlockInDirection(block.getLocation(), direction);
	}

	public static Block getBlockInDirection(Block block, BlockFace direction, int scale) {
		return getBlockInDirection(block.getLocation(), direction, scale);
	}

	public static Block getBlockInDirection(Location location, BlockFace direction) {
		return getBlockInDirection(location, direction, 1);
	}

	public static Block getBlockInDirection(Location location, BlockFace direction, int scale) {
		return location.add(direction.getModX() * scale, direction.getModY() * scale, direction.getModZ() * scale).getBlock();
	}
}

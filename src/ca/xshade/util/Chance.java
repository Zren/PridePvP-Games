package ca.xshade.util;

import java.util.Random;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/17/12
 */
public class Chance {
	public static boolean oneIn(int n) {
		return new Random().nextInt(n) == 0;
	}
}

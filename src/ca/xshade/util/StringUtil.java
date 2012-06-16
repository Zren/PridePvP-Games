package ca.xshade.util;

import java.util.Collection;
import java.util.Iterator;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/16/12
 */
public class StringUtil {

	/**
	 * http://stackoverflow.com/a/187720
	 *
	 * @param s
	 * @param delimiter
	 * @return
	 */
	public static String join(Collection<?> s, String delimiter) {
		StringBuilder builder = new StringBuilder();
		Iterator iter = s.iterator();
		while (iter.hasNext()) {
			builder.append(iter.next());
			if (!iter.hasNext()) {
				break;
			}
			builder.append(delimiter);
		}
		return builder.toString();
	}
}

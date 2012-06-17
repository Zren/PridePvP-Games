package ca.xshade.bukkit.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/17/12
 */
public class CollectionUtil {
	public static Collection<String> toStringList(List<?> objs) {
		List<String> strings = new ArrayList<String>(objs.size());
		for (Object obj : objs)
			strings.add(obj.toString());
		return strings;
	}
}

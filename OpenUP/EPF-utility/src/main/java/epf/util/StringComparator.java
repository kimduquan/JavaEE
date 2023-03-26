package epf.util;

import java.util.Comparator;

/**
 * @author PC
 *
 */
public class StringComparator implements Comparator<String> {

	@Override
	public int compare(final String o1, final String o2) {
		return o1.compareTo(o2);
	}
}

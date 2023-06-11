package epf.util;

import java.util.UUID;

/**
 * @author PC
 *
 */
public interface UUIDUtil {

	/**
	 * @param string
	 * @return
	 */
	static UUID fromString(final String string) {
		UUID uuid;
		try {
			uuid = UUID.fromString(string);
		}
		catch(IllegalArgumentException ex) {
			uuid = null;
		}
		return uuid;
	}
}

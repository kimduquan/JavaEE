/**
 * 
 */
package epf.util;

import java.time.Instant;
import java.util.UUID;

/**
 * @author PC
 *
 */
public interface StringUtil {

	/**
	 * @param string
	 * @return
	 */
	static String randomString(final String string) {
		return string + UUID.randomUUID() + Instant.now().toEpochMilli();
	}
}

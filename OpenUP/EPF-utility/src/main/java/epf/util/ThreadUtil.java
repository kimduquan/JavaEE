package epf.util;

import java.time.Duration;
import java.util.Objects;

/**
 * @author PC
 *
 */
public interface ThreadUtil {

	/**
	 * @param duration
	 */
	static void sleep(final Duration duration) {
		Objects.requireNonNull(duration, "");
		try {
			Thread.sleep(duration.toMillis());
		} 
		catch (Exception e) {
			e.toString();
		}
	}
}

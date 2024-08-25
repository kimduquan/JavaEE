package epf.util.time;

import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeParseException;

/**
 * @author PC
 *
 */
public interface TimeUtil {

	/**
	 * @param string
	 * @param def
	 * @return
	 */
	static Duration parse(final String string, final Duration def) {
		Duration duration;
		try {
			duration = Duration.parse(string);
		}
		catch(DateTimeParseException ex) {
			duration = def;
		}
		return duration;
	}
	
	/**
	 * @param string
	 * @param def
	 * @return
	 */
	static Instant parse(final String string, final Instant def) {
		Instant instant;
		try {
			instant = Instant.parse(string);
		}
		catch(DateTimeParseException ex) {
			instant = null;
		}
		return instant;
	}
}

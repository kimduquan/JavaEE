package epf.workflow.schedule.util;

import java.time.Duration;
import java.time.Instant;
import epf.workflow.schedule.ScheduleTrigger;

/**
 * @author PC
 *
 */
public interface ScheduleUtil {
	
	/**
	 * @param interval
	 * @return
	 */
	static ScheduleTrigger parseInterval(final String interval) {
		final String[] values = interval.split("/");
		Instant start = null;
		Duration duration = null;
		Instant end = null;
		if(values.length > 0 && values[0].equals("R")) {
			if(values.length > 1) {
				try {
					duration = Duration.parse(values[1]);
				}
				catch(Exception ex) {
					start = Instant.parse(values[1]);
				}
			}
			if(values.length > 2) {
				end = Instant.parse(values[2]);
			}
		}
		return new ScheduleTrigger(start, duration, end);
	}
}

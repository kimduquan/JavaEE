package epf.schedule.util;

import java.time.Duration;
import java.time.Instant;
import epf.schedule.schema.RecurringTimeInterval;
import epf.schedule.schema.TimeInterval;
import epf.util.time.TimeUtil;

/**
 * @author PC
 *
 */
public interface ScheduleUtil {

	static RecurringTimeInterval parse(final String intervalExpression) {
		RecurringTimeInterval recurringTimeInterval = null;
		if(intervalExpression.startsWith("R")) {
			recurringTimeInterval = new RecurringTimeInterval();
			String numberOfRepetitions = intervalExpression.substring(1, intervalExpression.indexOf("/"));
			if(!numberOfRepetitions.isEmpty()) {
				recurringTimeInterval.setNumberOfRepetitions(Integer.parseInt(numberOfRepetitions));
			}
			String timeInterval = intervalExpression.substring(intervalExpression.indexOf("/") + 1);
			recurringTimeInterval.setTimeInterval(parseTimeInterval(timeInterval));
		}
		return recurringTimeInterval;
	}
	
	static TimeInterval parseTimeInterval(final String intervalExpression) {
		TimeInterval timeInterval = null;
		String[] fragments = intervalExpression.split("/");
		if(fragments.length == 1) {
			timeInterval = new TimeInterval();
			timeInterval.setDuration(TimeUtil.parse(fragments[0], (Duration)null));
		}
		else if(fragments.length == 2) {
			final Instant start = TimeUtil.parse(fragments[0], (Instant)null);
			final Instant end = TimeUtil.parse(fragments[1], (Instant)null);
			Duration duration = null;
			if(start == null) {
				duration = TimeUtil.parse(fragments[0], (Duration)null);
			}
			else if(end == null) {
				duration = TimeUtil.parse(fragments[1], (Duration)null);
			}
			timeInterval = new TimeInterval();
			timeInterval.setDuration(duration);
			timeInterval.setEnd(end);
			timeInterval.setStart(start);
		}
		else if(fragments.length == 3) {
			final Instant start = TimeUtil.parse(fragments[0], (Instant)null);
			final Duration duration = TimeUtil.parse(fragments[1], (Duration)null);
			final Instant end = TimeUtil.parse(fragments[2], (Instant)null);
			timeInterval = new TimeInterval();
			timeInterval.setDuration(duration);
			timeInterval.setEnd(end);
			timeInterval.setStart(start);
		}
		return timeInterval;
	}
}

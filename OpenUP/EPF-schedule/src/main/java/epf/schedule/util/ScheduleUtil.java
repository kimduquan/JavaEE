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

	/**
	 * @param intervalExpression
	 * @return
	 */
	static RecurringTimeInterval parse(final String intervalExpression) {
		RecurringTimeInterval recurringTimeInterval = null;
		if(intervalExpression.startsWith("R")) {
			final int intervalExpressionIndex = intervalExpression.indexOf("/");
			if(intervalExpressionIndex != -1) {
				final String numberOfRepetitions = intervalExpression.substring(1, intervalExpressionIndex);
				final String timeInterval = intervalExpression.substring(intervalExpressionIndex + 1);
				if(!numberOfRepetitions.isEmpty() || !timeInterval.isEmpty()) {
					recurringTimeInterval = new RecurringTimeInterval();
					if(!numberOfRepetitions.isEmpty()) {
						recurringTimeInterval.setNumberOfRepetitions(Integer.parseInt(numberOfRepetitions));
					}
					if(!timeInterval.isEmpty()) {
						recurringTimeInterval.setTimeInterval(parseTimeInterval(timeInterval));
					}
				}
			}
		}
		return recurringTimeInterval;
	}
	
	static TimeInterval parseTimeInterval(final String intervalExpression) {
		TimeInterval timeInterval = null;
		String[] fragments = intervalExpression.split("/");
		Instant start = null;
		Duration duration = null;
		Instant end = null;
		if(fragments.length == 1) {
			duration = TimeUtil.parse(fragments[0], (Duration)null);
		}
		else if(fragments.length == 2) {
			start = TimeUtil.parse(fragments[0], (Instant)null);
			end = TimeUtil.parse(fragments[1], (Instant)null);
			
			if(start == null) {
				duration = TimeUtil.parse(fragments[0], (Duration)null);
			}
			else if(end == null) {
				duration = TimeUtil.parse(fragments[1], (Duration)null);
			}
		}
		else if(fragments.length == 3) {
			start = TimeUtil.parse(fragments[0], (Instant)null);
			duration = TimeUtil.parse(fragments[1], (Duration)null);
			end = TimeUtil.parse(fragments[2], (Instant)null);
		}
		if(
				start != null && duration == null && end != null
				|| start != null && duration != null && end == null
				|| start == null && duration != null && end != null
				|| duration != null
				) {
			timeInterval = new TimeInterval();
			timeInterval.setDuration(duration);
			timeInterval.setEnd(end);
			timeInterval.setStart(start);
		}
		return timeInterval;
	}
}

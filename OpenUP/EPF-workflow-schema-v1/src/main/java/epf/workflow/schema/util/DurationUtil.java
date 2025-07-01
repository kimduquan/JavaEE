package epf.workflow.schema.util;

import java.util.concurrent.TimeUnit;
import epf.workflow.schema.Duration;

public interface DurationUtil {
	
	static TimeUnit getTimeUnit(final Duration duration) {
		TimeUnit timeUnit = null;
		if(duration.getDays() != null) {
			timeUnit = TimeUnit.DAYS;
		}
		else if(duration.getHours() != null) {
			timeUnit = TimeUnit.HOURS;
		}
		else if(duration.getMinutes() != null) {
			timeUnit = TimeUnit.MINUTES;
		}
		else if(duration.getSeconds() != null) {
			timeUnit = TimeUnit.SECONDS;
		}
		else if(duration.getMilliseconds() != null) {
			timeUnit = TimeUnit.MILLISECONDS;
		}
		return timeUnit;
	}

	static long getTime(final Duration duration, final TimeUnit timeUnit) {
		long time = 0;
		java.time.Duration d = java.time.Duration.ZERO;
		if(duration.getDays() != null) {
			d = d.plusDays(duration.getDays());
		}
		if(duration.getHours() != null) {
			d = d.plusHours(duration.getHours());
		}
		if(duration.getMinutes() != null) {
			d = d.plusMinutes(duration.getMinutes());
		}
		if(duration.getSeconds() != null) {
			d = d.plusSeconds(duration.getSeconds());
		}
		if(duration.getMilliseconds() != null) {
			d = d.plusMillis(duration.getMilliseconds());
		}
		switch(timeUnit) {
			case DAYS:
				time = d.toDays();
				break;
			case HOURS:
				time = d.toHours();
				break;
			case MILLISECONDS:
				time = d.toMillis();
				break;
			case MINUTES:
				time = d.toMinutes();
				break;
			case SECONDS:
				time = d.toSeconds();
				break;
			default:
				break;
		}
		return time;
	}
	
	static java.time.Duration getDuration(final Duration duration) {
		java.time.Duration d = java.time.Duration.ZERO;
		if(duration.getDays() != null) {
			d = d.plusDays(duration.getDays());
		}
		if(duration.getHours() != null) {
			d = d.plusHours(duration.getHours());
		}
		if(duration.getMinutes() != null) {
			d = d.plusMinutes(duration.getMinutes());
		}
		if(duration.getSeconds() != null) {
			d = d.plusSeconds(duration.getSeconds());
		}
		if(duration.getMilliseconds() != null) {
			d = d.plusMillis(duration.getMilliseconds());
		}
		return d;
	}
}

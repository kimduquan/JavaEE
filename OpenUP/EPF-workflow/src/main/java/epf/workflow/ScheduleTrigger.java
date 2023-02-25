package epf.workflow;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import javax.enterprise.concurrent.LastExecution;
import javax.enterprise.concurrent.Trigger;

/**
 * @author PC
 *
 */
public class ScheduleTrigger implements Trigger {
	
	/**
	 * 
	 */
	private final Instant start;
	/**
	 * 
	 */
	private final Duration duration;
	/**
	 * 
	 */
	private final Instant end;
	
	/**
	 * @param start
	 * @param duration
	 * @param end
	 */
	public ScheduleTrigger(Instant start, Duration duration, Instant end) {
		this.start = start;
		this.duration = duration;
		this.end = end;
	}

	@Override
	public Date getNextRunTime(final LastExecution lastExecutionInfo, final Date taskScheduledTime) {
		if(start != null && start.isAfter(taskScheduledTime.toInstant())) {
			return Date.from(start);
		}
		return Date.from(taskScheduledTime.toInstant().plusSeconds(duration.toSeconds()));
	}

	@Override
	public boolean skipRun(final LastExecution lastExecutionInfo, final Date scheduledRunTime) {
		if(end != null && !scheduledRunTime.toInstant().isBefore(end)) {
			return true;
		}
		return false;
	}
}

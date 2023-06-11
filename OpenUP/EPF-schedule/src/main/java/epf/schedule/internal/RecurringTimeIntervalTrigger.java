package epf.schedule.internal;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import javax.enterprise.concurrent.LastExecution;
import javax.enterprise.concurrent.Trigger;
import epf.schedule.schema.RecurringTimeInterval;
import epf.schedule.schema.TimeInterval;

/**
 * @author PC
 *
 */
public class RecurringTimeIntervalTrigger implements Trigger {
	
	/**
	 * 
	 */
	private final RecurringTimeInterval recurringTimeInterval;
	
	/**
	 * 
	 */
	private final AtomicInteger numberOfRepetitions = new AtomicInteger();
	
	public RecurringTimeIntervalTrigger(final RecurringTimeInterval recurringTimeInterval) {
		this.recurringTimeInterval = recurringTimeInterval;
	}

	@Override
	public Date getNextRunTime(final LastExecution lastExecutionInfo, final Date taskScheduledTime) {
		Date nextRunTime = Date.from(Instant.now());
		if(recurringTimeInterval.getTimeInterval() != null && recurringTimeInterval.getTimeInterval().getDuration() != null) {
			final Instant now = Instant.now();
			recurringTimeInterval.getTimeInterval().getDuration().addTo(now);
			nextRunTime = Date.from(now);
		}
		numberOfRepetitions.incrementAndGet();
		return nextRunTime;
	}

	@Override
	public boolean skipRun(final LastExecution lastExecutionInfo, final Date scheduledRunTime) {
		boolean skipRun = false;
		if(!skipRun && recurringTimeInterval.getNumberOfRepetitions() != null && numberOfRepetitions.get() >= recurringTimeInterval.getNumberOfRepetitions()) {
			skipRun = true;
		}
		if(!skipRun && recurringTimeInterval.getTimeInterval() != null) {
			final TimeInterval timeInterval = recurringTimeInterval.getTimeInterval();
			if(!skipRun && timeInterval.getStart() != null && timeInterval.getStart().isBefore(Instant.now())) {
				skipRun = true;
			}
			if(!skipRun && timeInterval.getEnd() != null && timeInterval.getEnd().isBefore(Instant.now())) {
				skipRun = true;
			}
		}
		return skipRun;
	}
}

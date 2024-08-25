package epf.schedule.schema;

import java.time.Duration;
import java.time.Instant;

/**
 * @author PC
 *
 */
public class TimeInterval {

	/**
	 * 
	 */
	private Instant start;
	/**
	 * 
	 */
	private Duration duration;
	/**
	 * 
	 */
	private Instant end;
	
	public Instant getStart() {
		return start;
	}
	public void setStart(Instant start) {
		this.start = start;
	}
	public Duration getDuration() {
		return duration;
	}
	public void setDuration(Duration duration) {
		this.duration = duration;
	}
	public Instant getEnd() {
		return end;
	}
	public void setEnd(Instant end) {
		this.end = end;
	}
}

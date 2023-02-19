package epf.workflow.schema;

import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class ScheduleDefinition {

	/**
	 * 
	 */
	@Column
	private String interval;
	
	/**
	 * 
	 */
	@Column
	private CronDefinition cron;
	
	/**
	 * 
	 */
	@Column
	private String timezone;

	public String getInterval() {
		return interval;
	}

	public void setInterval(String interval) {
		this.interval = interval;
	}

	public CronDefinition getCron() {
		return cron;
	}

	public void setCron(CronDefinition cron) {
		this.cron = cron;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
}

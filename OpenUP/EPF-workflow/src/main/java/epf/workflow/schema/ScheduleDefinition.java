package epf.workflow.schema;

/**
 * @author PC
 *
 */
public class ScheduleDefinition {

	/**
	 * 
	 */
	private String interval;
	
	/**
	 * 
	 */
	private Object cron;
	
	/**
	 * 
	 */
	private String timezone;

	public String getInterval() {
		return interval;
	}

	public void setInterval(String interval) {
		this.interval = interval;
	}

	public Object getCron() {
		return cron;
	}

	public void setCron(Object cron) {
		this.cron = cron;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
}

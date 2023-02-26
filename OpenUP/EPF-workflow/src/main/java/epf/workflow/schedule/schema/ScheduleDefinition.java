package epf.workflow.schedule.schema;

import javax.json.bind.annotation.JsonbTypeAdapter;

import epf.workflow.schedule.schema.adapter.CronDefinitionAdapter;
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
	@JsonbTypeAdapter(value = CronDefinitionAdapter.class)
	private Object cron;
	
	/**
	 * 
	 */
	@Column
	private String timezone = "UTC";

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

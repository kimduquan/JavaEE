package epf.workflow.schema.schedule;

import jakarta.json.bind.annotation.JsonbTypeAdapter;
import java.io.Serializable;
import org.eclipse.jnosql.mapping.Embeddable;
import epf.workflow.schema.schedule.adapter.CronDefinitionAdapter;
import epf.workflow.schema.util.Either;
import jakarta.nosql.Column;

/**
 * @author PC
 *
 */
@Embeddable
public class ScheduleDefinition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	private Either<String, CronDefinition> cron;
	
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

	public Either<String, CronDefinition> getCron() {
		return cron;
	}

	public void setCron(Either<String, CronDefinition> cron) {
		this.cron = cron;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
}

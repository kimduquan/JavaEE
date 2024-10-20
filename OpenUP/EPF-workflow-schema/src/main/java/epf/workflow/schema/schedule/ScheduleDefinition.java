package epf.workflow.schema.schedule;

import jakarta.json.bind.annotation.JsonbTypeAdapter;
import java.io.Serializable;
import org.eclipse.jnosql.mapping.Embeddable;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import epf.nosql.schema.StringOrObject;
import epf.workflow.schema.schedule.adapter.StringOrCronDefinitionAdapter;
import jakarta.nosql.Column;

@Embeddable
public class ScheduleDefinition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column
	@Description("A recurring time interval expressed in the derivative of ISO 8601 format specified below. Declares that workflow instances should be automatically created at the start of each time interval in the series.")
	private String interval;
	
	@Column
	@Description("Cron expression defining when workflow instances should be automatically created")
	@JsonbTypeAdapter(value = StringOrCronDefinitionAdapter.class)
	private StringOrObject<CronDefinition> cron;
	
	@Column
	@Description("Timezone name used to evaluate the interval & cron-expression. If the interval specifies a date-time w/ timezone then proper timezone conversion will be applied. (default: UTC).")
	@DefaultValue("UTC")
	private String timezone = "UTC";

	public String getInterval() {
		return interval;
	}

	public void setInterval(String interval) {
		this.interval = interval;
	}

	public StringOrObject<CronDefinition> getCron() {
		return cron;
	}

	public void setCron(StringOrObject<CronDefinition> cron) {
		this.cron = cron;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
}

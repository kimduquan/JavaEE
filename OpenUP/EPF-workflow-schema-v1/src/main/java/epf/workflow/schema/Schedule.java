package epf.workflow.schema;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("Configures the schedule of a workflow.")
public class Schedule {

	@JsonPropertyDescription("Specifies the duration of the interval at which the workflow should be executed. Unlike after, this option will run the workflow regardless of whether the previous run is still in progress. Required when no other property has been set.")
	private Duration every;
	
	@JsonPropertyDescription("Specifies the schedule using a CRON expression, e.g., '0 0 * * *' for daily at midnight. Required when no other property has been set.")
	private String cron;
	
	@JsonPropertyDescription("Specifies a delay duration that the workflow must wait before starting again after it completes. In other words, when this workflow completes, it should run again after the specified amount of time. Required when no other property has been set.")
	private Duration after;
	
	@JsonPropertyDescription("Specifies the events that trigger the workflow execution. Required when no other property has been set.")
	private EventConsumptionStrategy on;

	public Duration getEvery() {
		return every;
	}

	public void setEvery(Duration every) {
		this.every = every;
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	public Duration getAfter() {
		return after;
	}

	public void setAfter(Duration after) {
		this.after = after;
	}

	public EventConsumptionStrategy getOn() {
		return on;
	}

	public void setOn(EventConsumptionStrategy on) {
		this.on = on;
	}
}

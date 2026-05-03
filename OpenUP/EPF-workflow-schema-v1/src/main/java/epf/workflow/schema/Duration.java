package epf.workflow.schema;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("Defines a duration. Durations can be defined through properties, with an ISO 8601 string or with a runtime expression that is evaluated to an ISO 8601 string")
public class Duration {

	@JsonPropertyDescription("Number of days, if any.")
	private Integer days;
	
	@JsonPropertyDescription("Number of hours, if any.")
	private Integer hours;
	
	@JsonPropertyDescription("Number of minutes, if any.")
	private Integer minutes;
	
	@JsonPropertyDescription("Number of seconds, if any.")
	private Integer seconds;
	
	@JsonPropertyDescription("Number of milliseconds, if any.")
	private Integer milliseconds;

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public Integer getHours() {
		return hours;
	}

	public void setHours(Integer hours) {
		this.hours = hours;
	}

	public Integer getMinutes() {
		return minutes;
	}

	public void setMinutes(Integer minutes) {
		this.minutes = minutes;
	}

	public Integer getSeconds() {
		return seconds;
	}

	public void setSeconds(Integer seconds) {
		this.seconds = seconds;
	}

	public Integer getMilliseconds() {
		return milliseconds;
	}

	public void setMilliseconds(Integer milliseconds) {
		this.milliseconds = milliseconds;
	}
}

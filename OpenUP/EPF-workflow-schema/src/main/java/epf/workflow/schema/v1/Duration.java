package epf.workflow.schema.v1;

import org.eclipse.microprofile.graphql.Description;

@Description("Defines a duration.")
public class Duration {

	@Description("Number of days, if any.")
	private Integer Days;
	
	@Description("Number of hours, if any.")
	private Integer Hours;
	
	@Description("Number of minutes, if any.")
	private Integer Minutes;
	
	@Description("Number of seconds, if any.")
	private Integer Seconds;
	
	@Description("Number of milliseconds, if any.")
	private Integer Milliseconds;

	public Integer getDays() {
		return Days;
	}

	public void setDays(Integer days) {
		Days = days;
	}

	public Integer getHours() {
		return Hours;
	}

	public void setHours(Integer hours) {
		Hours = hours;
	}

	public Integer getMinutes() {
		return Minutes;
	}

	public void setMinutes(Integer minutes) {
		Minutes = minutes;
	}

	public Integer getSeconds() {
		return Seconds;
	}

	public void setSeconds(Integer seconds) {
		Seconds = seconds;
	}

	public Integer getMilliseconds() {
		return Milliseconds;
	}

	public void setMilliseconds(Integer milliseconds) {
		Milliseconds = milliseconds;
	}
}

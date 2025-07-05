package epf.schedule.schema;

public class RecurringTimeInterval {
	
	private TimeInterval timeInterval;
	
	private Integer numberOfRepetitions;
	
	public Integer getNumberOfRepetitions() {
		return numberOfRepetitions;
	}
	public void setNumberOfRepetitions(Integer numberOfRepetitions) {
		this.numberOfRepetitions = numberOfRepetitions;
	}
	public TimeInterval getTimeInterval() {
		return timeInterval;
	}
	public void setTimeInterval(TimeInterval timeInterval) {
		this.timeInterval = timeInterval;
	}
}

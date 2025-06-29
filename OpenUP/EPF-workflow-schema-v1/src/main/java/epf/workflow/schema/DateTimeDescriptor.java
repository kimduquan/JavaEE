package epf.workflow.schema;

import java.time.Instant;

public class DateTimeDescriptor {
	
	public class Epoch {
		
		private long seconds;
		private long milliseconds;
		
		public long getSeconds() {
			return seconds;
		}
		public void setSeconds(long seconds) {
			this.seconds = seconds;
		}
		public long getMilliseconds() {
			return milliseconds;
		}
		public void setMilliseconds(long milliseconds) {
			this.milliseconds = milliseconds;
		}
		
	}

	private String iso8601;
	private Epoch epoch;
	
	public static DateTimeDescriptor from(final Instant instant) {
		final DateTimeDescriptor dateTimeDescriptor = new DateTimeDescriptor();
		dateTimeDescriptor.setIso8601(instant.toString());
		dateTimeDescriptor.setEpoch(dateTimeDescriptor.new Epoch());
		dateTimeDescriptor.getEpoch().setMilliseconds(instant.toEpochMilli());
		dateTimeDescriptor.getEpoch().setSeconds(instant.getEpochSecond());
		return dateTimeDescriptor;
	}
	
	public String getIso8601() {
		return iso8601;
	}
	public void setIso8601(String iso8601) {
		this.iso8601 = iso8601;
	}
	public Epoch getEpoch() {
		return epoch;
	}
	public void setEpoch(Epoch epoch) {
		this.epoch = epoch;
	}
}

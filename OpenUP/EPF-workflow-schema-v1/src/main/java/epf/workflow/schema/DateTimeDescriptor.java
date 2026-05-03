package epf.workflow.schema;

import java.time.Instant;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public class DateTimeDescriptor {
	
	public class Epoch {
		
		@JsonPropertyDescription("The date time as a integer value of seconds since midnight of 1970-01-01 UTC (i.e. the Unix Epoch)")
		private Integer seconds;
		
		@JsonPropertyDescription("@JsonPropertyDescription")
		private Integer milliseconds;

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
	
	public static DateTimeDescriptor from(final Instant instant) {
		final DateTimeDescriptor dateTimeDescriptor = new DateTimeDescriptor();
		dateTimeDescriptor.setIso8601(instant.toString());
		dateTimeDescriptor.setEpoch(dateTimeDescriptor.new Epoch());
		dateTimeDescriptor.getEpoch().setMilliseconds((int)instant.toEpochMilli());
		dateTimeDescriptor.getEpoch().setSeconds((int)instant.getEpochSecond());
		return dateTimeDescriptor;
	}

	@JsonPropertyDescription("The date time as a ISO 8601 date time string. It uses T as the date-time delimiter, either UTC (Z) or a time zone offset (+01:00). The precision can be either seconds, milliseconds or nanoseconds")
	private String iso8601;
	
	private Epoch epoch;

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

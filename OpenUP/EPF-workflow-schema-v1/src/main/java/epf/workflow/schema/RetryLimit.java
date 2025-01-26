package epf.workflow.schema;

import org.eclipse.microprofile.graphql.Description;

@Description("The definition of a retry policy.")
public class RetryLimit {

	public class Attempt {
		
		@Description("The maximum attempts count.")
		private Integer count;
		
		@Description("The duration limit, if any, for all retry attempts.")
		private Duration duration;

		public Integer getCount() {
			return count;
		}

		public void setCount(Integer count) {
			this.count = count;
		}

		public Duration getDuration() {
			return duration;
		}

		public void setDuration(Duration duration) {
			this.duration = duration;
		}
	}
	
	private Attempt attempt;
	
	@Description("The maximum duration, if any, during which to retry a given task.")
	private Duration duration;

	public Attempt getAttempt() {
		return attempt;
	}

	public void setAttempt(Attempt attempt) {
		this.attempt = attempt;
	}

	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}
}

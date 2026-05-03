package epf.workflow.schema;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("The Retry is a fundamental concept in the Serverless Workflow DSL, used to define the strategy for retrying a failed task when an error is encountered during execution. This policy provides developers with fine-grained control over how and when to retry failed tasks, enabling robust error handling and fault tolerance within workflows.")
public class Retry {

	@JsonPropertyDescription("A a runtime expression used to determine whether or not to retry running the task, in a given context.")
	private String when;
	
	@JsonPropertyDescription("A runtime expression used to determine whether or not to retry running the task, in a given context.")
	private String exceptWhen;
	
	@JsonPropertyDescription("The duration, if any, to wait between retry attempts.")
	private Duration delay;
	
	@JsonPropertyDescription("The limits, if any, to impose to the retry policy.")
	private Retry limit;
	
	@JsonPropertyDescription("The backoff strategy to use, if any.")
	private Backoff backoff;
	
	@JsonPropertyDescription("The parameters, if any, that control the randomness or variability of the delay between retry attempts.")
	private Jitter jitter;

	public String getWhen() {
		return when;
	}

	public void setWhen(String when) {
		this.when = when;
	}

	public String getExceptWhen() {
		return exceptWhen;
	}

	public void setExceptWhen(String exceptWhen) {
		this.exceptWhen = exceptWhen;
	}

	public Duration getDelay() {
		return delay;
	}

	public void setDelay(Duration delay) {
		this.delay = delay;
	}

	public Retry getLimit() {
		return limit;
	}

	public void setLimit(Retry limit) {
		this.limit = limit;
	}

	public Backoff getBackoff() {
		return backoff;
	}

	public void setBackoff(Backoff backoff) {
		this.backoff = backoff;
	}

	public Jitter getJitter() {
		return jitter;
	}

	public void setJitter(Jitter jitter) {
		this.jitter = jitter;
	}
}

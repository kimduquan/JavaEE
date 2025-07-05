package epf.workflow.schema;

import jakarta.validation.constraints.NotNull;
import jakarta.nosql.Column;
import java.io.Serializable;
import jakarta.nosql.Embeddable;
import org.eclipse.microprofile.graphql.Description;
import epf.workflow.schema.util.StringOrFloat;
import epf.workflow.schema.util.StringOrNumber;

@Embeddable
public class RetryDefinition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Column
	@Description("Unique retry strategy name")
	private String name;
	
	@Column
	@Description("Time delay between retry attempts (literal ISO 8601 duration format or expression which evaluation results in an ISO 8601 duration)")
	private String delay;
	
	@NotNull
	@Column
	@Description("Maximum number of retry attempts. Value of 1 means no retries are performed")
	private StringOrNumber maxAttempts;
	
	@Column
	@Description("Maximum amount of delay between retry attempts (literal ISO 8601 duration format or expression which evaluation results in an ISO 8601 duration)")
	private String maxDelay;
	
	@Column
	@Description("Static duration which will be added to the delay between successive retries (literal ISO 8601 duration format or expression which evaluation results in an ISO 8601 duration)")
	private String increment;
	
	@Column
	@Description("Float value by which the delay is multiplied before each attempt. For example: \"1.2\" meaning that each successive delay is 20% longer than the previous delay. For example, if delay is 'PT10S', then the delay between the first and second attempts will be 10 seconds, and the delay before the third attempt will be 12 seconds.")
	private StringOrFloat multiplier;
	
	@Column
	@Description("If float type, maximum amount of random time added or subtracted from the delay between each retry relative to total delay (between 0.0 and 1.0). If string type, absolute maximum amount of random time added or subtracted from the delay between each retry (literal ISO 8601 duration format or expression which evaluation results in an ISO 8601 duration)")
	private StringOrFloat jitter;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDelay() {
		return delay;
	}

	public void setDelay(String delay) {
		this.delay = delay;
	}

	public StringOrNumber getMaxAttempts() {
		return maxAttempts;
	}

	public void setMaxAttempts(StringOrNumber maxAttempts) {
		this.maxAttempts = maxAttempts;
	}

	public String getMaxDelay() {
		return maxDelay;
	}

	public void setMaxDelay(String maxDelay) {
		this.maxDelay = maxDelay;
	}

	public String getIncrement() {
		return increment;
	}

	public void setIncrement(String increment) {
		this.increment = increment;
	}

	public StringOrFloat getMultiplier() {
		return multiplier;
	}

	public void setMultiplier(StringOrFloat multiplier) {
		this.multiplier = multiplier;
	}

	public StringOrFloat getJitter() {
		return jitter;
	}

	public void setJitter(StringOrFloat jitter) {
		this.jitter = jitter;
	}
}

package epf.workflow.schema;

import jakarta.validation.constraints.NotNull;
import jakarta.nosql.Column;
import org.eclipse.jnosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class RetryDefinition {

	/**
	 * 
	 */
	@NotNull
	@Column
	private String name;
	/**
	 * 
	 */
	@Column
	private String delay;
	/**
	 * 
	 */
	@NotNull
	@Column
	private Object maxAttempts;
	/**
	 * 
	 */
	@Column
	private String maxDelay;
	/**
	 * 
	 */
	@Column
	private String increment;
	/**
	 * 
	 */
	@Column
	private Object multiplier;
	/**
	 * 
	 */
	@Column
	private Object jitter;
	
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
	public Object getMaxAttempts() {
		return maxAttempts;
	}
	public void setMaxAttempts(Object maxAttempts) {
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
	public Object getMultiplier() {
		return multiplier;
	}
	public void setMultiplier(Object multiplier) {
		this.multiplier = multiplier;
	}
	public Object getJitter() {
		return jitter;
	}
	public void setJitter(Object jitter) {
		this.jitter = jitter;
	}
}

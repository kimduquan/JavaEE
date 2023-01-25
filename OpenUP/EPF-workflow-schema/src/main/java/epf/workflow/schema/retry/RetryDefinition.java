package epf.workflow.schema.retry;

import java.io.Serializable;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Entity;
import jakarta.nosql.mapping.Id;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;

/**
 * @author PC
 *
 */
@JsonbPropertyOrder({
    "name",
    "delay",
    "maxDelay",
    "increment",
    "multiplier",
    "maxAttempts",
    "jitter"
})
@Entity
public class RetryDefinition implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Unique retry strategy name
     * (Required)
     * 
     */
    @JsonbProperty("name")
    @Size(min = 1)
    @NotNull
    @Column
    @Id
    private String name;
    /**
     * Time delay between retry attempts (ISO 8601 duration format)
     * 
     */
    @JsonbProperty("delay")
    @Column
    private String delay;
    /**
     * Maximum time delay between retry attempts (ISO 8601 duration format)
     * 
     */
    @JsonbProperty("maxDelay")
    @Column
    private String maxDelay;
    /**
     * Static value by which the delay increases during each attempt (ISO 8601 time format)
     * 
     */
    @JsonbProperty("increment")
    @Column
    private String increment;
    /**
     * Multiplier value by which interval increases during each attempt (ISO 8601 time format)
     * 
     */
    @JsonbProperty("multiplier")
    @Column
    private String multiplier;
    /**
     * Maximum number of retry attempts. Value of 0 means no retries are performed
     * (Required)
     * 
     */
    @JsonbProperty("maxAttempts")
    @NotNull
    @Column
    private String maxAttempts = "0";
    /**
     * Absolute maximum amount of random time added or subtracted from the delay between each retry (ISO 8601 duration format)
     * 
     */
    @JsonbProperty("jitter")
    @DecimalMin("0")
    @DecimalMax("1")
    @Column
    private String jitter;

    /**
     * Unique retry strategy name
     * (Required)
     * 
     */
    @JsonbProperty("name")
    public String getName() {
        return name;
    }

    /**
     * Unique retry strategy name
     * (Required)
     * 
     */
    @JsonbProperty("name")
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Time delay between retry attempts (ISO 8601 duration format)
     * 
     */
    @JsonbProperty("delay")
    public String getDelay() {
        return delay;
    }

    /**
     * Time delay between retry attempts (ISO 8601 duration format)
     * 
     */
    @JsonbProperty("delay")
    public void setDelay(final String delay) {
        this.delay = delay;
    }

    /**
     * Maximum time delay between retry attempts (ISO 8601 duration format)
     * 
     */
    @JsonbProperty("maxDelay")
    public String getMaxDelay() {
        return maxDelay;
    }

    /**
     * Maximum time delay between retry attempts (ISO 8601 duration format)
     * 
     */
    @JsonbProperty("maxDelay")
    public void setMaxDelay(final String maxDelay) {
        this.maxDelay = maxDelay;
    }

    /**
     * Static value by which the delay increases during each attempt (ISO 8601 time format)
     * 
     */
    @JsonbProperty("increment")
    public String getIncrement() {
        return increment;
    }

    /**
     * Static value by which the delay increases during each attempt (ISO 8601 time format)
     * 
     */
    @JsonbProperty("increment")
    public void setIncrement(final String increment) {
        this.increment = increment;
    }

    /**
     * Multiplier value by which interval increases during each attempt (ISO 8601 time format)
     * 
     */
    @JsonbProperty("multiplier")
    public String getMultiplier() {
        return multiplier;
    }

    /**
     * Multiplier value by which interval increases during each attempt (ISO 8601 time format)
     * 
     */
    @JsonbProperty("multiplier")
    public void setMultiplier(final String multiplier) {
        this.multiplier = multiplier;
    }

    /**
     * Maximum number of retry attempts. Value of 0 means no retries are performed
     * (Required)
     * 
     */
    @JsonbProperty("maxAttempts")
    public String getMaxAttempts() {
        return maxAttempts;
    }

    /**
     * Maximum number of retry attempts. Value of 0 means no retries are performed
     * (Required)
     * 
     */
    @JsonbProperty("maxAttempts")
    public void setMaxAttempts(final String maxAttempts) {
        this.maxAttempts = maxAttempts;
    }
    
    /**
     * Absolute maximum amount of random time added or subtracted from the delay between each retry (ISO 8601 duration format)
     * 
     */
    @JsonbProperty("jitter")
    public String getJitter() {
        return jitter;
    }

    /**
     * Absolute maximum amount of random time added or subtracted from the delay between each retry (ISO 8601 duration format)
     * 
     */
    @JsonbProperty("jitter")
    public void setJitter(final String jitter) {
        this.jitter = jitter;
    }
}

package epf.workflow.schema.schedule;

import java.io.Serializable;
import javax.validation.Valid;
import epf.workflow.schema.cron.Cron;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;

/**
 * @author PC
 *
 */
@JsonbPropertyOrder({
    "interval",
    "cron",
    "timezone"
})
@Embeddable
public class Schedule implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Time interval (ISO 8601 format) describing when the workflow starting state is active
     * 
     */
    @JsonbProperty("interval")
    @Column
    private String interval;
    /**
     * Schedule cron definition
     * 
     */
    @JsonbProperty("cron")
    @Valid
    @Column
    private Cron cron;
    /**
     * Timezone name used to evaluate the cron expression. Not used for interval as timezone can be specified there directly. If not specified, should default to local machine timezone.
     * 
     */
    @JsonbProperty("timezone")
    @Column
    private String timezone;

    /**
     * Time interval (ISO 8601 format) describing when the workflow starting state is active
     * 
     */
    @JsonbProperty("interval")
    public String getInterval() {
        return interval;
    }

    /**
     * Time interval (ISO 8601 format) describing when the workflow starting state is active
     * 
     */
    @JsonbProperty("interval")
    public void setInterval(final String interval) {
        this.interval = interval;
    }

    /**
     * Schedule cron definition
     * 
     */
    @JsonbProperty("cron")
    public Cron getCron() {
        return cron;
    }

    /**
     * Schedule cron definition
     * 
     */
    @JsonbProperty("cron")
    public void setCron(final Cron cron) {
        this.cron = cron;
    }

    /**
     * Timezone name used to evaluate the cron expression. Not used for interval as timezone can be specified there directly. If not specified, should default to local machine timezone.
     * 
     */
    @JsonbProperty("timezone")
    public String getTimezone() {
        return timezone;
    }

    /**
     * Timezone name used to evaluate the cron expression. Not used for interval as timezone can be specified there directly. If not specified, should default to local machine timezone.
     * 
     */
    @JsonbProperty("timezone")
    public void setTimezone(final String timezone) {
        this.timezone = timezone;
    }
}

package epf.workflow.schema.states;

import javax.validation.constraints.NotNull;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;

/**
 * @author PC
 *
 */
@JsonbPropertyOrder({
    "duration",
    "usedForCompensation"
})
@Embeddable
public class SleepState extends DefaultState
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Duration (ISO 8601 duration format) to sleep
     * (Required)
     * 
     */
    @JsonbProperty("duration")
    @NotNull
    @Column
    private String duration;
    /**
     * If true, this state is used to compensate another state. Default is false
     * 
     */
    @JsonbProperty("usedForCompensation")
    @Column
    private boolean usedForCompensation = false;

    /**
     * Duration (ISO 8601 duration format) to sleep
     * (Required)
     * 
     */
    @JsonbProperty("duration")
    public String getDuration() {
        return duration;
    }

    /**
     * Duration (ISO 8601 duration format) to sleep
     * (Required)
     * 
     */
    @JsonbProperty("duration")
    public void setDuration(final String duration) {
        this.duration = duration;
    }

    /**
     * If true, this state is used to compensate another state. Default is false
     * 
     */
    @JsonbProperty("usedForCompensation")
    public boolean isUsedForCompensation() {
        return usedForCompensation;
    }

    /**
     * If true, this state is used to compensate another state. Default is false
     * 
     */
    @JsonbProperty("usedForCompensation")
    public void setUsedForCompensation(final boolean usedForCompensation) {
        this.usedForCompensation = usedForCompensation;
    }
}

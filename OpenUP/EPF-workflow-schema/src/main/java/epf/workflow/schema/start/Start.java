package epf.workflow.schema.start;

import java.io.Serializable;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import epf.workflow.schema.schedule.Schedule;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;

/**
 * @author PC
 *
 */
@JsonbPropertyOrder({
    "stateName",
    "schedule"
})
public class Start implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Name of the starting workflow state
     * 
     */
    @JsonbProperty("stateName")
    @Size(min = 1)
    private String stateName;
    /**
     * Start state schedule definition
     * 
     */
    @JsonbProperty("schedule")
    @Valid
    private Schedule schedule;

    /**
     * Name of the starting workflow state
     * 
     */
    @JsonbProperty("stateName")
    public String getStateName() {
        return stateName;
    }

    /**
     * Name of the starting workflow state
     * 
     */
    @JsonbProperty("stateName")
    public void setStateName(final String stateName) {
        this.stateName = stateName;
    }

    /**
     * Start state schedule definition
     * 
     */
    @JsonbProperty("schedule")
    public Schedule getSchedule() {
        return schedule;
    }

    /**
     * Start state schedule definition
     * 
     */
    @JsonbProperty("schedule")
    public void setSchedule(final Schedule schedule) {
        this.schedule = schedule;
    }
}

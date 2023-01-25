package epf.workflow.schema.branches;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.validation.Valid;

/**
 * @author PC
 *
 */
@JsonbPropertyOrder({
    "name",
    "actions",
    "timeouts"
})
public class Branch implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Branch name
     * 
     */
    @JsonbProperty("name")
    private String name;
    /**
     * Actions to be executed in this branch
     * 
     */
    @JsonbProperty("actions")
    @Valid
    private List<Action> actions = new ArrayList<Action>();
    /**
     * Timeouts Definition
     * 
     */
    @JsonbProperty("timeouts")
    @Valid
    private TimeoutsDefinition timeouts;

    /**
     * Branch name
     * 
     */
    @JsonbProperty("name")
    public String getName() {
        return name;
    }

    /**
     * Branch name
     * 
     */
    @JsonbProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public Branch withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Actions to be executed in this branch
     * 
     */
    @JsonbProperty("actions")
    public List<Action> getActions() {
        return actions;
    }

    /**
     * Actions to be executed in this branch
     * 
     */
    @JsonbProperty("actions")
    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public Branch withActions(List<Action> actions) {
        this.actions = actions;
        return this;
    }

    /**
     * Timeouts Definition
     * 
     */
    @JsonbProperty("timeouts")
    public TimeoutsDefinition getTimeouts() {
        return timeouts;
    }

    /**
     * Timeouts Definition
     * 
     */
    @JsonbProperty("timeouts")
    public void setTimeouts(TimeoutsDefinition timeouts) {
        this.timeouts = timeouts;
    }

    public Branch withTimeouts(TimeoutsDefinition timeouts) {
        this.timeouts = timeouts;
        return this;
    }
}
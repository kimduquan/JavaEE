package epf.workflow.schema.branches;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.validation.Valid;
import epf.workflow.schema.actions.Action;
import epf.workflow.schema.timeouts.TimeoutsDefinition;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@JsonbPropertyOrder({
    "name",
    "actions",
    "timeouts"
})
@Embeddable
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
    @Column
    private String name;
    /**
     * Actions to be executed in this branch
     * 
     */
    @JsonbProperty("actions")
    @Valid
    @Column
    private List<Action> actions = new ArrayList<Action>();
    /**
     * Timeouts Definition
     * 
     */
    @JsonbProperty("timeouts")
    @Valid
    @Column
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
    public void setName(final String name) {
        this.name = name;
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
    public void setActions(final List<Action> actions) {
        this.actions = actions;
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
    public void setTimeouts(final TimeoutsDefinition timeouts) {
        this.timeouts = timeouts;
    }
}
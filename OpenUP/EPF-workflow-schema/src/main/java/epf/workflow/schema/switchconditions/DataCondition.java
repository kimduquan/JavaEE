package epf.workflow.schema.switchconditions;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import epf.workflow.schema.end.End;
import epf.workflow.schema.interfaces.SwitchCondition;
import epf.workflow.schema.transitions.Transition;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;

/**
 * @author PC
 *
 */
@JsonbPropertyOrder({
    "name",
    "condition",
    "transition",
    "end"
})
public class DataCondition extends SwitchCondition
{

    /**
     * Data condition name
     * 
     */
    @JsonbProperty("name")
    private String name;
    /**
     * Workflow expression evaluated against state data. True if results are not empty
     * (Required)
     * 
     */
    @JsonbProperty("condition")
    @NotNull
    private String condition;
    /**
     * 
     * (Required)
     * 
     */
    @JsonbProperty("transition")
    @Valid
    @NotNull
    private Transition transition;
    /**
     * State end definition
     * 
     */
    @JsonbProperty("end")
    @Valid
    private End end;

    /**
     * Data condition name
     * 
     */
    @JsonbProperty("name")
    public String getName() {
        return name;
    }

    /**
     * Data condition name
     * 
     */
    @JsonbProperty("name")
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Workflow expression evaluated against state data. True if results are not empty
     * (Required)
     * 
     */
    @JsonbProperty("condition")
    public String getCondition() {
        return condition;
    }

    /**
     * Workflow expression evaluated against state data. True if results are not empty
     * (Required)
     * 
     */
    @JsonbProperty("condition")
    public void setCondition(final String condition) {
        this.condition = condition;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonbProperty("transition")
    public Transition getTransition() {
        return transition;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonbProperty("transition")
    public void setTransition(final Transition transition) {
        this.transition = transition;
    }

    /**
     * State end definition
     * 
     */
    @JsonbProperty("end")
    public End getEnd() {
        return end;
    }

    /**
     * State end definition
     * 
     */
    @JsonbProperty("end")
    public void setEnd(final End end) {
        this.end = end;
    }
}

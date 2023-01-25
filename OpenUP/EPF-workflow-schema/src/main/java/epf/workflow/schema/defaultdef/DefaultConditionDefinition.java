package epf.workflow.schema.defaultdef;

import java.io.Serializable;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.validation.Valid;
import epf.workflow.schema.end.End;
import epf.workflow.schema.transitions.Transition;

/**
 * @author PC
 *
 */
@JsonbPropertyOrder({
    "transition",
    "end"
})
public class DefaultConditionDefinition implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonbProperty("transition")
    @Valid
    private Transition transition;
    /**
     * State end definition
     * 
     */
    @JsonbProperty("end")
    @Valid
    private End end;

    @JsonbProperty("transition")
    public Transition getTransition() {
        return transition;
    }

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

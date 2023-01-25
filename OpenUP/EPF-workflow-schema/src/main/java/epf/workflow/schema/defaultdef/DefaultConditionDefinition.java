package epf.workflow.schema.defaultdef;

import java.io.Serializable;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.validation.Valid;

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
    public void setTransition(Transition transition) {
        this.transition = transition;
    }

    public DefaultConditionDefinition withTransition(Transition transition) {
        this.transition = transition;
        return this;
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
    public void setEnd(End end) {
        this.end = end;
    }

    public DefaultConditionDefinition withEnd(End end) {
        this.end = end;
        return this;
    }

}

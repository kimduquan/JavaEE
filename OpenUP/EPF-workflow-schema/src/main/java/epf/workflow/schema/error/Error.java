package epf.workflow.schema.error;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import epf.workflow.schema.end.End;
import epf.workflow.schema.transitions.Transition;

/**
 * @author PC
 *
 */
@JsonbPropertyOrder({
    "errorRef",
    "errorRefs",
    "transition",
    "end"
})
public class Error implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Reference to a unique workflow error definition. Used of errorRefs is not used
     * 
     */
    @JsonbProperty("errorRef")
    @Size(min = 1)
    private String errorRef;
    /**
     * References one or more workflow error definitions. Used if errorRef is not used
     * 
     */
    @JsonbProperty("errorRefs")
    @Size(min = 1)
    @Valid
    private List<String> errorRefs = new ArrayList<String>();
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
     * Reference to a unique workflow error definition. Used of errorRefs is not used
     * 
     */
    @JsonbProperty("errorRef")
    public String getErrorRef() {
        return errorRef;
    }

    /**
     * Reference to a unique workflow error definition. Used of errorRefs is not used
     * 
     */
    @JsonbProperty("errorRef")
    public void setErrorRef(final String errorRef) {
        this.errorRef = errorRef;
    }

    /**
     * References one or more workflow error definitions. Used if errorRef is not used
     * 
     */
    @JsonbProperty("errorRefs")
    public List<String> getErrorRefs() {
        return errorRefs;
    }

    /**
     * References one or more workflow error definitions. Used if errorRef is not used
     * 
     */
    @JsonbProperty("errorRefs")
    public void setErrorRefs(final List<String> errorRefs) {
        this.errorRefs = errorRefs;
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
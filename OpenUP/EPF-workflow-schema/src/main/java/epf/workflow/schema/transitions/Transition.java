package epf.workflow.schema.transitions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import epf.workflow.schema.produce.ProduceEvent;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;

/**
 * @author PC
 *
 */
@JsonbPropertyOrder({
    "produceEvents",
    "nextState",
    "compensate"
})
public class Transition implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Array of events to be produced
     * 
     */
    @JsonbProperty("produceEvents")
    @Valid
    private List<ProduceEvent> produceEvents = new ArrayList<ProduceEvent>();
    /**
     * State to transition to next
     * (Required)
     * 
     */
    @JsonbProperty("nextState")
    @Size(min = 1)
    @NotNull
    private String nextState;
    /**
     * If set to true, triggers workflow compensation before this transition is taken. Default is false
     * 
     */
    @JsonbProperty("compensate")
    private boolean compensate = false;

    /**
     * Array of events to be produced
     * 
     */
    @JsonbProperty("produceEvents")
    public List<ProduceEvent> getProduceEvents() {
        return produceEvents;
    }

    /**
     * Array of events to be produced
     * 
     */
    @JsonbProperty("produceEvents")
    public void setProduceEvents(final List<ProduceEvent> produceEvents) {
        this.produceEvents = produceEvents;
    }

    /**
     * State to transition to next
     * (Required)
     * 
     */
    @JsonbProperty("nextState")
    public String getNextState() {
        return nextState;
    }

    /**
     * State to transition to next
     * (Required)
     * 
     */
    @JsonbProperty("nextState")
    public void setNextState(final String nextState) {
        this.nextState = nextState;
    }

    /**
     * If set to true, triggers workflow compensation before this transition is taken. Default is false
     * 
     */
    @JsonbProperty("compensate")
    public boolean isCompensate() {
        return compensate;
    }

    /**
     * If set to true, triggers workflow compensation before this transition is taken. Default is false
     * 
     */
    @JsonbProperty("compensate")
    public void setCompensate(final boolean compensate) {
        this.compensate = compensate;
    }
}
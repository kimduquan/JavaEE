package epf.workflow.schema.end;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.validation.Valid;
import epf.workflow.schema.produce.ProduceEvent;

/**
 * @author PC
 *
 */
@JsonbPropertyOrder({
    "terminate",
    "produceEvents",
    "compensate",
    "continueAs"
})
public class End implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * If true, completes all execution flows in the given workflow instance
     * 
     */
    @JsonbProperty("terminate")
    private boolean terminate = false;
    /**
     * Array of events to be produced
     * 
     */
    @JsonbProperty("produceEvents")
    @Valid
    private List<ProduceEvent> produceEvents = new ArrayList<ProduceEvent>();
    /**
     * If set to true, triggers workflow compensation when before workflow executin completes. Default is false
     * 
     */
    @JsonbProperty("compensate")
    private boolean compensate = false;
    /**
     * End definition continue as
     * 
     */
    @JsonbProperty("continueAs")
    @Valid
    private ContinueAs continueAs;

    /**
     * If true, completes all execution flows in the given workflow instance
     * 
     */
    @JsonbProperty("terminate")
    public boolean isTerminate() {
        return terminate;
    }

    /**
     * If true, completes all execution flows in the given workflow instance
     * 
     */
    @JsonbProperty("terminate")
    public void setTerminate(final boolean terminate) {
        this.terminate = terminate;
    }

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
     * If set to true, triggers workflow compensation when before workflow executin completes. Default is false
     * 
     */
    @JsonbProperty("compensate")
    public boolean isCompensate() {
        return compensate;
    }

    /**
     * If set to true, triggers workflow compensation when before workflow executin completes. Default is false
     * 
     */
    @JsonbProperty("compensate")
    public void setCompensate(final boolean compensate) {
        this.compensate = compensate;
    }

    /**
     * End definition continue as
     * 
     */
    @JsonbProperty("continueAs")
    public ContinueAs getContinueAs() {
        return continueAs;
    }

    /**
     * End definition continue as
     * 
     */
    @JsonbProperty("continueAs")
    public void setContinueAs(final ContinueAs continueAs) {
        this.continueAs = continueAs;
    }
}

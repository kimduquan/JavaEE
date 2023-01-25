package epf.workflow.schema.end;

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
    public void setTerminate(boolean terminate) {
        this.terminate = terminate;
    }

    public End withTerminate(boolean terminate) {
        this.terminate = terminate;
        return this;
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
    public void setProduceEvents(List<ProduceEvent> produceEvents) {
        this.produceEvents = produceEvents;
    }

    public End withProduceEvents(List<ProduceEvent> produceEvents) {
        this.produceEvents = produceEvents;
        return this;
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
    public void setCompensate(boolean compensate) {
        this.compensate = compensate;
    }

    public End withCompensate(boolean compensate) {
        this.compensate = compensate;
        return this;
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
    public void setContinueAs(ContinueAs continueAs) {
        this.continueAs = continueAs;
    }

    public End withContinueAs(ContinueAs continueAs) {
        this.continueAs = continueAs;
        return this;
    }

}

package epf.workflow.schema.states;

import javax.validation.Valid;
import epf.workflow.schema.actions.Action;
import epf.workflow.schema.filters.EventDataFilter;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;

/**
 * @author PC
 *
 */
@JsonbPropertyOrder({
    "action",
    "eventRef",
    "eventDataFilter",
    "usedForCompensation"
})
public class CallbackState extends DefaultState
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Action Definition
     * 
     */
    @JsonbProperty("action")
    @Valid
    private Action action;
    /**
     * References an unique callback event name in the defined workflow events
     * 
     */
    @JsonbProperty("eventRef")
    private String eventRef;
    @JsonbProperty("eventDataFilter")
    @Valid
    private EventDataFilter eventDataFilter;
    /**
     * If true, this state is used to compensate another state. Default is false
     * 
     */
    @JsonbProperty("usedForCompensation")
    private boolean usedForCompensation = false;

    /**
     * Action Definition
     * 
     */
    @JsonbProperty("action")
    public Action getAction() {
        return action;
    }

    /**
     * Action Definition
     * 
     */
    @JsonbProperty("action")
    public void setAction(final Action action) {
        this.action = action;
    }

    /**
     * References an unique callback event name in the defined workflow events
     * 
     */
    @JsonbProperty("eventRef")
    public String getEventRef() {
        return eventRef;
    }

    /**
     * References an unique callback event name in the defined workflow events
     * 
     */
    @JsonbProperty("eventRef")
    public void setEventRef(final String eventRef) {
        this.eventRef = eventRef;
    }

    @JsonbProperty("eventDataFilter")
    public EventDataFilter getEventDataFilter() {
        return eventDataFilter;
    }

    @JsonbProperty("eventDataFilter")
    public void setEventDataFilter(final EventDataFilter eventDataFilter) {
        this.eventDataFilter = eventDataFilter;
    }

    /**
     * If true, this state is used to compensate another state. Default is false
     * 
     */
    @JsonbProperty("usedForCompensation")
    public boolean isUsedForCompensation() {
        return usedForCompensation;
    }

    /**
     * If true, this state is used to compensate another state. Default is false
     * 
     */
    @JsonbProperty("usedForCompensation")
    public void setUsedForCompensation(final boolean usedForCompensation) {
        this.usedForCompensation = usedForCompensation;
    }
}

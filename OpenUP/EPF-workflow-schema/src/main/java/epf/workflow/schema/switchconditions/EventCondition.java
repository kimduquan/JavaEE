package epf.workflow.schema.switchconditions;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import epf.workflow.schema.end.End;
import epf.workflow.schema.filters.EventDataFilter;
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
    "eventRef",
    "eventDataFilter",
    "transition",
    "end"
})
public class EventCondition extends SwitchCondition
{

    /**
     * Event condition name
     * 
     */
    @JsonbProperty("name")
    private String name;
    /**
     * References an unique event name in the defined workflow events
     * (Required)
     * 
     */
    @JsonbProperty("eventRef")
    @NotNull
    private String eventRef;
    /**
     * 
     */
    @JsonbProperty("eventDataFilter")
    @Valid
    private EventDataFilter eventDataFilter;
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
     * Event condition name
     * 
     */
    @JsonbProperty("name")
    public String getName() {
        return name;
    }

    /**
     * Event condition name
     * 
     */
    @JsonbProperty("name")
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * References an unique event name in the defined workflow events
     * (Required)
     * 
     */
    @JsonbProperty("eventRef")
    public String getEventRef() {
        return eventRef;
    }

    /**
     * References an unique event name in the defined workflow events
     * (Required)
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
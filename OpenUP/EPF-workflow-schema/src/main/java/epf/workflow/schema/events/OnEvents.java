package epf.workflow.schema.events;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import epf.workflow.schema.actions.Action;
import epf.workflow.schema.filters.EventDataFilter;
import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;

/**
 * @author PC
 *
 */
@JsonbPropertyOrder({
    "eventRefs",
    "actionMode",
    "actions",
    "eventDataFilter"
})
public class OnEvents implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * References one or more unique event names in the defined workflow events
     * (Required)
     * 
     */
    @JsonbProperty("eventRefs")
    @Valid
    @NotNull
    private List<String> eventRefs = new ArrayList<String>();
    /**
     * Specifies how actions are to be performed (in sequence of parallel)
     * 
     */
    @JsonbProperty("actionMode")
    private OnEvents.ActionMode actionMode = OnEvents.ActionMode.fromValue("sequential");
    /**
     * Actions to be performed.
     * (Required)
     * 
     */
    @JsonbProperty("actions")
    @Valid
    @NotNull
    private List<Action> actions = new ArrayList<Action>();
    /**
     * 
     */
    @JsonbProperty("eventDataFilter")
    @Valid
    private EventDataFilter eventDataFilter;

    /**
     * References one or more unique event names in the defined workflow events
     * (Required)
     * 
     */
    @JsonbProperty("eventRefs")
    public List<String> getEventRefs() {
        return eventRefs;
    }

    /**
     * References one or more unique event names in the defined workflow events
     * (Required)
     * 
     */
    @JsonbProperty("eventRefs")
    public void setEventRefs(final List<String> eventRefs) {
        this.eventRefs = eventRefs;
    }

    /**
     * Specifies how actions are to be performed (in sequence of parallel)
     * 
     */
    @JsonbProperty("actionMode")
    public OnEvents.ActionMode getActionMode() {
        return actionMode;
    }

    /**
     * Specifies how actions are to be performed (in sequence of parallel)
     * 
     */
    @JsonbProperty("actionMode")
    public void setActionMode(final OnEvents.ActionMode actionMode) {
        this.actionMode = actionMode;
    }

    /**
     * Actions to be performed.
     * (Required)
     * 
     */
    @JsonbProperty("actions")
    public List<Action> getActions() {
        return actions;
    }

    /**
     * Actions to be performed.
     * (Required)
     * 
     */
    @JsonbProperty("actions")
    public void setActions(final List<Action> actions) {
        this.actions = actions;
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
     * @author PC
     *
     */
    public enum ActionMode {

        SEQUENTIAL("sequential"),
        PARALLEL("parallel");
        /**
         * 
         */
        private final String value;
        /**
         * 
         */
        private final static Map<String, OnEvents.ActionMode> CONSTANTS = new HashMap<String, OnEvents.ActionMode>();

        static {
            for (OnEvents.ActionMode c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        /**
         * @param value
         */
        ActionMode(final String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        @JsonbCreator
        public static OnEvents.ActionMode fromValue(final String value) {
        	final OnEvents.ActionMode constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }
    }
}
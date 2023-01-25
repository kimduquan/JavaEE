package epf.workflow.schema.states;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import epf.workflow.schema.end.End;
import epf.workflow.schema.filters.StateDataFilter;
import epf.workflow.schema.interfaces.State;
import epf.workflow.schema.timeouts.TimeoutsDefinition;
import epf.workflow.schema.transitions.Transition;
import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;

/**
 * @author PC
 *
 */
@JsonbPropertyOrder({
    "id",
    "name",
    "type",
    "end",
    "stateDataFilter",
    "metadata",
    "transition",
    "onErrors",
    "compensatedBy",
    "timeouts"
})
public class DefaultState extends State
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * State unique identifier
     * 
     */
    @JsonbProperty("id")
    @Size(min = 1)
    private String id;
    /**
     * Unique name of the state
     * (Required)
     * 
     */
    @JsonbProperty("name")
    @Size(min = 1)
    @NotNull
    private String name;
    /**
     * State type
     * (Required)
     * 
     */
    @JsonbProperty("type")
    @NotNull
    private DefaultState.Type type;
    /**
     * State end definition
     * 
     */
    @JsonbProperty("end")
    @Valid
    private End end;
    /**
     * 
     */
    @JsonbProperty("stateDataFilter")
    @Valid
    private StateDataFilter stateDataFilter;
    /**
     * Metadata
     * 
     */
    @JsonbProperty("metadata")
    @Valid
    private Map<String, String> metadata;
    /**
     * 
     */
    @JsonbProperty("transition")
    @Valid
    private Transition transition;
    /**
     * State error handling definitions
     * 
     */
    @JsonbProperty("onErrors")
    @Valid
    private List<Error> onErrors = new ArrayList<Error>();
    /**
     * Unique Name of a workflow state which is responsible for compensation of this state
     * 
     */
    @JsonbProperty("compensatedBy")
    @Size(min = 1)
    private String compensatedBy;
    /**
     * Timeouts Definition
     * 
     */
    @JsonbProperty("timeouts")
    @Valid
    private TimeoutsDefinition timeouts;

    /**
     * State unique identifier
     * 
     */
    @JsonbProperty("id")
    public String getId() {
        return id;
    }

    /**
     * State unique identifier
     * 
     */
    @JsonbProperty("id")
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * Unique name of the state
     * (Required)
     * 
     */
    @JsonbProperty("name")
    public String getName() {
        return name;
    }

    /**
     * Unique name of the state
     * (Required)
     * 
     */
    @JsonbProperty("name")
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * State type
     * (Required)
     * 
     */
    @JsonbProperty("type")
    public DefaultState.Type getType() {
        return type;
    }

    /**
     * State type
     * (Required)
     * 
     */
    @JsonbProperty("type")
    public void setType(final DefaultState.Type type) {
        this.type = type;
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

    @JsonbProperty("stateDataFilter")
    public StateDataFilter getStateDataFilter() {
        return stateDataFilter;
    }

    @JsonbProperty("stateDataFilter")
    public void setStateDataFilter(final StateDataFilter stateDataFilter) {
        this.stateDataFilter = stateDataFilter;
    }

    /**
     * Metadata
     * 
     */
    @JsonbProperty("metadata")
    public Map<String, String> getMetadata() {
        return metadata;
    }

    /**
     * Metadata
     * 
     */
    @JsonbProperty("metadata")
    public void setMetadata(final Map<String, String> metadata) {
        this.metadata = metadata;
    }

    @JsonbProperty("transition")
    public Transition getTransition() {
        return transition;
    }

    @JsonbProperty("transition")
    public void setTransition(final Transition transition) {
        this.transition = transition;
    }

    /**
     * State error handling definitions
     * 
     */
    @JsonbProperty("onErrors")
    public List<Error> getOnErrors() {
        return onErrors;
    }

    /**
     * State error handling definitions
     * 
     */
    @JsonbProperty("onErrors")
    public void setOnErrors(final List<Error> onErrors) {
        this.onErrors = onErrors;
    }

    /**
     * Unique Name of a workflow state which is responsible for compensation of this state
     * 
     */
    @JsonbProperty("compensatedBy")
    public String getCompensatedBy() {
        return compensatedBy;
    }

    /**
     * Unique Name of a workflow state which is responsible for compensation of this state
     * 
     */
    @JsonbProperty("compensatedBy")
    public void setCompensatedBy(final String compensatedBy) {
        this.compensatedBy = compensatedBy;
    }

    /**
     * Timeouts Definition
     * 
     */
    @JsonbProperty("timeouts")
    public TimeoutsDefinition getTimeouts() {
        return timeouts;
    }

    /**
     * Timeouts Definition
     * 
     */
    @JsonbProperty("timeouts")
    public void setTimeouts(final TimeoutsDefinition timeouts) {
        this.timeouts = timeouts;
    }

    /**
     * @author PC
     *
     */
    public enum Type {

        EVENT("event"),
        OPERATION("operation"),
        SWITCH("switch"),
        SLEEP("sleep"),
        PARALLEL("parallel"),
        SUBFLOW("subflow"),
        INJECT("inject"),
        FOREACH("foreach"),
        CALLBACK("callback");
        /**
         * 
         */
        private final String value;
        /**
         * 
         */
        private final static Map<String, DefaultState.Type> CONSTANTS = new HashMap<String, DefaultState.Type>();

        static {
            for (DefaultState.Type c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        Type(final String value) {
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
        public static DefaultState.Type fromValue(final String value) {
        	final DefaultState.Type constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}

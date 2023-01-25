package epf.workflow.schema.states;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import epf.workflow.schema.actions.Action;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;
import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;

/**
 * @author PC
 *
 */
@JsonbPropertyOrder({
    "actionMode",
    "actions",
    "usedForCompensation"
})
@Embeddable
public class OperationState extends DefaultState
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Specifies whether functions are executed in sequence or in parallel.
     * (Required)
     * 
     */
    @JsonbProperty("actionMode")
    @NotNull
    @Column
    private OperationState.ActionMode actionMode;
    /**
     * Actions Definitions
     * (Required)
     * 
     */
    @JsonbProperty("actions")
    @Valid
    @NotNull
    @Column
    private List<Action> actions = new ArrayList<Action>();
    /**
     * If true, this state is used to compensate another state. Default is false
     * 
     */
    @JsonbProperty("usedForCompensation")
    @Column
    private boolean usedForCompensation = false;

    /**
     * Specifies whether functions are executed in sequence or in parallel.
     * (Required)
     * 
     */
    @JsonbProperty("actionMode")
    public OperationState.ActionMode getActionMode() {
        return actionMode;
    }

    /**
     * Specifies whether functions are executed in sequence or in parallel.
     * (Required)
     * 
     */
    @JsonbProperty("actionMode")
    public void setActionMode(final OperationState.ActionMode actionMode) {
        this.actionMode = actionMode;
    }

    /**
     * Actions Definitions
     * (Required)
     * 
     */
    @JsonbProperty("actions")
    public List<Action> getActions() {
        return actions;
    }

    /**
     * Actions Definitions
     * (Required)
     * 
     */
    @JsonbProperty("actions")
    public void setActions(final List<Action> actions) {
        this.actions = actions;
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

    /**
     * @author PC
     *
     */
    @Embeddable
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
        private final static Map<String, OperationState.ActionMode> CONSTANTS = new HashMap<String, OperationState.ActionMode>();

        static {
            for (OperationState.ActionMode c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

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
        public static OperationState.ActionMode fromValue(final String value) {
        	final OperationState.ActionMode constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }
    }
}

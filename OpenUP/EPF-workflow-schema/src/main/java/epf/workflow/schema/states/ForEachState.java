package epf.workflow.schema.states;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import epf.workflow.schema.actions.Action;

/**
 * @author PC
 *
 */
@JsonbPropertyOrder({
    "inputCollection",
    "outputCollection",
    "iterationParam",
    "batchSize",
    "actions",
    "usedForCompensation",
    "mode"
})
public class ForEachState extends DefaultState
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Workflow expression selecting an array element of the states data
     * 
     */
    @JsonbProperty("inputCollection")
    private String inputCollection;
    /**
     * Workflow expression specifying an array element of the states data to add the results of each iteration
     * 
     */
    @JsonbProperty("outputCollection")
    private String outputCollection;
    /**
     * Name of the iteration parameter that can be referenced in actions/workflow. For each parallel iteration, this param should contain an unique element of the inputCollection array
     * 
     */
    @JsonbProperty("iterationParam")
    private String iterationParam;
    /**
     * Specifies how many iterations may run in parallel at the same time. Used if 'mode' property is set to 'parallel' (default)
     * 
     */
    @JsonbProperty("batchSize")
    @DecimalMin("0")
    private int batchSize = 0;
    /**
     * Actions to be executed for each of the elements of inputCollection
     * 
     */
    @JsonbProperty("actions")
    @Valid
    private List<Action> actions = new ArrayList<Action>();
    /**
     * If true, this state is used to compensate another state. Default is false
     * 
     */
    @JsonbProperty("usedForCompensation")
    private boolean usedForCompensation = false;
    /**
     * Specifies how iterations are to be performed (sequentially or in parallel)
     * 
     */
    @JsonbProperty("mode")
    private ForEachState.Mode mode = ForEachState.Mode.fromValue("parallel");

    /**
     * Workflow expression selecting an array element of the states data
     * 
     */
    @JsonbProperty("inputCollection")
    public String getInputCollection() {
        return inputCollection;
    }

    /**
     * Workflow expression selecting an array element of the states data
     * 
     */
    @JsonbProperty("inputCollection")
    public void setInputCollection(final String inputCollection) {
        this.inputCollection = inputCollection;
    }

    /**
     * Workflow expression specifying an array element of the states data to add the results of each iteration
     * 
     */
    @JsonbProperty("outputCollection")
    public String getOutputCollection() {
        return outputCollection;
    }

    /**
     * Workflow expression specifying an array element of the states data to add the results of each iteration
     * 
     */
    @JsonbProperty("outputCollection")
    public void setOutputCollection(final String outputCollection) {
        this.outputCollection = outputCollection;
    }

    /**
     * Name of the iteration parameter that can be referenced in actions/workflow. For each parallel iteration, this param should contain an unique element of the inputCollection array
     * 
     */
    @JsonbProperty("iterationParam")
    public String getIterationParam() {
        return iterationParam;
    }

    /**
     * Name of the iteration parameter that can be referenced in actions/workflow. For each parallel iteration, this param should contain an unique element of the inputCollection array
     * 
     */
    @JsonbProperty("iterationParam")
    public void setIterationParam(final String iterationParam) {
        this.iterationParam = iterationParam;
    }

    /**
     * Specifies how many iterations may run in parallel at the same time. Used if 'mode' property is set to 'parallel' (default)
     * 
     */
    @JsonbProperty("batchSize")
    public int getBatchSize() {
        return batchSize;
    }

    /**
     * Specifies how many iterations may run in parallel at the same time. Used if 'mode' property is set to 'parallel' (default)
     * 
     */
    @JsonbProperty("batchSize")
    public void setBatchSize(final int batchSize) {
        this.batchSize = batchSize;
    }

    /**
     * Actions to be executed for each of the elements of inputCollection
     * 
     */
    @JsonbProperty("actions")
    public List<Action> getActions() {
        return actions;
    }

    /**
     * Actions to be executed for each of the elements of inputCollection
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
     * Specifies how iterations are to be performed (sequentially or in parallel)
     * 
     */
    @JsonbProperty("mode")
    public ForEachState.Mode getMode() {
        return mode;
    }

    /**
     * Specifies how iterations are to be performed (sequentially or in parallel)
     * 
     */
    @JsonbProperty("mode")
    public void setMode(final ForEachState.Mode mode) {
        this.mode = mode;
    }

    /**
     * @author PC
     *
     */
    public enum Mode {

        SEQUENTIAL("sequential"),
        PARALLEL("parallel");
        /**
         * 
         */
        private final String value;
        /**
         * 
         */
        private final static Map<String, ForEachState.Mode> CONSTANTS = new HashMap<String, ForEachState.Mode>();

        static {
            for (ForEachState.Mode c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        /**
         * @param value
         */
        Mode(final String value) {
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
        public static ForEachState.Mode fromValue(final String value) {
        	final ForEachState.Mode constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }
    }
}

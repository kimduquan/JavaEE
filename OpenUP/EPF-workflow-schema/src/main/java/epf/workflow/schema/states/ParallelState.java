package epf.workflow.schema.states;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import epf.workflow.schema.branches.Branch;
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
    "branches",
    "completionType",
    "numCompleted",
    "usedForCompensation"
})
@Embeddable
public class ParallelState extends DefaultState
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Branch Definitions
     * (Required)
     * 
     */
    @JsonbProperty("branches")
    @Valid
    @NotNull
    @Column
    private List<Branch> branches = new ArrayList<Branch>();
    /**
     * Option types on how to complete branch execution.
     * 
     */
    @JsonbProperty("completionType")
    @Column
    private ParallelState.CompletionType completionType = ParallelState.CompletionType.fromValue("allOf");
    /**
     * Used when completionType is set to 'atLeast' to specify the minimum number of branches that must complete before the state will transition.
     * 
     */
    @JsonbProperty("numCompleted")
    @Column
    private String numCompleted = "0";
    /**
     * If true, this state is used to compensate another state. Default is false
     * 
     */
    @JsonbProperty("usedForCompensation")
    @Column
    private boolean usedForCompensation = false;

    /**
     * Branch Definitions
     * (Required)
     * 
     */
    @JsonbProperty("branches")
    public List<Branch> getBranches() {
        return branches;
    }

    /**
     * Branch Definitions
     * (Required)
     * 
     */
    @JsonbProperty("branches")
    public void setBranches(final List<Branch> branches) {
        this.branches = branches;
    }

    /**
     * Option types on how to complete branch execution.
     * 
     */
    @JsonbProperty("completionType")
    public ParallelState.CompletionType getCompletionType() {
        return completionType;
    }

    /**
     * Option types on how to complete branch execution.
     * 
     */
    @JsonbProperty("completionType")
    public void setCompletionType(final ParallelState.CompletionType completionType) {
        this.completionType = completionType;
    }

    /**
     * Used when completionType is set to 'atLeast' to specify the minimum number of branches that must complete before the state will transition.
     * 
     */
    @JsonbProperty("numCompleted")
    public String getNumCompleted() {
        return numCompleted;
    }

    /**
     * Used when completionType is set to 'atLeast' to specify the minimum number of branches that must complete before the state will transition.
     * 
     */
    @JsonbProperty("numCompleted")
    public void setNumCompleted(final String numCompleted) {
        this.numCompleted = numCompleted;
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
    public enum CompletionType {

        ALL_OF("allOf"),
        AT_LEAST("atLeast");
        /**
         * 
         */
        private final String value;
        /**
         * 
         */
        private final static Map<String, ParallelState.CompletionType> CONSTANTS = new HashMap<String, ParallelState.CompletionType>();

        static {
            for (ParallelState.CompletionType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        CompletionType(final String value) {
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
        public static ParallelState.CompletionType fromValue(final String value) {
        	final ParallelState.CompletionType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }
    }
}

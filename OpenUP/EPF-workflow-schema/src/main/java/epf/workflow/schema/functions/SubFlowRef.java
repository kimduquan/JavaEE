package epf.workflow.schema.functions;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;

/**
 * @author PC
 *
 */
@JsonbPropertyOrder({
    "workflowId",
    "version",
    "onParentComplete",
    "invoke"
})
public class SubFlowRef implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Unique id of the sub-workflow to be invoked
     * (Required)
     * 
     */
    @JsonbProperty("workflowId")
    @NotNull
    private String workflowId;
    /**
     * Version of the sub-workflow to be invoked
     * 
     */
    @JsonbProperty("version")
    @Size(min = 1)
    private String version;
    /**
     * If invoke is 'async', specifies how subflow execution should behave when parent workflow completes. Default is 'terminate'
     * 
     */
    @JsonbProperty("onParentComplete")
    private SubFlowRef.OnParentComplete onParentComplete = SubFlowRef.OnParentComplete.fromValue("terminate");
    /**
     * Specifies if the function should be invoked sync or async. Default is sync.
     * 
     */
    @JsonbProperty("invoke")
    private SubFlowRef.Invoke invoke = SubFlowRef.Invoke.fromValue("sync");

    /**
     * Unique id of the sub-workflow to be invoked
     * (Required)
     * 
     */
    @JsonbProperty("workflowId")
    public String getWorkflowId() {
        return workflowId;
    }

    /**
     * Unique id of the sub-workflow to be invoked
     * (Required)
     * 
     */
    @JsonbProperty("workflowId")
    public void setWorkflowId(final String workflowId) {
        this.workflowId = workflowId;
    }

    /**
     * Version of the sub-workflow to be invoked
     * 
     */
    @JsonbProperty("version")
    public String getVersion() {
        return version;
    }

    /**
     * Version of the sub-workflow to be invoked
     * 
     */
    @JsonbProperty("version")
    public void setVersion(final String version) {
        this.version = version;
    }

    /**
     * If invoke is 'async', specifies how subflow execution should behave when parent workflow completes. Default is 'terminate'
     * 
     */
    @JsonbProperty("onParentComplete")
    public SubFlowRef.OnParentComplete getOnParentComplete() {
        return onParentComplete;
    }

    /**
     * If invoke is 'async', specifies how subflow execution should behave when parent workflow completes. Default is 'terminate'
     * 
     */
    @JsonbProperty("onParentComplete")
    public void setOnParentComplete(final SubFlowRef.OnParentComplete onParentComplete) {
        this.onParentComplete = onParentComplete;
    }

    /**
     * Specifies if the function should be invoked sync or async. Default is sync.
     * 
     */
    @JsonbProperty("invoke")
    public SubFlowRef.Invoke getInvoke() {
        return invoke;
    }

    /**
     * Specifies if the function should be invoked sync or async. Default is sync.
     * 
     */
    @JsonbProperty("invoke")
    public void setInvoke(final SubFlowRef.Invoke invoke) {
        this.invoke = invoke;
    }

    /**
     * @author PC
     *
     */
    public enum Invoke {

        SYNC("sync"),
        ASYNC("async");
        /**
         * 
         */
        private final String value;
        /**
         * 
         */
        private final static Map<String, SubFlowRef.Invoke> CONSTANTS = new HashMap<String, SubFlowRef.Invoke>();

        static {
            for (SubFlowRef.Invoke c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        /**
         * @param value
         */
        Invoke(final String value) {
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
        public static SubFlowRef.Invoke fromValue(final String value) {
        	final SubFlowRef.Invoke constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    /**
     * @author PC
     *
     */
    public enum OnParentComplete {

        CONTINUE("continue"),
        TERMINATE("terminate");
        /**
         * 
         */
        private final String value;
        /**
         * 
         */
        private final static Map<String, SubFlowRef.OnParentComplete> CONSTANTS = new HashMap<String, SubFlowRef.OnParentComplete>();

        static {
            for (SubFlowRef.OnParentComplete c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        /**
         * @param value
         */
        OnParentComplete(final String value) {
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
        public static SubFlowRef.OnParentComplete fromValue(final String value) {
        	final SubFlowRef.OnParentComplete constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}

package epf.workflow.schema.events;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;

/**
 * @author PC
 *
 */
@JsonbPropertyOrder({
    "triggerEventRef",
    "resultEventRef",
    "resultEventTimeout",
    "data",
    "contextAttributes",
    "invoke"
})
public class EventRef implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Reference to the unique name of a 'produced' event definition
     * (Required)
     * 
     */
    @JsonbProperty("triggerEventRef")
    @NotNull
    private String triggerEventRef;
    /**
     * Reference to the unique name of a 'consumed' event definition
     * (Required)
     * 
     */
    @JsonbProperty("resultEventRef")
    @NotNull
    private String resultEventRef;
    /**
     * Maximum amount of time (ISO 8601 format) to wait for the result event. If not defined it should default to the actionExecutionTimeout
     * 
     */
    @JsonbProperty("resultEventTimeout")
    private String resultEventTimeout;
    /**
     * Expression which selects parts of the states data output to become the data of the produced event.
     * 
     */
    @JsonbProperty("data")
    private String data;
    /**
     * Add additional extension context attributes to the produced event
     * 
     */
    @JsonbProperty("contextAttributes")
    @Valid
    private Map<String, String> contextAttributes;
    /**
     * Specifies if the function should be invoked sync or async. Default is sync.
     * 
     */
    @JsonbProperty("invoke")
    private EventRef.Invoke invoke = EventRef.Invoke.fromValue("sync");

    /**
     * Reference to the unique name of a 'produced' event definition
     * (Required)
     * 
     */
    @JsonbProperty("triggerEventRef")
    public String getTriggerEventRef() {
        return triggerEventRef;
    }

    /**
     * Reference to the unique name of a 'produced' event definition
     * (Required)
     * 
     */
    @JsonbProperty("triggerEventRef")
    public void setTriggerEventRef(final String triggerEventRef) {
        this.triggerEventRef = triggerEventRef;
    }

    /**
     * Reference to the unique name of a 'consumed' event definition
     * (Required)
     * 
     */
    @JsonbProperty("resultEventRef")
    public String getResultEventRef() {
        return resultEventRef;
    }

    /**
     * Reference to the unique name of a 'consumed' event definition
     * (Required)
     * 
     */
    @JsonbProperty("resultEventRef")
    public void setResultEventRef(final String resultEventRef) {
        this.resultEventRef = resultEventRef;
    }

    /**
     * Maximum amount of time (ISO 8601 format) to wait for the result event. If not defined it should default to the actionExecutionTimeout
     * 
     */
    @JsonbProperty("resultEventTimeout")
    public String getResultEventTimeout() {
        return resultEventTimeout;
    }

    /**
     * Maximum amount of time (ISO 8601 format) to wait for the result event. If not defined it should default to the actionExecutionTimeout
     * 
     */
    @JsonbProperty("resultEventTimeout")
    public void setResultEventTimeout(final String resultEventTimeout) {
        this.resultEventTimeout = resultEventTimeout;
    }

    /**
     * Expression which selects parts of the states data output to become the data of the produced event.
     * 
     */
    @JsonbProperty("data")
    public String getData() {
        return data;
    }

    /**
     * Expression which selects parts of the states data output to become the data of the produced event.
     * 
     */
    @JsonbProperty("data")
    public void setData(final String data) {
        this.data = data;
    }

    /**
     * Add additional extension context attributes to the produced event
     * 
     */
    @JsonbProperty("contextAttributes")
    public Map<String, String> getContextAttributes() {
        return contextAttributes;
    }

    /**
     * Add additional extension context attributes to the produced event
     * 
     */
    @JsonbProperty("contextAttributes")
    public void setContextAttributes(final Map<String, String> contextAttributes) {
        this.contextAttributes = contextAttributes;
    }

    /**
     * Specifies if the function should be invoked sync or async. Default is sync.
     * 
     */
    @JsonbProperty("invoke")
    public EventRef.Invoke getInvoke() {
        return invoke;
    }

    /**
     * Specifies if the function should be invoked sync or async. Default is sync.
     * 
     */
    @JsonbProperty("invoke")
    public void setInvoke(final EventRef.Invoke invoke) {
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
        private final static Map<String, EventRef.Invoke> CONSTANTS = new HashMap<String, EventRef.Invoke>();

        static {
            for (EventRef.Invoke c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

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

        /**
         * @param value
         * @return
         */
        @JsonbCreator
        public static EventRef.Invoke fromValue(final String value) {
        	final EventRef.Invoke constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }
    }
}

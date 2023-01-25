package epf.workflow.schema.functions;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.json.JsonValue;
import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;

/**
 * @author PC
 *
 */
@JsonbPropertyOrder({
    "refName",
    "arguments",
    "selectionSet",
    "invoke"
})
public class FunctionRef implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Name of the referenced function
     * (Required)
     * 
     */
    @JsonbProperty("refName")
    @Size(min = 1)
    @NotNull
    private String refName;
    /**
     * Function arguments
     * 
     */
    @JsonbProperty("arguments")
    @Valid
    private JsonValue arguments;
    /**
     * Only used if function type is 'graphql'. A string containing a valid GraphQL selection set
     * 
     */
    @JsonbProperty("selectionSet")
    private String selectionSet;
    /**
     * Specifies if the function should be invoked sync or async. Default is sync.
     * 
     */
    @JsonbProperty("invoke")
    private FunctionRef.Invoke invoke = FunctionRef.Invoke.fromValue("sync");

    /**
     * Name of the referenced function
     * (Required)
     * 
     */
    @JsonbProperty("refName")
    public String getRefName() {
        return refName;
    }

    /**
     * Name of the referenced function
     * (Required)
     * 
     */
    @JsonbProperty("refName")
    public void setRefName(final String refName) {
        this.refName = refName;
    }
    
    /**
     * Function arguments
     * 
     */
    @JsonbProperty("arguments")
    public JsonValue getArguments() {
        return arguments;
    }

    /**
     * Function arguments
     * 
     */
    @JsonbProperty("arguments")
    public void setArguments(final JsonValue arguments) {
        this.arguments = arguments;
    }

    /**
     * Only used if function type is 'graphql'. A string containing a valid GraphQL selection set
     * 
     */
    @JsonbProperty("selectionSet")
    public String getSelectionSet() {
        return selectionSet;
    }

    /**
     * Only used if function type is 'graphql'. A string containing a valid GraphQL selection set
     * 
     */
    @JsonbProperty("selectionSet")
    public void setSelectionSet(final String selectionSet) {
        this.selectionSet = selectionSet;
    }

    /**
     * Specifies if the function should be invoked sync or async. Default is sync.
     * 
     */
    @JsonbProperty("invoke")
    public FunctionRef.Invoke getInvoke() {
        return invoke;
    }

    /**
     * Specifies if the function should be invoked sync or async. Default is sync.
     * 
     */
    @JsonbProperty("invoke")
    public void setInvoke(final FunctionRef.Invoke invoke) {
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
        private final static Map<String, FunctionRef.Invoke> CONSTANTS = new HashMap<String, FunctionRef.Invoke>();

        static {
            for (FunctionRef.Invoke c: values()) {
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
        public static FunctionRef.Invoke fromValue(final String value) {
        	final FunctionRef.Invoke constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }
    }
}
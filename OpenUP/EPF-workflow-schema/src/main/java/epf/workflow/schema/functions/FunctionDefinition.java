package epf.workflow.schema.functions;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;
import jakarta.nosql.mapping.Entity;
import jakarta.nosql.mapping.Id;
import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;

/**
 * @author PC
 *
 */
@JsonbPropertyOrder({
    "name",
    "operation",
    "type",
    "authRef",
    "metadata"
})
@Entity
public class FunctionDefinition implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Function unique name
     * (Required)
     * 
     */
    @JsonbProperty("name")
    @Size(min = 1)
    @NotNull
    @Column
    @Id
    private String name;
    /**
     * If type is `rest`, <path_to_openapi_definition>#<operation_id>. If type is `rpc`, <path_to_grpc_proto_file>#<service_name>#<service_method>. If type is `expression`, defines the workflow expression.
     * 
     */
    @JsonbProperty("operation")
    @Size(min = 1)
    @Column
    private String operation;
    /**
     * Defines the function type. Is either `rest`, `asyncapi, `rpc`, `graphql`, `odata`, `expression`, or `custom`. Default is `rest`
     * 
     */
    @JsonbProperty("type")
    @Column
    private FunctionDefinition.Type type = FunctionDefinition.Type.fromValue("rest");
    /**
     * References an auth definition name to be used to access to resource defined in the operation parameter
     * 
     */
    @JsonbProperty("authRef")
    @Size(min = 1)
    @Column
    private String authRef;
    /**
     * Metadata
     * 
     */
    @JsonbProperty("metadata")
    @Valid
    @Column
    private Map<String, String> metadata;

    /**
     * Function unique name
     * (Required)
     * 
     */
    @JsonbProperty("name")
    public String getName() {
        return name;
    }

    /**
     * Function unique name
     * (Required)
     * 
     */
    @JsonbProperty("name")
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * If type is `rest`, <path_to_openapi_definition>#<operation_id>. If type is `rpc`, <path_to_grpc_proto_file>#<service_name>#<service_method>. If type is `expression`, defines the workflow expression.
     * 
     */
    @JsonbProperty("operation")
    public String getOperation() {
        return operation;
    }

    /**
     * If type is `rest`, <path_to_openapi_definition>#<operation_id>. If type is `rpc`, <path_to_grpc_proto_file>#<service_name>#<service_method>. If type is `expression`, defines the workflow expression.
     * 
     */
    @JsonbProperty("operation")
    public void setOperation(final String operation) {
        this.operation = operation;
    }

    /**
     * Defines the function type. Is either `rest`, `asyncapi, `rpc`, `graphql`, `odata`, `expression`, or `custom`. Default is `rest`
     * 
     */
    @JsonbProperty("type")
    public FunctionDefinition.Type getType() {
        return type;
    }

    /**
     * Defines the function type. Is either `rest`, `asyncapi, `rpc`, `graphql`, `odata`, `expression`, or `custom`. Default is `rest`
     * 
     */
    @JsonbProperty("type")
    public void setType(final FunctionDefinition.Type type) {
        this.type = type;
    }

    /**
     * References an auth definition name to be used to access to resource defined in the operation parameter
     * 
     */
    @JsonbProperty("authRef")
    public String getAuthRef() {
        return authRef;
    }

    /**
     * References an auth definition name to be used to access to resource defined in the operation parameter
     * 
     */
    @JsonbProperty("authRef")
    public void setAuthRef(final String authRef) {
        this.authRef = authRef;
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

    /**
     * @author PC
     *
     */
    @Embeddable
    public enum Type {

        REST("rest"),
        ASYNCAPI("asyncapi"),
        RPC("rpc"),
        GRAPHQL("graphql"),
        ODATA("odata"),
        EXPRESSION("expression"),
        CUSTOM("custom");
        /**
         * 
         */
        private final String value;
        /**
         * 
         */
        private final static Map<String, FunctionDefinition.Type> CONSTANTS = new HashMap<String, FunctionDefinition.Type>();

        static {
            for (FunctionDefinition.Type c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        /**
         * @param value
         */
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
        public static FunctionDefinition.Type fromValue(final String value) {
        	final FunctionDefinition.Type constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }
    }
}
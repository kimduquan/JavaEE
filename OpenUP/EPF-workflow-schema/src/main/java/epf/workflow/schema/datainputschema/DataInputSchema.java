package epf.workflow.schema.datainputschema;

import java.io.Serializable;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author PC
 *
 */
@JsonbPropertyOrder({
    "schema",
    "failOnValidationErrors"
})
public class DataInputSchema implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * URI of the JSON Schema used to validate the workflow data input
     * (Required)
     * 
     */
    @JsonbProperty("schema")
    @Size(min = 1)
    @NotNull
    private String schema;
    /**
     * Determines if workfow execution should continue if there are validation errors
     * (Required)
     * 
     */
    @JsonbProperty("failOnValidationErrors")
    @NotNull
    private boolean failOnValidationErrors = true;

    /**
     * No args constructor for use in serialization
     * 
     */
    public DataInputSchema() {
    }

    /**
     * 
     * @param schema
     * @param failOnValidationErrors
     */
    public DataInputSchema(String schema, boolean failOnValidationErrors) {
        super();
        this.schema = schema;
        this.failOnValidationErrors = failOnValidationErrors;
    }

    /**
     * URI of the JSON Schema used to validate the workflow data input
     * (Required)
     * 
     */
    @JsonbProperty("schema")
    public String getSchema() {
        return schema;
    }

    /**
     * URI of the JSON Schema used to validate the workflow data input
     * (Required)
     * 
     */
    @JsonbProperty("schema")
    public void setSchema(String schema) {
        this.schema = schema;
    }

    public DataInputSchema withSchema(String schema) {
        this.schema = schema;
        return this;
    }

    /**
     * Determines if workfow execution should continue if there are validation errors
     * (Required)
     * 
     */
    @JsonbProperty("failOnValidationErrors")
    public boolean isFailOnValidationErrors() {
        return failOnValidationErrors;
    }

    /**
     * Determines if workfow execution should continue if there are validation errors
     * (Required)
     * 
     */
    @JsonbProperty("failOnValidationErrors")
    public void setFailOnValidationErrors(boolean failOnValidationErrors) {
        this.failOnValidationErrors = failOnValidationErrors;
    }

    public DataInputSchema withFailOnValidationErrors(boolean failOnValidationErrors) {
        this.failOnValidationErrors = failOnValidationErrors;
        return this;
    }

}

package epf.workflow.schema.datainputschema;

import java.io.Serializable;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@JsonbPropertyOrder({
    "schema",
    "failOnValidationErrors"
})
@Embeddable
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
    @Column
    private String schema;
    /**
     * Determines if workfow execution should continue if there are validation errors
     * (Required)
     * 
     */
    @JsonbProperty("failOnValidationErrors")
    @NotNull
    @Column
    private boolean failOnValidationErrors = true;

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
    public void setSchema(final String schema) {
        this.schema = schema;
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
    public void setFailOnValidationErrors(final boolean failOnValidationErrors) {
        this.failOnValidationErrors = failOnValidationErrors;
    }
}

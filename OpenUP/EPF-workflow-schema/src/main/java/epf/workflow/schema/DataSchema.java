package epf.workflow.schema;

import epf.json.schema.JsonSchema;
import epf.workflow.schema.mapping.JsonSchemaAttributeConverter;
import jakarta.nosql.Column;
import org.eclipse.jnosql.mapping.Convert;
import org.eclipse.jnosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class DataSchema {

	/**
	 * 
	 */
	@Column
	@Convert(value = JsonSchemaAttributeConverter.class)
	private JsonSchema schema;
	
	/**
	 * 
	 */
	@Column
	private boolean failOnValidationErrors = false;
	
	public JsonSchema getSchema() {
		return schema;
	}
	public void setSchema(JsonSchema schema) {
		this.schema = schema;
	}
	public boolean isFailOnValidationErrors() {
		return failOnValidationErrors;
	}
	public void setFailOnValidationErrors(boolean failOnValidationErrors) {
		this.failOnValidationErrors = failOnValidationErrors;
	}
}

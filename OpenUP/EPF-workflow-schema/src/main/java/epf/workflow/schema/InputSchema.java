package epf.workflow.schema;

import javax.json.JsonValue;
import epf.workflow.schema.mapping.JsonValueAttributeConverter;
import jakarta.nosql.Column;
import org.eclipse.jnosql.mapping.Convert;
import org.eclipse.jnosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class InputSchema {

	/**
	 * 
	 */
	@Column
	@Convert(JsonValueAttributeConverter.class)
	private JsonValue schema;
	
	/**
	 * 
	 */
	@Column
	private boolean failOnValidationErrors = false;
	
	public JsonValue getSchema() {
		return schema;
	}
	public void setSchema(JsonValue schema) {
		this.schema = schema;
	}
	public boolean isFailOnValidationErrors() {
		return failOnValidationErrors;
	}
	public void setFailOnValidationErrors(boolean failOnValidationErrors) {
		this.failOnValidationErrors = failOnValidationErrors;
	}
}

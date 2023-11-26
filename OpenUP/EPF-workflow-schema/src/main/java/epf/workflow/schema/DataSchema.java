package epf.workflow.schema;

import epf.json.schema.JsonSchema;
import epf.workflow.schema.util.StringOrObject;
import jakarta.nosql.Column;
import java.io.Serializable;
import org.eclipse.jnosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class DataSchema implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@Column
	private StringOrObject<JsonSchema> schema;
	
	/**
	 * 
	 */
	@Column
	private boolean failOnValidationErrors = false;
	
	public StringOrObject<JsonSchema> getSchema() {
		return schema;
	}
	public void setSchema(StringOrObject<JsonSchema> schema) {
		this.schema = schema;
	}
	public boolean isFailOnValidationErrors() {
		return failOnValidationErrors;
	}
	public void setFailOnValidationErrors(boolean failOnValidationErrors) {
		this.failOnValidationErrors = failOnValidationErrors;
	}
}

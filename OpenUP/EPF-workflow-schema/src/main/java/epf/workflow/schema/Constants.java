package epf.workflow.schema;

import javax.json.JsonValue;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class Constants 
{
	/**
	 * 
	 */
	@Column
	private String refValue;
	/**
	 * 
	 */
	@Column
	private JsonValue constantsDef;
	
	public String getRefValue() {
		return refValue;
	}
	
	public void setRefValue(final String refValue) {
		this.refValue = refValue;
	}
	
	public JsonValue getConstantsDef() {
		return constantsDef;
	}
	
	public void setConstantsDef(final JsonValue constantsDef) {
		this.constantsDef = constantsDef;
	}
}

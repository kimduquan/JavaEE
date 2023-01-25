package epf.workflow.schema;

import javax.json.JsonValue;

/**
 * @author PC
 *
 */
public class Constants 
{
	/**
	 * 
	 */
	private String refValue;
	/**
	 * 
	 */
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

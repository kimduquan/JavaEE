package epf.workflow.schema;

import java.util.List;
import epf.workflow.schema.functions.FunctionDefinition;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class Functions 
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
	private List<FunctionDefinition> functionDefs;
	
	public String getRefValue() {
		return refValue;
	}
	
	public void setRefValue(final String refValue) {
		this.refValue = refValue;
	}
	
	public List<FunctionDefinition> getFunctionDefs() {
		return functionDefs;
	}
	
	public void setFunctionDefs(final List<FunctionDefinition> functionDefs) {
		this.functionDefs = functionDefs;
	}
}
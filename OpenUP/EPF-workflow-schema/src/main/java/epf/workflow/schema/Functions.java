package epf.workflow.schema;

import java.util.List;
import epf.workflow.schema.functions.FunctionDefinition;

/**
 * @author PC
 *
 */
public class Functions 
{
	/**
	 * 
	 */
	private String refValue;
	/**
	 * 
	 */
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
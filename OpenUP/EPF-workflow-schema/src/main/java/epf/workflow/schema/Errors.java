package epf.workflow.schema;

import java.util.List;
import io.serverlessworkflow.api.error.ErrorDefinition;

/**
 * @author PC
 *
 */
public class Errors 
{
	/**
	 * 
	 */
	private String refValue;
	/**
	 * 
	 */
	private List<ErrorDefinition> errorDefs;

	public String getRefValue() {
		return refValue;
	}

	public void setRefValue(final String refValue) {
		this.refValue = refValue;
	}

	public List<ErrorDefinition> getErrorDefs() {
		return errorDefs;
	}

	public void setErrorDefs(final List<ErrorDefinition> errorDefs) {
		this.errorDefs = errorDefs;
	}
}
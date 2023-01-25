package epf.workflow.schema;

import java.util.List;
import epf.workflow.schema.error.ErrorDefinition;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class Errors 
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
package epf.workflow.schema;

import java.util.List;
import epf.workflow.schema.retry.RetryDefinition;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class Retries 
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
	private List<RetryDefinition> retryDefs;
	
	public String getRefValue() {
		return refValue;
	}
	
	public void setRefValue(final String refValue) {
		this.refValue = refValue;
	}
	
	public List<RetryDefinition> getRetryDefs() {
		return retryDefs;
	}
	
	public void setRetryDefs(final List<RetryDefinition> retryDefs) {
		this.retryDefs = retryDefs;
	}
}

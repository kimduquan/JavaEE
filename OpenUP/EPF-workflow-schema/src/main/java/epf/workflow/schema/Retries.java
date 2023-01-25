package epf.workflow.schema;

import java.util.List;
import epf.workflow.schema.retry.RetryDefinition;

/**
 * @author PC
 *
 */
public class Retries 
{
	/**
	 * 
	 */
	private String refValue;
	/**
	 * 
	 */
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

package epf.workflow.schema;

import java.util.List;

/**
 * @author PC
 *
 */
public class Secrets 
{
	/**
	 * 
	 */
	private String refValue;
	/**
	 * 
	 */
	private List<String> secretDefs;
	
	public String getRefValue() {
		return refValue;
	}
	
	public void setRefValue(final String refValue) {
		this.refValue = refValue;
	}
	
	public List<String> getSecretDefs() {
		return secretDefs;
	}
	
	public void setSecretDefs(final List<String> secretDefs) {
		this.secretDefs = secretDefs;
	}
}
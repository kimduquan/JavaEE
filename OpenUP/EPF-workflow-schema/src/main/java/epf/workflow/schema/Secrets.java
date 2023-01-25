package epf.workflow.schema;

import java.util.List;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class Secrets 
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
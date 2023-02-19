package epf.workflow.schema;

import javax.validation.constraints.NotNull;

import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class CorrelationDefinition {

	/**
	 * 
	 */
	@NotNull
	@Column
	private String contextAttributeName;
	/**
	 * 
	 */
	@Column
	private String contextAttributeValue;
	
	public String getContextAttributeName() {
		return contextAttributeName;
	}
	public void setContextAttributeName(String contextAttributeName) {
		this.contextAttributeName = contextAttributeName;
	}
	public String getContextAttributeValue() {
		return contextAttributeValue;
	}
	public void setContextAttributeValue(String contextAttributeValue) {
		this.contextAttributeValue = contextAttributeValue;
	}
}

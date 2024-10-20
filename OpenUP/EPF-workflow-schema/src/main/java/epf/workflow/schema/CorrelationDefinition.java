package epf.workflow.schema;

import jakarta.validation.constraints.NotNull;
import jakarta.nosql.Column;
import java.io.Serializable;
import org.eclipse.jnosql.mapping.Embeddable;
import org.eclipse.microprofile.graphql.Description;

@Embeddable
public class CorrelationDefinition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Column
	@Description("CloudEvent Extension Context Attribute name")
	private String contextAttributeName;
	
	@Column
	@Description("CloudEvent Extension Context Attribute value")
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

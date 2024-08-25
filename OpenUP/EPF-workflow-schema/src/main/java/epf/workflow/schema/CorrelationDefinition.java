package epf.workflow.schema;

import jakarta.validation.constraints.NotNull;
import jakarta.nosql.Column;
import java.io.Serializable;
import org.eclipse.jnosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class CorrelationDefinition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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

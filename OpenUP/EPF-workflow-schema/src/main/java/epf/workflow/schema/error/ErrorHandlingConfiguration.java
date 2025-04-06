package epf.workflow.schema.error;

import java.io.Serializable;
import java.util.List;
import jakarta.nosql.Embeddable;
import org.eclipse.microprofile.graphql.Description;
import jakarta.nosql.Column;

@Embeddable
public class ErrorHandlingConfiguration implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column
	@Description("An array containing reusable definitions of errors to throw and/or to handle.	")
	private List<ErrorDefinition> definitions;
	
	@Column
	@Description("An array containing reusable error handlers, which are used to configure what to do when catching specific errors.")
	private List<ErrorHandlerDefinition> handlers;
	
	@Column
	@Description("An array containg named groups of error handlers that define reusable error policies")
	private List<ErrorPolicyDefinition> policies;

	public List<ErrorDefinition> getDefinitions() {
		return definitions;
	}

	public void setDefinitions(List<ErrorDefinition> definitions) {
		this.definitions = definitions;
	}

	public List<ErrorHandlerDefinition> getHandlers() {
		return handlers;
	}

	public void setHandlers(List<ErrorHandlerDefinition> handlers) {
		this.handlers = handlers;
	}

	public List<ErrorPolicyDefinition> getPolicies() {
		return policies;
	}

	public void setPolicies(List<ErrorPolicyDefinition> policies) {
		this.policies = policies;
	}
}

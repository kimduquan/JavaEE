package epf.workflow.schema.error;

import java.io.Serializable;
import java.util.List;
import jakarta.nosql.Embeddable;
import org.eclipse.microprofile.graphql.Description;
import jakarta.nosql.Column;
import jakarta.validation.constraints.NotNull;

@Embeddable
public class ErrorPolicyDefinition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Column
	@Description("The name of the error handler")
	private String name;
	
	@NotNull
	@Column
	@Description("A list of the error handlers to use")
	private List<ErrorHandlerReference> handlers;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ErrorHandlerReference> getHandlers() {
		return handlers;
	}

	public void setHandlers(List<ErrorHandlerReference> handlers) {
		this.handlers = handlers;
	}
}

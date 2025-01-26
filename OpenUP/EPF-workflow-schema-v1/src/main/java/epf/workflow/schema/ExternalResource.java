package epf.workflow.schema;

import org.eclipse.microprofile.graphql.Description;
import jakarta.validation.constraints.NotNull;

@Description("Defines an external resource.")
public class ExternalResource {

	@Description("The name, if any, of the defined resource.")
	private String name;
	
	@NotNull
	@Description("The endpoint at which to get the defined resource.")
	private Endpoint endpoint;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Endpoint getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(Endpoint endpoint) {
		this.endpoint = endpoint;
	}
}

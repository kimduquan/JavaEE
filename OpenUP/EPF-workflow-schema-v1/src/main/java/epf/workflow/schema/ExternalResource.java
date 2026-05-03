package epf.workflow.schema;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("Defines an external resource.")
public class ExternalResource {

	@JsonPropertyDescription("The name, if any, of the defined resource.")
	private String name;
	
	@NotNull
	@JsonPropertyDescription("The endpoint at which to get the defined resource.")
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

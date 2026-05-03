package epf.workflow.schema;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("A resource catalog is an external collection of reusable components, such as functions, that can be referenced and imported into workflows. Catalogs allow workflows to integrate with externally defined resources, making it easier to manage reuse and versioning across different workflows.")
public class Catalog {

	@NotNull
	@JsonPropertyDescription("The endpoint that defines the root URL at which the catalog is located.")
	private Endpoint endpoint;

	public Endpoint getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(Endpoint endpoint) {
		this.endpoint = endpoint;
	}
}

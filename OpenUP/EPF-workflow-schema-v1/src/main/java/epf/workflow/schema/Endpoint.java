package epf.workflow.schema;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("Describes an enpoint.")
public class Endpoint {

	@NotNull
	@JsonPropertyDescription("The endpoint's URI.")
	private String uri;
	
	@JsonPropertyDescription("The authentication policy to use.")
	private Authentication authentication;

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Authentication getAuthentication() {
		return authentication;
	}

	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}
}

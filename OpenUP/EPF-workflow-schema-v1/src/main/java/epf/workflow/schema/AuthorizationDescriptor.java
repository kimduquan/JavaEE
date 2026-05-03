package epf.workflow.schema;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public class AuthorizationDescriptor {

	@JsonPropertyDescription("The resolved authorization scheme.")
	private String scheme;
	
	@JsonPropertyDescription("The resolved authorization parameter.")
	private String parameter;

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
}

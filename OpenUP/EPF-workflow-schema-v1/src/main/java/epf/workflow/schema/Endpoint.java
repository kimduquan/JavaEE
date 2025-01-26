package epf.workflow.schema;

import org.eclipse.microprofile.graphql.Description;

@Description("Describes an enpoint.")
public class Endpoint {

	@Description("The endpoint's URI.")
	private String uri;
	
	@Description("The authentication policy to use.")
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

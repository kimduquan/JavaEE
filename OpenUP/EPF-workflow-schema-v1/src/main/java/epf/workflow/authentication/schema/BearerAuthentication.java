package epf.workflow.authentication.schema;

import org.eclipse.microprofile.graphql.Description;
import jakarta.validation.constraints.NotNull;

@Description("Defines the fundamentals of a 'bearer' authentication")
public class BearerAuthentication {

	@NotNull
	@Description("The bearer token to use.")
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}

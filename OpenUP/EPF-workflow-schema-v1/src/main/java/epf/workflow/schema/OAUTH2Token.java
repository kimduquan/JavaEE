package epf.workflow.schema;

import org.eclipse.microprofile.graphql.Description;
import jakarta.validation.constraints.NotNull;

@Description("Represents the definition of an OAUTH2 token")
public class OAUTH2Token {

	@NotNull
	@Description("The security token to use to use.")
	private String token;
	
	@NotNull
	@Description("The type of security token to use.")
	private String type;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}

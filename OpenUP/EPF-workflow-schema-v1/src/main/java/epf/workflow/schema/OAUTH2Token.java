package epf.workflow.schema;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("Represents the definition of an OAUTH2 token")
public class OAUTH2Token {

	@NotNull
	@JsonPropertyDescription("The security token to use to use.")
	private String token;
	
	@NotNull
	@JsonPropertyDescription("The type of security token to use.")
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

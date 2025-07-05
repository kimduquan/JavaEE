package epf.workflow.authentication.schema;

import org.eclipse.microprofile.graphql.Description;
import jakarta.validation.constraints.NotNull;

@Description("Defines the fundamentals of a 'basic' authentication.")
public class BasicAuthentication {

	@NotNull
	@Description("The username to use.")
	private String username;
	
	@NotNull
	@Description("The password to use.")
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}

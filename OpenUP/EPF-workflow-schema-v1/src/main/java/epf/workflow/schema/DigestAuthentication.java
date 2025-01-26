package epf.workflow.schema;

import org.eclipse.microprofile.graphql.Description;

@Description("Defines the fundamentals of a 'digest' authentication.")
public class DigestAuthentication {

	@Description("The username to use.")
	private String username;
	
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

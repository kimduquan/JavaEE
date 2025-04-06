package epf.workflow.schema.auth;

import jakarta.validation.constraints.NotNull;
import jakarta.nosql.Column;
import jakarta.nosql.Embeddable;
import org.eclipse.microprofile.graphql.Description;

@Embeddable
public class BasicPropertiesDefinition extends PropertiesDefinition {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Column
	@Description("String or a workflow expression. Contains the user name")
	private String username;
	
	@NotNull
	@Column
	@Description("String or a workflow expression. Contains the user password	")
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

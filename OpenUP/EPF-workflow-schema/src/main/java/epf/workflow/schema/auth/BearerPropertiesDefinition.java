package epf.workflow.schema.auth;

import jakarta.validation.constraints.NotNull;
import jakarta.nosql.Column;
import jakarta.nosql.Embeddable;
import org.eclipse.microprofile.graphql.Description;

@Embeddable
public class BearerPropertiesDefinition extends PropertiesDefinition {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Column
	@Description("String or a workflow expression. Contains the token information")
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}

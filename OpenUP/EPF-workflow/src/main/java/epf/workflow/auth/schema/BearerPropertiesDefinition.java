package epf.workflow.auth.schema;

import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class BearerPropertiesDefinition extends PropertiesDefinition {

	/**
	 * 
	 */
	@Column
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}

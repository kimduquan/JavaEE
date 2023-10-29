package epf.workflow.schema.auth;

import jakarta.validation.constraints.NotNull;
import jakarta.nosql.Column;
import org.eclipse.jnosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class BearerPropertiesDefinition extends PropertiesDefinition {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	@Column
	@NotNull
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}

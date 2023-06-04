package epf.workflow.auth.schema;

import javax.validation.constraints.NotNull;
import jakarta.nosql.Column;
import org.eclipse.jnosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class BasicPropertiesDefinition extends PropertiesDefinition {

	/**
	 * 
	 */
	@Column
	@NotNull
	private String username;
	/**
	 * 
	 */
	@Column
	@NotNull
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

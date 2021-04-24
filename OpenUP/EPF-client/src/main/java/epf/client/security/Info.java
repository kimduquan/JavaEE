/**
 * 
 */
package epf.client.security;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.FormParam;

/**
 * @author PC
 *
 */
public class Info {

	/**
	 * 
	 */
	@FormParam("password")
	@NotNull
	@NotBlank
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}
}

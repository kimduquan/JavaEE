/**
 * 
 */
package epf.web.page.security;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import epf.client.security.Credential;

/**
 * @author PC
 *
 */
@Named("security_login")
@RequestScoped
public class Login {

	/**
	 * 
	 */
	private Credential credential = new Credential();

	/**
	 * @return the credential
	 */
	public Credential getCredential() {
		return credential;
	}

	/**
	 * @param credential the credential to set
	 */
	public void setCredential(final Credential credential) {
		this.credential = credential;
	}
	
	/**
	 * @return
	 */
	public String login() {
		return "";
	}
}

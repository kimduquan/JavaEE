/**
 * 
 */
package epf.web.page.security;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import epf.client.security.Credential;
import epf.web.page.Page;

/**
 * @author PC
 *
 */
@Named(Page.SECURITY_LOGIN_PAGE)
@RequestScoped
public class LoginPage {

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
                
		return Page.TASKS_PAGE;
	}
}

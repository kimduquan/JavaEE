package epf.webapp.security.view;

import epf.webapp.security.Credential;

/**
 * @author PC
 *
 */
public interface LoginView {

	/**
	 * @return
	 */
	Credential getCredential();
	
	/**
	 * @return
	 */
	String authenticate();
}

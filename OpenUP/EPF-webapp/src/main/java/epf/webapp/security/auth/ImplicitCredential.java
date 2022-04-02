package epf.webapp.security.auth;

import javax.security.enterprise.credential.Credential;
import epf.security.auth.core.ImplicitAuthResponse;

/**
 * @author PC
 *
 */
public class ImplicitCredential implements Credential {

	/**
	 * 
	 */
	private transient final ImplicitAuthResponse authResponse;
	
	/**
	 * @param implicitAuthResponse
	 */
	public ImplicitCredential(final ImplicitAuthResponse implicitAuthResponse) {
		this.authResponse = implicitAuthResponse;
	}

	public ImplicitAuthResponse getAuthResponse() {
		return authResponse;
	}
}

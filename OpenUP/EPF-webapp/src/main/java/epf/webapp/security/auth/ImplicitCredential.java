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
	private transient final ImplicitAuthResponse implicitAuthResponse;
	
	/**
	 * @param implicitAuthResponse
	 */
	public ImplicitCredential(final ImplicitAuthResponse implicitAuthResponse) {
		this.implicitAuthResponse = implicitAuthResponse;
	}

	public ImplicitAuthResponse getImplicitAuthResponse() {
		return implicitAuthResponse;
	}
}

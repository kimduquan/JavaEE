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
	 * 
	 */
	private transient final String sessionId;
	
	/**
	 * @param implicitAuthResponse
	 * @param sessionId
	 */
	public ImplicitCredential(final ImplicitAuthResponse implicitAuthResponse, final String sessionId) {
		this.authResponse = implicitAuthResponse;
		this.sessionId = sessionId;
	}

	public ImplicitAuthResponse getAuthResponse() {
		return authResponse;
	}

	public String getSessionId() {
		return sessionId;
	}
}

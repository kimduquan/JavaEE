package epf.webapp.security.auth;

import epf.security.auth.core.ImplicitAuthResponse;

/**
 * @author PC
 *
 */
public class ImplicitCredential extends Credential {

	/**
	 * 
	 */
	private transient final ImplicitAuthResponse authResponse;
	
	/**
	 * @param implicitAuthResponse
	 * @param provider
	 * @param sessionId
	 */
	public ImplicitCredential(final ImplicitAuthResponse implicitAuthResponse, final String provider, final String sessionId) {
		super(provider, sessionId);
		this.authResponse = implicitAuthResponse;
	}

	public ImplicitAuthResponse getAuthResponse() {
		return authResponse;
	}
}
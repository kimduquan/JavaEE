package epf.webapp.security.auth;

import java.io.Serializable;
import javax.enterprise.context.ConversationScoped;
import epf.security.auth.openid.AuthError;
import epf.security.auth.openid.AuthRequest;
import epf.security.auth.openid.AuthResponse;
import epf.security.auth.openid.Provider;

/**
 * @author PC
 *
 */
@ConversationScoped
public class AuthFlow implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private transient char[] clientSecret;
	
	/**
	 * 
	 */
	private String id;
	
	/**
	 * 
	 */
	private Provider provider;
	
	/**
	 * 
	 */
	private AuthRequest authRequest;
	
	/**
	 * 
	 */
	private AuthResponse authResponse;
	
	/**
	 * 
	 */
	private AuthError authError;

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public AuthRequest getAuthRequest() {
		return authRequest;
	}

	public void setAuthRequest(final AuthRequest authRequest) {
		this.authRequest = authRequest;
	}

	public AuthResponse getAuthResponse() {
		return authResponse;
	}

	public void setAuthResponse(final AuthResponse authResponse) {
		this.authResponse = authResponse;
	}

	public AuthError getAuthError() {
		return authError;
	}

	public void setAuthError(final AuthError authError) {
		this.authError = authError;
	}

	public char[] getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(final char[] clientSecret) {
		this.clientSecret = clientSecret;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}
}

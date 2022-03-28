package epf.webapp.security.auth;

import java.io.Serializable;
import javax.enterprise.context.ConversationScoped;
import epf.security.auth.openid.AuthError;
import epf.security.auth.openid.AuthRequest;
import epf.security.auth.openid.AuthResponse;
import epf.security.auth.openid.ProviderMetadata;

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
	private ProviderMetadata providerMetadata;
	
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

	public ProviderMetadata getProviderMetadata() {
		return providerMetadata;
	}

	public void setProviderMetadata(final ProviderMetadata provider) {
		this.providerMetadata = provider;
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
}

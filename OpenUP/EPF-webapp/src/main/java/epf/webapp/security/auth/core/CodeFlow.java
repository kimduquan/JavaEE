package epf.webapp.security.auth.core;

import java.io.Serializable;
import javax.enterprise.context.ConversationScoped;
import epf.security.auth.Provider;
import epf.security.auth.core.AuthError;
import epf.security.auth.core.AuthRequest;
import epf.security.auth.core.AuthResponse;
import epf.security.auth.core.CodeFlowAuth;
import epf.security.auth.core.TokenResponse;

/**
 * @author PC
 *
 */
@ConversationScoped
public class CodeFlow extends Flow implements CodeFlowAuth, Serializable {

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

	public void setProvider(final Provider provider) {
		this.provider = provider;
	}

	@Override
	public boolean validate(final AuthResponse authResponse) {
		return true;
	}

	@Override
	public boolean validate(final TokenResponse tokenResponse) {
		return true;
	}
}

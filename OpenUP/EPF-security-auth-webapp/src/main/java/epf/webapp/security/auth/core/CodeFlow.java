package epf.webapp.security.auth.core;

import java.io.Serializable;
import jakarta.enterprise.context.ConversationScoped;
import epf.security.auth.core.AuthError;
import epf.security.auth.core.AuthRequest;
import epf.security.auth.core.AuthResponse;
import epf.security.auth.core.CodeFlowAuth;
import epf.security.auth.core.TokenResponse;

@ConversationScoped
public class CodeFlow implements CodeFlowAuth, Serializable {

	private static final long serialVersionUID = 1L;
	
	private AuthRequest authRequest;
	
	private AuthResponse authResponse;
	
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

	@Override
	public boolean validate(final AuthResponse authResponse) {
		return true;
	}

	@Override
	public boolean validate(final TokenResponse tokenResponse) {
		return true;
	}
}

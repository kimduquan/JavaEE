package epf.webapp.security.auth.core;

import java.io.Serializable;
import javax.enterprise.context.ConversationScoped;
import epf.security.auth.core.ImplicitAuthError;
import epf.security.auth.core.ImplicitAuthRequest;
import epf.security.auth.core.ImplicitAuthResponse;
import epf.security.auth.core.ImplicitFlowAuth;

/**
 * @author PC
 *
 */
@ConversationScoped
public class ImplicitFlow extends Flow implements ImplicitFlowAuth, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private ImplicitAuthRequest authRequest;
	
	/**
	 * 
	 */
	private ImplicitAuthResponse authResponse;
	
	/**
	 * 
	 */
	private ImplicitAuthError authError;

	public ImplicitAuthRequest getAuthRequest() {
		return authRequest;
	}

	public void setAuthRequest(final ImplicitAuthRequest authRequest) {
		this.authRequest = authRequest;
	}

	public ImplicitAuthResponse getAuthResponse() {
		return authResponse;
	}

	public void setAuthResponse(final ImplicitAuthResponse authResponse) {
		this.authResponse = authResponse;
	}

	public ImplicitAuthError getAuthError() {
		return authError;
	}

	public void setAuthError(final ImplicitAuthError authError) {
		this.authError = authError;
	}

	@Override
	public boolean validate(final ImplicitAuthResponse implicitAuthResponse) {
		return true;
	}
}

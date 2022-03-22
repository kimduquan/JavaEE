package epf.security.auth.openid;

/**
 * @author PC
 *
 */
public class AuthError {
	
	/**
	 * @author PC
	 *
	 */
	public enum Error {
		
		//Section 4.1.2.1 of OAuth 2.0 [RFC6749]
		/**
		 * 
		 */
		invalid_request,
		/**
		 * 
		 */
		unauthorized_client,
		/**
		 * 
		 */
		access_denied,
		/**
		 * 
		 */
		unsupported_response_type,
		/**
		 * 
		 */
		invalid_scope,
		/**
		 * 
		 */
		server_error,
		/**
		 * 
		 */
		temporarily_unavailable,
		
		/**
		 * 
		 */
		interaction_required,
		/**
		 * 
		 */
		login_required,
		/**
		 * 
		 */
		account_selection_required,
		/**
		 * 
		 */
		consent_required,
		/**
		 * 
		 */
		invalid_request_uri,
		/**
		 * 
		 */
		invalid_request_object,
		/**
		 * 
		 */
		request_not_supported,
		/**
		 * 
		 */
		request_uri_not_supported,
		/**
		 * 
		 */
		registration_not_supported
	}

	/**
	 * 
	 */
	private Error error;
	
	/**
	 * 
	 */
	private String state;
	
	/**
	 * 
	 */
	private String error_description;
	
	/**
	 * 
	 */
	private String error_uri;

	public Error getError() {
		return error;
	}

	public void setError(final Error error) {
		this.error = error;
	}

	public String getState() {
		return state;
	}

	public void setState(final String state) {
		this.state = state;
	}

	public String getError_description() {
		return error_description;
	}

	public void setError_description(final String error_description) {
		this.error_description = error_description;
	}

	public String getError_uri() {
		return error_uri;
	}

	public void setError_uri(final String error_uri) {
		this.error_uri = error_uri;
	}
}

package epf.security.auth.openid;

/**
 * @author PC
 *
 */
public class UserInfoErrorResponse extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @author PC
	 *
	 */
	public enum Error {
		/**
		 * 
		 */
		invalid_request,
		/**
		 * 
		 */
		invalid_token,
		/**
		 * 
		 */
		insufficient_scope
	}
	
	/**
	 * 
	 */
	private Error error;
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
	
	@Override
	public String toString() {
		return String.format("[error=%s\terror_uri=%s\terror_description=%s", error, error_uri, error_description);
	}

}

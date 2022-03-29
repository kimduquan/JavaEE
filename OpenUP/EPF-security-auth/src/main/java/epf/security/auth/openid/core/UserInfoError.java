package epf.security.auth.openid.core;

/**
 * @author PC
 *
 */
public class UserInfoError extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String error;
	/**
	 * 
	 */
	private String error_description;
	
	public String getError() {
		return error;
	}
	public void setError(final String error) {
		this.error = error;
	}
	public String getError_description() {
		return error_description;
	}
	public void setError_description(final String error_description) {
		this.error_description = error_description;
	}
}

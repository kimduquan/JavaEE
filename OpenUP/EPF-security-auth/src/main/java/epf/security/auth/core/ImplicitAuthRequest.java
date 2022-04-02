package epf.security.auth.core;

/**
 * @author PC
 *
 */
public class ImplicitAuthRequest extends AuthRequest {
	
	/**
	 * 
	 */
	private String response_mode;

	public String getResponse_mode() {
		return response_mode;
	}

	public void setResponse_mode(final String response_mode) {
		this.response_mode = response_mode;
	}
}

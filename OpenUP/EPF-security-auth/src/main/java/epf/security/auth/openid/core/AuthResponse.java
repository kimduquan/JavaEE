package epf.security.auth.openid.core;

/**
 * @author PC
 *
 */
public class AuthResponse {

	/**
	 * 
	 */
	private String code;
	/**
	 * 
	 */
	private String state;
	
	public String getCode() {
		return code;
	}
	public void setCode(final String code) {
		this.code = code;
	}
	public String getState() {
		return state;
	}
	public void setState(final String state) {
		this.state = state;
	}
}

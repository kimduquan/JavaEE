package epf.security.auth.openid.core;

/**
 * @author PC
 *
 */
public class ImplicitAuthResponse {

	/**
	 * 
	 */
	private String access_token;
	/**
	 * 
	 */
	private String token_type;
	/**
	 * 
	 */
	private String id_token;
	/**
	 * 
	 */
	private String state;
	/**
	 * 
	 */
	private Long expires_in;
	
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(final String access_token) {
		this.access_token = access_token;
	}
	public String getToken_type() {
		return token_type;
	}
	public void setToken_type(final String token_type) {
		this.token_type = token_type;
	}
	public String getId_token() {
		return id_token;
	}
	public void setId_token(final String id_token) {
		this.id_token = id_token;
	}
	public String getState() {
		return state;
	}
	public void setState(final String state) {
		this.state = state;
	}
	public Long getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(final Long expires_in) {
		this.expires_in = expires_in;
	}
}

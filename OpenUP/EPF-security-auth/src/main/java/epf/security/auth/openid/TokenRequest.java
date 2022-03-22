package epf.security.auth.openid;

/**
 * @author PC
 *
 */
public class TokenRequest {

	/**
	 * 
	 */
	private String grant_type;
	/**
	 * 
	 */
	private String code;
	/**
	 * 
	 */
	private String redirect_uri;
	/**
	 * 
	 */
	private String client_id;
	
	public String getGrant_type() {
		return grant_type;
	}
	
	public void setGrant_type(final String grant_type) {
		this.grant_type = grant_type;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(final String code) {
		this.code = code;
	}
	
	public String getRedirect_uri() {
		return redirect_uri;
	}
	
	public void setRedirect_uri(final String redirect_uri) {
		this.redirect_uri = redirect_uri;
	}
	
	public String getClient_id() {
		return client_id;
	}
	
	public void setClient_id(final String client_id) {
		this.client_id = client_id;
	}
}

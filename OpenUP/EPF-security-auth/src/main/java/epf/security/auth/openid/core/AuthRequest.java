package epf.security.auth.openid.core;

/**
 * @author PC
 *
 */
public class AuthRequest {

	/**
	 * 
	 */
	private String scope;
	/**
	 * 
	 */
	private String response_type;
	/**
	 * 
	 */
	private String client_id;
	/**
	 * 
	 */
	private String redirect_uri;
	/**
	 * 
	 */
	private String state;
	/**
	 * 
	 */
	private String nonce;
	
	public String getScope() {
		return scope;
	}
	public void setScope(final String scope) {
		this.scope = scope;
	}
	public String getResponse_type() {
		return response_type;
	}
	public void setResponse_type(final String response_type) {
		this.response_type = response_type;
	}
	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(final String client_id) {
		this.client_id = client_id;
	}
	public String getRedirect_uri() {
		return redirect_uri;
	}
	public void setRedirect_uri(final String redirect_uri) {
		this.redirect_uri = redirect_uri;
	}
	public String getState() {
		return state;
	}
	public void setState(final String state) {
		this.state = state;
	}
	public String getNonce() {
		return nonce;
	}
	public void setNonce(final String nonce) {
		this.nonce = nonce;
	}
}

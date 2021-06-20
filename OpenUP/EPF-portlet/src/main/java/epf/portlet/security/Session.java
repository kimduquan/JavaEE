/**
 * 
 */
package epf.portlet.security;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import epf.client.security.Token;
import epf.portlet.Portlet;
import java.io.Serializable;

/**
 * @author PC
 *
 */
@SessionScoped
@Named(Portlet.SECURITY_SESSION)
public class Session implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private String token;
	
	/**
	 * 
	 */
	private Token principal;

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	protected void setToken(final String token) {
		this.token = token;
	}

	/**
	 * @return the principal
	 */
	public Token getPrincipal() {
		return principal;
	}

	/**
	 * @param principal the principal to set
	 */
	protected void setPrincipal(final Token principal) {
		this.principal = principal;
	}
}

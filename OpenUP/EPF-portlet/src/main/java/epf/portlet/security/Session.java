/**
 * 
 */
package epf.portlet.security;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import epf.client.security.Token;
import epf.portlet.Name;
import java.io.Serializable;
import java.time.Instant;

/**
 * @author PC
 *
 */
@SessionScoped
@Named(Name.SECURITY_SESSION)
public class Session implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private Token token;
	
	/**
	 * @return
	 */
	public boolean isExpired() {
		return token != null && Instant.now().getEpochSecond() > token.getExpirationTime();
	}

	public Token getToken() {
		return token;
	}

	public void setToken(final Token token) {
		this.token = token;
	}
}

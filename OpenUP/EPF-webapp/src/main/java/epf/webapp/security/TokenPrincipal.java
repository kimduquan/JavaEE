package epf.webapp.security;

import javax.security.enterprise.CallerPrincipal;

/**
 * @author PC
 *
 */
public class TokenPrincipal extends CallerPrincipal {
	
	/**
	 * 
	 */
	private final String token;

	/**
	 * @param name
	 * @param token
	 */
	public TokenPrincipal(final String name, final String token) {
		super(name);
		this.token = token;
	}

	public String getToken() {
		return token;
	}
}

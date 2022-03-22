package epf.webapp.security.auth;

import javax.security.enterprise.CallerPrincipal;

import epf.security.auth.openid.TokenResponse;

/**
 * @author PC
 *
 */
public class OpenIDPrincipal extends CallerPrincipal {
	
	/**
	 * 
	 */
	private final TokenResponse token;

	/**
	 * @param name
	 */
	public OpenIDPrincipal(final String name, final TokenResponse token) {
		super(name);
		this.token = token;
	}

	public TokenResponse getToken() {
		return token;
	}
}

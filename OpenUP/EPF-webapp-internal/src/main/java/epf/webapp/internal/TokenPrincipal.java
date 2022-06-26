package epf.webapp.internal;

import javax.security.enterprise.CallerPrincipal;

/**
 * @author PC
 *
 */
public class TokenPrincipal extends CallerPrincipal {
	
	/**
	 * 
	 */
	private transient final char[] rawToken;
	
	/**
	 * 
	 */
	private transient char[] rememberToken;

	/**
	 * @param name
	 * @param token
	 */
	public TokenPrincipal(final String name, final char[] rawToken) {
		super(name);
		this.rawToken = rawToken;
	}

	public char[] getRawToken() {
		return rawToken;
	}
	
	/**
	 * @param token
	 */
	public void setRememberToken(final char[] token) {
		rememberToken = token;
	}
	
	/**
	 * @return
	 */
	public char[] getRememberToken(){
		return this.rememberToken;
	}
}

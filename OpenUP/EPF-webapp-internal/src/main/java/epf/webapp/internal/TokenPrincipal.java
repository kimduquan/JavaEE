package epf.webapp.internal;

import java.util.Map;
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
	 *
	 */
	private transient final Map<String, Object> claims;

	/**
	 * @param name
	 * @param rawToken
	 * @param claims
	 */
	public TokenPrincipal(final String name, final char[] rawToken, final Map<String, Object> claims) {
		super(name);
		this.rawToken = rawToken;
		this.claims = claims;
	}

	public char[] getRawToken() {
		return rawToken;
	}

	public Map<String, Object> getClaims() {
		return claims;
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

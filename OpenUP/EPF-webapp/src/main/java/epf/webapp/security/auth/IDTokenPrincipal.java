package epf.webapp.security.auth;

import java.util.Map;
import javax.security.enterprise.CallerPrincipal;

/**
 * @author PC
 *
 */
public class IDTokenPrincipal extends CallerPrincipal {
	
	/**
	 * 
	 */
	private final String id_token;
	
	/**
	 * 
	 */
	private final Map<String, Object> claims;

	/**
	 * @param name
	 * @param id_token
	 * @param claims
	 */
	public IDTokenPrincipal(final String name, final String id_token, final Map<String, Object> claims) {
		super(name);
		this.id_token = id_token;
		this.claims = claims;
	}

	public String getId_token() {
		return id_token;
	}

	public Map<String, Object> getClaims() {
		return claims;
	}
}

package epf.webapp.security.auth;

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
	 * @param name
	 * @param id_token
	 */
	public IDTokenPrincipal(final String name, final String id_token) {
		super(name);
		this.id_token = id_token;
	}

	public String getId_token() {
		return id_token;
	}

}

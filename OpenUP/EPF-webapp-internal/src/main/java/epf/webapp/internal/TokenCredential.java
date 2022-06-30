package epf.webapp.internal;

import javax.security.enterprise.credential.Credential;

/**
 * 
 */
public class TokenCredential implements Credential {

	private transient final char[] token;
	
	/**
	 * @param token
	 */
	public TokenCredential(final char[] token) {
		this.token = token;
	}

	public char[] getToken() {
		return token;
	}
}

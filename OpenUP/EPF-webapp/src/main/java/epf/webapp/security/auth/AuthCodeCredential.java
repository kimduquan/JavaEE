package epf.webapp.security.auth;

import javax.security.enterprise.credential.Credential;
import epf.security.auth.openid.Provider;
import epf.security.auth.openid.TokenRequest;

/**
 * @author PC
 *
 */
public class AuthCodeCredential implements Credential {
	
	/**
	 * 
	 */
	private transient final Provider provider;

	/**
	 * 
	 */
	private final TokenRequest tokenRequest;
	
	/**
	 * 
	 */
	private transient final char[] clientSecret;
	
	public AuthCodeCredential(final TokenRequest tokenRequest, final Provider provider, final char[] clientSecret) {
		this.provider = provider;
		this.tokenRequest = tokenRequest;
		this.clientSecret = clientSecret;
	}

	public TokenRequest getTokenRequest() {
		return tokenRequest;
	}
	
	public char[] getClientSecret() {
		return clientSecret;
	}

	public Provider getProvider() {
		return provider;
	}
}

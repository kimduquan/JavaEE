package epf.webapp.security.auth;

import javax.security.enterprise.credential.Credential;

import epf.security.auth.openid.ProviderMetadata;
import epf.security.auth.openid.TokenRequest;

/**
 * @author PC
 *
 */
public class AuthCodeCredential implements Credential {
	
	/**
	 * 
	 */
	private final ProviderMetadata providerMetadata;

	/**
	 * 
	 */
	private final TokenRequest tokenRequest;
	
	/**
	 * 
	 */
	private transient final char[] clientSecret;
	
	/**
	 * @param tokenRequest
	 */
	public AuthCodeCredential(final TokenRequest tokenRequest, final ProviderMetadata providerMetadata, final char[] clientSecret) {
		this.providerMetadata = providerMetadata;
		this.tokenRequest = tokenRequest;
		this.clientSecret = clientSecret;
	}

	public TokenRequest getTokenRequest() {
		return tokenRequest;
	}

	public ProviderMetadata getProviderMetadata() {
		return providerMetadata;
	}

	public char[] getClientSecret() {
		return clientSecret;
	}
}

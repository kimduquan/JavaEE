package epf.webapp.security.auth;

import javax.security.enterprise.credential.Credential;
import epf.security.auth.openid.TokenRequest;
import epf.security.auth.openid.spi.ProviderMetadata;

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
	 * @param tokenRequest
	 */
	public AuthCodeCredential(final TokenRequest tokenRequest, final ProviderMetadata providerMetadata) {
		this.providerMetadata = providerMetadata;
		this.tokenRequest = tokenRequest;
		
	}

	public TokenRequest getTokenRequest() {
		return tokenRequest;
	}

	public ProviderMetadata getProviderMetadata() {
		return providerMetadata;
	}
}

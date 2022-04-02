package epf.webapp.security.auth;

import javax.security.enterprise.credential.Credential;
import epf.security.auth.core.TokenRequest;
import epf.security.auth.discovery.ProviderMetadata;

/**
 * @author PC
 *
 */
public class AuthCodeCredential implements Credential {

	/**
	 * 
	 */
	private final TokenRequest tokenRequest;
	
	/**
	 * 
	 */
	private final ProviderMetadata providerMetadata;
	
	/**
	 * @param tokenRequest
	 * @param providerMetadata
	 */
	public AuthCodeCredential(final TokenRequest tokenRequest, final ProviderMetadata providerMetadata) {
		this.tokenRequest = tokenRequest;
		this.providerMetadata = providerMetadata;
	}

	public TokenRequest getTokenRequest() {
		return tokenRequest;
	}

	public ProviderMetadata getProviderMetadata() {
		return providerMetadata;
	}
}

package epf.webapp.security.auth;

import epf.security.auth.core.TokenRequest;
import epf.security.auth.discovery.ProviderMetadata;

/**
 * @author PC
 *
 */
public class AuthCodeCredential extends Credential {

	/**
	 * 
	 */
	private final TokenRequest tokenRequest;
	
	/**
	 * 
	 */
	private final ProviderMetadata providerMetadata;
	
	/**
	 * @param providerMetadata
	 * @param tokenRequest
	 * @param provider
	 * @param sessionId
	 */
	public AuthCodeCredential(final ProviderMetadata providerMetadata, final TokenRequest tokenRequest, final String provider, final String sessionId) {
		super(provider, sessionId);
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
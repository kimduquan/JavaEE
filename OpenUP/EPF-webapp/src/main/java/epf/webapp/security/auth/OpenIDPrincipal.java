package epf.webapp.security.auth;

import javax.security.enterprise.CallerPrincipal;
import epf.security.auth.openid.core.TokenResponse;
import epf.security.auth.openid.discovery.ProviderMetadata;

/**
 * @author PC
 *
 */
public class OpenIDPrincipal extends CallerPrincipal {
	
	/**
	 * 
	 */
	private final TokenResponse token;
	
	/**
	 * 
	 */
	private final ProviderMetadata providerMetadata;

	/**
	 * @param name
	 * @param token
	 * @param metadata
	 */
	public OpenIDPrincipal(final String name, final TokenResponse token, final ProviderMetadata metadata) {
		super(name);
		this.token = token;
		this.providerMetadata = metadata;
	}

	public TokenResponse getToken() {
		return token;
	}

	public ProviderMetadata getProviderMetadata() {
		return providerMetadata;
	}
}

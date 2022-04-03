package epf.webapp.security.auth;

import javax.security.enterprise.credential.Credential;

import epf.security.auth.core.CodeFlowAuth;
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
	 * 
	 */
	private transient final CodeFlowAuth flow;
	
	/**
	 * @param providerMetadata
	 * @param tokenRequest
	 * @param flow
	 */
	public AuthCodeCredential(final ProviderMetadata providerMetadata, final TokenRequest tokenRequest, final CodeFlowAuth flow) {
		this.tokenRequest = tokenRequest;
		this.providerMetadata = providerMetadata;
		this.flow = flow;
	}

	public TokenRequest getTokenRequest() {
		return tokenRequest;
	}

	public ProviderMetadata getProviderMetadata() {
		return providerMetadata;
	}

	public CodeFlowAuth getFlow() {
		return flow;
	}
}

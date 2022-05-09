package epf.webapp.security.auth.core;

import epf.security.auth.discovery.ProviderMetadata;

/**
 * 
 */
public abstract class Flow {
	
	/**
	 * 
	 */
	private ProviderMetadata providerMetadata;
	
	/**
	 *
	 */
	private String provider;
	
	/**
	 *
	 */
	private String sessionId;

	public ProviderMetadata getProviderMetadata() {
		return providerMetadata;
	}

	public void setProviderMetadata(final ProviderMetadata providerMetadata) {
		this.providerMetadata = providerMetadata;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(final String provider) {
		this.provider = provider;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(final String sessionId) {
		this.sessionId = sessionId;
	}
}

package epf.webapp.security.auth.core;

import epf.security.auth.discovery.ProviderMetadata;

public abstract class Flow {
	
	/**
	 * 
	 */
	private ProviderMetadata providerMetadata;

	public ProviderMetadata getProviderMetadata() {
		return providerMetadata;
	}

	public void setProviderMetadata(final ProviderMetadata providerMetadata) {
		this.providerMetadata = providerMetadata;
	}
}

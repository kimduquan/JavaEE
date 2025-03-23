package epf.webapp;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@ApplicationScoped
@Named("config")
public class Config {

	private final String providerURI = "http://localhost:9196/.well-known/openid-configuration";
	private final String clientId = "oidc-client";
	private final String scope = "openid";
	private final String[] extraParameters = new String[] {};

	public String getProviderURI() {
		return providerURI;
	}

	public String getClientId() {
		return clientId;
	}

	public String getScope() {
		return scope;
	}

	public String[] getExtraParameters() {
		return extraParameters;
	}
}

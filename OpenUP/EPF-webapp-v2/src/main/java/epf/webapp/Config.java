package epf.webapp;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@ApplicationScoped
@Named("config")
public class Config {

	private final String providerURI = "http://localhost:9196/realms/EPF/.well-known/openid-configuration";
	private final String clientId = "oidc-client";
	private final String clientSecret = "1D5W0hPaL9712X8H8mcYX2USsxJqYh8R";
	private final String[] extraParameters = new String[] {};

	public String getProviderURI() {
		return providerURI;
	}

	public String getClientId() {
		return clientId;
	}

	public String[] getExtraParameters() {
		return extraParameters;
	}

	public String getClientSecret() {
		return clientSecret;
	}
}

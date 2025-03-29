package epf.webapp;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@ApplicationScoped
@Named("config")
public class Config {

	private final String providerURI = "http://localhost:9196/realms/EPF/.well-known/openid-configuration";
	private final String clientId = "oidc-client";
	private final String clientSecret = "a1tF4THlREb1nFQaqisSnoxVgq5PiWtA";

	public String getProviderURI() {
		return providerURI;
	}

	public String getClientId() {
		return clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}
}

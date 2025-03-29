package epf.webapp;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@ApplicationScoped
@Named("config")
public class Config {

	private final String providerURI = "https://host.docker.internal/openid/realms/EPF/.well-known/openid-configuration";
	private final String clientId = "oidc-client";
	private final String clientSecret = "Cp2WH4PLVor69p4Uw9t2yNyPoshwjfl9";

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

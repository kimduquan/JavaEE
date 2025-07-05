package epf.webapp.persistence;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@ApplicationScoped
@Named("config")
public class Config {

	@Inject
    @ConfigProperty(name = "oidc.provider.uri")
	private String providerURI;
	
	@Inject
    @ConfigProperty(name = "oidc.client.id")
	private String clientId;
	
	@Inject
    @ConfigProperty(name = "oidc.client.secret")
	private String clientSecret;

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

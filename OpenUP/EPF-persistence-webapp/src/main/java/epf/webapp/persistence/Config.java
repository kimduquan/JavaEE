package epf.webapp.persistence;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import epf.naming.Naming;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@ApplicationScoped
@Named(Naming.CONFIG)
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
	
	@Inject
    @ConfigProperty(name = Naming.Gateway.GATEWAY_URL)
	private String gatewayUrl;

	public String getProviderURI() {
		return providerURI;
	}

	public String getClientId() {
		return clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public String getGatewayUrl() {
		return gatewayUrl;
	}
}

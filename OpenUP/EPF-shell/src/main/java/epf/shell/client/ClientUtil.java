package epf.shell.client;

import epf.naming.Naming;
import epf.shell.security.SecurityUtil;
import java.net.URI;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.UriBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

@ApplicationScoped
public class ClientUtil {
	
	@Inject
	transient SecurityUtil securityUtil;
	
	@ConfigProperty(name = Naming.Client.CLIENT_CONFIG + "/mp-rest/uri")
	@Inject
	URI gatewayUrl;
	
	private RestClientBuilder build(final RestClientBuilder builder) {
		return builder.keyStore(securityUtil.getKeyStore(), securityUtil.getKeyPassword()).trustStore(securityUtil.getTrustStore());
	}
	
	public URI getUrl(final String name) {
		return gatewayUrl.resolve(name);
	}
	
	public URI getWSUrl(final String name) {
		final URI url = gatewayUrl.resolve(name);
		return UriBuilder.fromUri(url).scheme("wss").build();
	}
	
	public <T> T newClient(final Class<T> cls) {
		return build(RestClientBuilder.newBuilder()).baseUri(gatewayUrl).build(cls);
	}
	
	public <T> T newClient(final URI baseUri, final Class<T> cls) {
		return build(RestClientBuilder.newBuilder()).baseUri(baseUri).build(cls);
	}
}

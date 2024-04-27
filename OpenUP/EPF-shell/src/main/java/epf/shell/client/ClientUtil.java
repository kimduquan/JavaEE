package epf.shell.client;

import epf.naming.Naming;
import epf.shell.security.SecurityUtil;
import java.net.URI;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class ClientUtil {
	
	/**
	 *
	 */
	@Inject
	transient SecurityUtil securityUtil;
	
	/**
	 * 
	 */
	@ConfigProperty(name = Naming.Client.CLIENT_CONFIG + "/mp-rest/uri")
	@Inject
	URI gatewayUrl;
	
	/**
	 * @param builder
	 * @return
	 */
	private RestClientBuilder build(final RestClientBuilder builder) {
		return builder.keyStore(securityUtil.getKeyStore(), securityUtil.getKeyPassword()).trustStore(securityUtil.getTrustStore());
	}
	
	/**
	 * @param name
	 * @return
	 */
	public URI getUrl(final String name) {
		return gatewayUrl.resolve(name);
	}
	
	/**
	 * @param <T>
	 * @param cls
	 * @return
	 */
	public <T> T newClient(final Class<T> cls) {
		return build(RestClientBuilder.newBuilder()).baseUri(gatewayUrl).build(cls);
	}
	
	/**
	 * @param <T>
	 * @param baseUri
	 * @param cls
	 * @return
	 */
	public <T> T newClient(final URI baseUri, final Class<T> cls) {
		return build(RestClientBuilder.newBuilder()).baseUri(baseUri).build(cls);
	}
}

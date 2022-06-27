package epf.shell.client;

import epf.client.util.Client;
import epf.client.util.ClientQueue;
import epf.naming.Naming;
import epf.shell.security.SecurityUtil;
import java.net.URI;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.ClientBuilder;
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
	private transient ClientQueue clients;
	
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
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		clients = new ClientQueue();
	}
	
	/**
	 * 
	 */
	@PreDestroy
	protected void preDestroy() {
		clients.close();
	}
	
	/**
	 * @param builder
	 * @return
	 */
	private ClientBuilder build(final ClientBuilder builder) {
		return builder.keyStore(securityUtil.getKeyStore(), securityUtil.getKeyPassword()).trustStore(securityUtil.getTrustStore());
	}
	
	/**
	 * @param uri
	 * @return
	 */
	public Client newClient(final URI uri) {
		return new Client(clients, uri, this::build);
	}
	
	/**
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public Client newClient(final String name) throws Exception {
		return new Client(clients, gatewayUrl.resolve(name), this::build);
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
		return RestClientBuilder.newBuilder().keyStore(securityUtil.getKeyStore(), securityUtil.getKeyPassword()).trustStore(securityUtil.getTrustStore()).baseUri(gatewayUrl).build(cls);
	}
	
	/**
	 * @param <T>
	 * @param baseUri
	 * @param cls
	 * @return
	 */
	public <T> T newClient(final URI baseUri, final Class<T> cls) {
		return RestClientBuilder.newBuilder().keyStore(securityUtil.getKeyStore(), securityUtil.getKeyPassword()).trustStore(securityUtil.getTrustStore()).baseUri(baseUri).build(cls);
	}
}

/**
 * 
 */
package epf.shell.client;

import epf.client.util.Client;
import epf.client.util.ClientQueue;
import epf.naming.Naming;
import java.net.URI;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

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
	@ConfigProperty(name = Naming.Client.CLIENT_CONFIG + "/mp-rest/uri")
	URI gatewayUrl;

	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		clients = new ClientQueue();
		clients.initialize();
	}
	
	/**
	 * 
	 */
	@PreDestroy
	protected void preDestroy() {
		clients.close();
	}
	
	/**
	 * @param uri
	 * @return
	 */
	public Client newClient(final URI uri) {
		return new Client(clients, uri, builder -> builder);
	}
	
	/**
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public Client newClient(final String name) throws Exception {
		return new Client(clients, gatewayUrl.resolve(name), builder -> builder);
	}
	
	/**
	 * @param name
	 * @return
	 */
	public URI getUrl(final String name) {
		return gatewayUrl.resolve(name);
	}
}

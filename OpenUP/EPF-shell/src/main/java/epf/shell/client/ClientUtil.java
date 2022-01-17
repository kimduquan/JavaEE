/**
 * 
 */
package epf.shell.client;

import epf.client.gateway.GatewayUtil;
import epf.client.util.Client;
import epf.client.util.ClientQueue;
import epf.naming.Naming;
import java.net.URI;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
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
	@ConfigProperty(name = Naming.Client.CLIENT_CONFIG + "/mp-rest/uri")
	String gatewayUrl;

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
		return new Client(clients, GatewayUtil.get(gatewayUrl, name), builder -> builder);
	}
	
	/**
	 * @return
	 */
	public String getBaseUri() {
		return gatewayUrl;
	}
}

/**
 * 
 */
package epf.shell.client;

import epf.client.gateway.GatewayUtil;
import epf.client.util.Client;
import epf.client.util.ClientQueue;
import java.net.URI;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;

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
	 * @param gatewayUrl
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public Client newClient(final String gatewayUrl, final String name) throws Exception {
		return new Client(clients, GatewayUtil.get(gatewayUrl, name), builder -> builder);
	}
}

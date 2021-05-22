/**
 * 
 */
package epf.shell.client;

import java.net.URI;
import epf.util.client.Client;
import epf.util.client.ClientQueue;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;

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
		return new Client(clients, uri, null);
	}
}

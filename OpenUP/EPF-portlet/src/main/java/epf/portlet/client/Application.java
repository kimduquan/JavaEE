/**
 * 
 */
package epf.portlet.client;

import java.net.URI;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import epf.util.client.Client;
import epf.util.client.ClientQueue;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class Application {

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
	 * @return
	 */
	protected ClientQueue getClients() {
		return clients;
	}
	
	/**
	 * @param url
	 * @return
	 */
	public Client newClient(final URI url) {
		return new Client(clients, url, b -> b);
	}
}

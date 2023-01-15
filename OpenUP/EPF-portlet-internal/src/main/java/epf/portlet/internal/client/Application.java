package epf.portlet.internal.client;

import java.net.URI;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;

import epf.client.internal.ClientQueue;
import epf.client.util.Client;

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
		Objects.requireNonNull(url, "URI");
		return new Client(clients.poll(url, b -> b), url, clients::add);
	}
}

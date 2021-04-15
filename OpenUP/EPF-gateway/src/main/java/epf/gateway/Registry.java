package epf.gateway;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import epf.util.client.Client;
import epf.util.client.ClientQueue;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class Registry {

	/**
	 * 
	 */
	private static final String SERVICE_URL = System.getenv("epf.service.url");
	
	/**
	 * 
	 */
	private transient final Map<String, URI> remotes;
	
	/**
	 * 
	 */
	@Inject
	private transient ClientQueue clients;
	
	/**
	 * 
	 */
	@Inject
	private transient Logger logger;
	
	/**
	 * 
	 */
	public Registry() {
		remotes = new ConcurrentHashMap<>();
	}
    
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try(Client client = new Client(clients, new URI(SERVICE_URL).resolve("registry"), b -> b)){
			client
			.request(
					target -> target, 
					req -> req
					)
			.get()
			.getLinks()
			.forEach(link -> {
				remotes.put(link.getRel(), link.getUri());
			});
		} 
		catch (Exception e) {
			logger.log(Level.SEVERE, SERVICE_URL, e);
		}
	}
	
	/**
	 * @param name
	 * @return
	 */
	public URI lookup(final String name) {
		return remotes.get(name);
	}
}

package epf.client.registry;

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
public class LocateRegistry {

	/**
	 * 
	 */
	private static final String REGISTRY_URL = System.getenv("epf.registry.url");
	
	/**
	 * 
	 */
	private final transient Map<String, URI> remotes;
	
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
	public LocateRegistry() {
		remotes = new ConcurrentHashMap<>();
	}
    
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try(Client client = new Client(clients, new URI(REGISTRY_URL), b -> b)){
			Registry
			.list(client, null)
			.forEach(link -> {
				remotes.put(link.getRel(), link.getUri());
			});
		} 
		catch (Exception e) {
			logger.log(Level.SEVERE, REGISTRY_URL, e);
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

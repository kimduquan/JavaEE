package epf.gateway;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import epf.client.util.Client;
import epf.client.util.ClientQueue;
import epf.naming.Naming;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class Registry {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(Registry.class.getName());
	
	/**
	 * 
	 */
	private transient final Map<String, URI> remotes = new ConcurrentHashMap<>();
	
	/**
	 * 
	 */
	@Inject
	transient ClientQueue clients;
	
	/**
	 * 
	 */
	@ConfigProperty(name = Naming.Registry.REGISTRY_URL)
	String registryUrl;
    
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try(Client client = new Client(clients, new URI(registryUrl), b -> b)){
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
			LOGGER.log(Level.SEVERE, registryUrl, e);
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
